package com.example.market;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

public class addMeat extends AppCompatActivity implements View.OnClickListener {

    EditText editTextSort;
    EditText editTextWeight;
    EditText editTextPrice;
    Button buttonBackToMenu_meat;
    Button buttonAdd;

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meat);


        editTextSort = findViewById(R.id.editTextSort);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextPrice = findViewById(R.id.editTextPrice);
        buttonBackToMenu_meat = findViewById(R.id.buttonBackToMenu_meat);
        buttonBackToMenu_meat.setOnClickListener(this);

        buttonAdd = findViewById(R.id.buttonAddMeat_a);
        buttonAdd.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();



        UpdateTable();

    }

    public void UpdateTable() {
        Cursor cursor = database.query(DBHelper.TABLE_MEAT, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID1);
            int startRouteIndex = cursor.getColumnIndex(DBHelper.KEY_SORT);
            int endRouteIndex = cursor.getColumnIndex(DBHelper.KEY_WEIGHT);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICE);
            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                outputID.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                outputID.setTextSize(12);
                dbOutputRow.addView(outputID);

                TextView outputStart = new TextView(this);
                outputStart.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 3.0f;
                outputStart.setLayoutParams(params);
                outputStart.setText(cursor.getString(startRouteIndex));
                outputStart.setTextSize(12);
                dbOutputRow.addView(outputStart);

                TextView outputEnd = new TextView(this);
                outputEnd.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 3.0f;
                outputEnd.setLayoutParams(params);
                outputEnd.setText(cursor.getString(endRouteIndex) + " кг");
                outputEnd.setTextSize(12);
                dbOutputRow.addView(outputEnd);

                TextView outputPrice = new TextView(this);
                outputPrice.setTextColor(Color.parseColor("#E0E0E0"));
                params.weight = 3.0f;
                outputPrice.setLayoutParams(params);
                outputPrice.setText(cursor.getString(priceIndex) + " руб");
                outputPrice.setTextSize(12);
                dbOutputRow.addView(outputPrice);




                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight = 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Убрать\nмясо");
                deleteBtn.setTextSize(12);
                deleteBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonBackToMenu_meat:
                startActivity(new Intent(this, MainActivity2.class));
                break;

            case R.id.buttonAddMeat_a:
                String sort = editTextSort.getText().toString();
                String weight = editTextWeight.getText().toString();
                String price = editTextPrice.getText().toString();
                contentValues = new ContentValues();

                contentValues.put(DBHelper.KEY_SORT, sort);
                contentValues.put(DBHelper.KEY_WEIGHT, weight);
                contentValues.put(DBHelper.KEY_PRICE, price);

                database.insert(DBHelper.TABLE_MEAT, null, contentValues);
                editTextSort.setText("");
                editTextWeight.setText("");
                editTextPrice.setText("");
                UpdateTable();
                break;



            default:

                View outputDBRow = (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                outputDB.removeView(outputDBRow);
                outputDB.invalidate();




                database.delete(DBHelper.TABLE_MEAT, DBHelper.KEY_ID1 + " = ?", new String[]{String.valueOf(view.getId())});

                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DBHelper.TABLE_MEAT, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_ID1);
                    int startRouteIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_SORT);
                    int endRouteIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_PRICE);
                    int priceIndex = cursorUpdater.getColumnIndex(DBHelper.KEY_PRICE);
                    int realID = 1;
                    do{
                        if(cursorUpdater.getInt(idIndex) > idIndex){
                            contentValues.put(DBHelper.KEY_ID1, realID);
                            contentValues.put(DBHelper.KEY_SORT, cursorUpdater.getString(startRouteIndex));
                            contentValues.put(DBHelper.KEY_WEIGHT, cursorUpdater.getString(endRouteIndex));
                            contentValues.put(DBHelper.KEY_PRICE, cursorUpdater.getString(priceIndex));
                            database.replace(DBHelper.TABLE_MEAT, null, contentValues);
                        }
                        realID++;
                    } while(cursorUpdater.moveToNext());
                    if(cursorUpdater.moveToLast() && (cursorUpdater.getInt(idIndex) == realID)){
                        database.delete(DBHelper.TABLE_MEAT, DBHelper.KEY_ID1 + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                break;


        }
    }
}