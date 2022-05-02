package com.example.market;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class redDrinks extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_drinks);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        UpdateTable();

    }

    public void UpdateTable(){
        Cursor cursor = database.query(DBHelper.TABLE_DRINKS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID2);
            int startRouteIndex = cursor.getColumnIndex(DBHelper.KEY_NAMING);
            int endRouteIndex = cursor.getColumnIndex(DBHelper.KEY_VOL);
            int priceIndex = cursor.getColumnIndex(DBHelper.KEY_PRICED);
            TableLayout dbOutput = findViewById(R.id.dbOutput_redDrinks);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputStart = new TextView(this);
                params.weight = 3.0f;
                outputStart.setLayoutParams(params);
                outputStart.setText(cursor.getString(startRouteIndex));
                outputStart.setTextSize(12);
                dbOutputRow.addView(outputStart);

                TextView outputEnd = new TextView(this);
                params.weight = 3.0f;
                outputEnd.setLayoutParams(params);
                outputEnd.setText(cursor.getString(endRouteIndex) + " л");
                outputEnd.setTextSize(12);
                dbOutputRow.addView(outputEnd);

                TextView outputPrice = new TextView(this);
                params.weight = 3.0f;
                outputPrice.setLayoutParams(params);
                outputPrice.setText(cursor.getString(priceIndex) + " руб");
                outputPrice.setTextSize(12);
                dbOutputRow.addView(outputPrice);

                outputEnd.setTextColor(Color.parseColor("#E0E0E0"));
                outputStart.setTextColor(Color.parseColor("#E0E0E0"));
                outputPrice.setTextColor(Color.parseColor("#E0E0E0"));

                Button chBtn = new Button(this);
                chBtn.setOnClickListener(this);
                params.weight = 1.0f;
                chBtn.setLayoutParams(params);
                chBtn.setText("Изменить");
                chBtn.setTextSize(12);
                chBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(chBtn);

                dbOutput.addView(dbOutputRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                View outputDBRow = (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                int indexStr = outputDB.indexOfChild(outputDBRow);
                int index = 0;

                Cursor cursorUpdater = database.rawQuery("SELECT * FROM " + DBHelper.TABLE_DRINKS, null);

                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(indexStr);
                    index =  cursorUpdater.getInt(0);
                }
                Intent intent = new Intent(this, redDrinksWin.class);
                Bundle b = new Bundle();
                b.putInt("key", index);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                assert cursorUpdater != null;
                cursorUpdater.close();
                break;



        }

    }
}