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

public class redMeatWin extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSort_red;
    EditText editTextWeight_red;
    EditText editTextPrice_red;

    Button buttonSaveRedMeat;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_meat_win);

        editTextSort_red = findViewById(R.id.editTextSort_red);
        editTextWeight_red = findViewById(R.id.editTextWeight_red);
        editTextPrice_red = findViewById(R.id.editTextPrice_red);

        buttonSaveRedMeat = findViewById(R.id.buttonSaveRedMeat);
        buttonSaveRedMeat.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        Bundle arguments = getIntent().getExtras();
        index = arguments.getInt("key");

        Update();
    }

    public void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_MEAT + " WHERE " + DBHelper.KEY_ID1 + " = '" + index + "'", null);

        int sortIndex = cursor.getColumnIndex(DBHelper.KEY_SORT);
        int weightIndex = cursor.getColumnIndex(DBHelper.KEY_WEIGHT);
        int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);

        if (cursor.moveToFirst()) {
            editTextSort_red.setText(cursor.getString(sortIndex));
            editTextWeight_red.setText(cursor.getString(weightIndex));
            editTextPrice_red.setText(cursor.getString(priceIndex));
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSaveRedMeat:
                String sort = editTextSort_red.getText().toString();
                String weight = editTextWeight_red.getText().toString();
                String price = editTextPrice_red.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_SORT, sort);
                contentValues.put(DBHelper.KEY_WEIGHT, weight);
                contentValues.put(DBHelper.KEY_PRICE, price);
                database.update(DBHelper.TABLE_MEAT, contentValues,DBHelper.KEY_ID1 + " = '" + index + "'", null);
                startActivity(new Intent(this, redMeat.class));
                finish();
                break;


    }
}}
