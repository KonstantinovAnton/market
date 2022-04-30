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

public class redDrinksWin extends AppCompatActivity implements View.OnClickListener {

    EditText editTextNaming_red;
    EditText editTextVol_red;
    EditText editTextPriced_red;

    Button buttonSaveRedDrink;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_drinks_win);

        editTextNaming_red = findViewById(R.id.editTextDrinkName_red);
        editTextVol_red = findViewById(R.id.editTextVol_red);
        editTextPriced_red = findViewById(R.id.editTextDrinkPrice_red);

        buttonSaveRedDrink = findViewById(R.id.buttonSaveRedDrink);
        buttonSaveRedDrink.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Bundle arguments = getIntent().getExtras();
        index = arguments.getInt("key");

        Update();

    }

    public void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_DRINKS + " WHERE " + DBHelper.KEY_ID2 + " = '" + index + "'", null);

        int namingIndex = cursor.getColumnIndex(DBHelper.KEY_NAMING);
        int volIndex = cursor.getColumnIndex(DBHelper.KEY_VOL);
        int pricedIndex = cursor.getColumnIndex(DBHelper.KEY_PRICED);

        if (cursor.moveToFirst()) {
            editTextNaming_red.setText(cursor.getString(namingIndex));
            editTextVol_red.setText(cursor.getString(volIndex));
            editTextPriced_red.setText(cursor.getString(pricedIndex));
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSaveRedDrink:
                String naming = editTextNaming_red.getText().toString();
                String vol = editTextVol_red.getText().toString();
                String priced = editTextPriced_red.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAMING, naming);
                contentValues.put(DBHelper.KEY_VOL, vol);
                contentValues.put(DBHelper.KEY_PRICED, priced);
                database.update(DBHelper.TABLE_DRINKS, contentValues, DBHelper.KEY_ID2 + " = '" + index + "'", null);
                startActivity(new Intent(this, redDrinks.class));
                finish();
                break;
        }
    }
}