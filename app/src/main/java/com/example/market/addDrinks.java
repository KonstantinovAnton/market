package com.example.market;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class addDrinks extends AppCompatActivity implements View.OnClickListener {

    EditText editTextDrinkName;
    EditText editTextVol;
    EditText editTextDrinkPrice;
    Button buttonBackToMenu_drinks;
    Button buttonAddDrink;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinks);
        buttonBackToMenu_drinks = findViewById(R.id.buttonBackToMenu_drinks);
        buttonBackToMenu_drinks.setOnClickListener(this);
        editTextDrinkName = findViewById(R.id.editTextDrinkName);
        editTextVol = findViewById(R.id.editTextVol);
        editTextDrinkPrice = findViewById(R.id.editTextDrinkPrice);

        buttonAddDrink = findViewById(R.id.buttonAddDrinks_a);
        buttonAddDrink.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();



        UpdateTable();
    }
    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_DRINKS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID2);
            int startRouteIndex = cursor.getColumnIndex(DBHelper.KEY_NAMING);
            int endRouteIndex = cursor.getColumnIndex(DBHelper.KEY_VOL);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICED);
            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);

                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputIDD = new TextView(this);
                outputIDD.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 1.0f;
                outputIDD.setLayoutParams(params);
                outputIDD.setText(cursor.getString(idIndex));
                outputIDD.setTextSize(12);
                dbOutputRow.addView(outputIDD);

                TextView outputStartD = new TextView(this);
                outputStartD.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 3.0f;
                outputStartD.setLayoutParams(params);
                outputStartD.setText(cursor.getString(startRouteIndex));
                outputStartD.setTextSize(12);
                dbOutputRow.addView(outputStartD);

                TextView outputEndD = new TextView(this);
                params.weight = 3.0f;
                outputEndD.setTextColor(Color.parseColor("#E0E0E0"));
                outputEndD.setLayoutParams(params);
                outputEndD.setText(cursor.getString(endRouteIndex) + " л");
                outputEndD.setTextSize(12);
                dbOutputRow.addView(outputEndD);



                TextView outputPriceD = new TextView(this);
                outputPriceD.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 3.0f;
                outputPriceD.setLayoutParams(params);
                outputPriceD.setText(cursor.getString(priceIndex) + " руб");
                outputPriceD.setTextSize(12);
                dbOutputRow.addView(outputPriceD);

                Button deleteBtnD = new Button(this);
                deleteBtnD.setOnClickListener(this);

                params.weight = 1.0f;
                deleteBtnD.setLayoutParams(params);
                deleteBtnD.setText("Убрать\nнапиток");
                deleteBtnD.setTextSize(12);
                deleteBtnD.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtnD);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonBackToMenu_drinks:
                startActivity(new Intent(this, MainActivity2.class));
                break;


            case R.id.buttonAddDrinks_a:
                String drinkName = editTextDrinkName.getText().toString();
                String vol = editTextVol.getText().toString();
                String drinkPrice = editTextDrinkPrice.getText().toString();
                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_NAMING, drinkName);
                contentValues.put(DBHelper.KEY_VOL, vol);
                contentValues.put(DBHelper.KEY_PRICED, drinkPrice);

                database.insert(DBHelper.TABLE_DRINKS, null, contentValues);
                editTextDrinkName.setText("");
                editTextVol.setText("");
                editTextDrinkPrice.setText("");
                UpdateTable();
                break;

            default:
                View outputDBRow = (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                outputDB.removeView(outputDBRow);
                outputDB.invalidate();

                database.delete(DBHelper.TABLE_DRINKS, DBHelper.KEY_ID2 + " = ?", new String[]{String.valueOf(view.getId())});

                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DBHelper.TABLE_DRINKS, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID2);
                    int startRouteIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_NAMING);
                    int endRouteIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_VOL);
                    int priceIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_PRICED);
                    int realID = 1;
                    do{
                        if(cursorUpdater.getInt(idIndex) > idIndex){
                            contentValues.put(DBHelper.KEY_ID2, realID);
                            contentValues.put(DBHelper.KEY_NAMING, cursorUpdater.getString(startRouteIndex));
                            contentValues.put(DBHelper.KEY_VOL, cursorUpdater.getString(endRouteIndex));
                            contentValues.put(DBHelper.KEY_PRICED, cursorUpdater.getString(priceIndex));
                            database.replace(DBHelper.TABLE_DRINKS, null, contentValues);
                        }
                        realID++;
                    } while(cursorUpdater.moveToNext());
                    if(cursorUpdater.moveToLast() && (cursorUpdater.getInt(idIndex) == realID)){
                        database.delete(DBHelper.TABLE_DRINKS, DBHelper.KEY_ID2 + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                break;

        }

    }
}