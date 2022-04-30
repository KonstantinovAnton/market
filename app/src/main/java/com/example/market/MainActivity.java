package com.example.market;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLogin;
    EditText editTextPassword;

    DBHelper dbHelper;
    SQLiteDatabase database;

    Button buttonEnter;
    Button buttonReg;

    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonEnter = findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(this);

        buttonReg = findViewById(R.id.buttonReg);
        buttonReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonEnter:
                Cursor logCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean logged = false;
                if(logCursor.moveToFirst()){
                    int usernameIndex = logCursor.getColumnIndex(DBHelper.KEY_NAME);
                    int passwordIndex = logCursor.getColumnIndex(DBHelper.KEY_PASSWORD);
                    do{
                        if(editTextLogin.getText().toString().equals("admin") && editTextPassword.getText().toString().equals("admin")) {
                            startActivity(new Intent(this, MainActivity2.class));
                            logged = true;
                            break;
                        }
                        if(editTextLogin.getText().toString().equals(logCursor.getString(usernameIndex)) && editTextPassword.getText().toString().equals(logCursor.getString(passwordIndex))){
                            user = logCursor.getString(usernameIndex);
                            startActivity(new Intent(this, userMenu.class));
                            logged = true;
                           break;
                        }
                    }while (logCursor.moveToNext());
                }
                logCursor.close();
                if(!logged) Toast.makeText(this, "Данный камрад не найден", Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonReg:
                Cursor signCursor = database.query(DBHelper.TABLE_USERS, null, null, null, null, null, null);

                boolean finded = false;
                if(signCursor.moveToFirst()){
                    int usernameIndex = signCursor.getColumnIndex(DBHelper.KEY_NAME);
                    do{
                        if(editTextLogin.getText().toString().equals(signCursor.getString(usernameIndex))){
                            Toast.makeText(this, "Такой камрад уже есть!", Toast.LENGTH_LONG).show();
                            finded = true;
                            break;
                        }
                    }while (signCursor.moveToNext());
                }
                if(!finded){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_NAME, editTextLogin.getText().toString());
                    contentValues.put(DBHelper.KEY_PASSWORD, editTextPassword.getText().toString());
                    database.insert(DBHelper.TABLE_USERS, null, contentValues);
                    Toast.makeText(this, "Новый камрад запомнен!", Toast.LENGTH_LONG).show();
                }
                signCursor.close();
                break;

        }
    }
}