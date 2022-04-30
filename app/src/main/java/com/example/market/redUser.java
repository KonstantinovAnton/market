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

public class redUser extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLoginRed;
    EditText editTextPasswordRed;

    Button buttonSaveUser;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_user);

        editTextLoginRed = findViewById(R.id.editTextLoginRed);
        editTextPasswordRed = findViewById(R.id.editTextPasswordRed);

        buttonSaveUser = findViewById(R.id.buttonSaveUser);
        buttonSaveUser.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Update();

    }
    public void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_USERS + " WHERE " + DBHelper.KEY_NAME + " = '" + MainActivity.user + "'", null);

        int loginIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
        int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_PASSWORD);

        if (cursor.moveToFirst()) {
            editTextLoginRed.setText(cursor.getString(loginIndex));
            editTextPasswordRed.setText(cursor.getString(passwordIndex));
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSaveUser:
                String login = editTextLoginRed.getText().toString();
                String password = editTextPasswordRed.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAME, login);
                contentValues.put(DBHelper.KEY_PASSWORD, password);
                database.update(DBHelper.TABLE_USERS, contentValues,DBHelper.KEY_NAME + " = '" + MainActivity.user + "'", null);
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}