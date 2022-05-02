package com.example.market;

import static java.lang.String.valueOf;

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
import android.widget.Toast;

public class chDrink extends AppCompatActivity implements View.OnClickListener {

    Button buttonBuyDrinks;

    TextView totalVol;
    TextView totalPrice;

    DBHelper dbHelper;
    SQLiteDatabase database;

    float vol = 0;
    String count = "Литраж:\n" + vol;

    float summa;
    String totalAmount = "Сумма:\n"+ summa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ch_drink);
        buttonBuyDrinks = findViewById(R.id.buttonBuyDrinks);
        buttonBuyDrinks.setOnClickListener(this);

        totalVol = findViewById(R.id.textTotalVol);
        totalVol.setText(valueOf(count));

        totalPrice = findViewById(R.id.textDrinksBill);
        totalPrice.setText(valueOf(totalAmount));

        totalVol.setTextColor(Color.parseColor("#E0E0E0"));
        totalPrice.setTextColor(Color.parseColor("#E0E0E0"));


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
            TableLayout dbOutput = findViewById(R.id.dbOutput);
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
                outputPrice.setText(cursor.getString(priceIndex) + "руб");
                outputPrice.setTextSize(12);
                dbOutputRow.addView(outputPrice);

                outputEnd.setTextColor(Color.parseColor("#E0E0E0"));
                outputStart.setTextColor(Color.parseColor("#E0E0E0"));
                outputPrice.setTextColor(Color.parseColor("#E0E0E0"));

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight = 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Взять");
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
            case R.id.buttonBuyDrinks:
                if(vol != 0){
                    Toast.makeText(this, "Вы затарились " + vol + " литрами на сумму в " + summa + " рублей", Toast.LENGTH_LONG).show();
                    vol = 0;
                    count = "Вес:\n" + vol;
                    totalVol.setText(count);
                    summa = 0;
                    totalAmount = "Сумма:\n"+ summa;
                    totalPrice.setText(totalAmount);
                }
                else{
                    Toast.makeText(this, "Вы не взяли ни капли", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                View outputDBRow = (View) view.getParent();
                ViewGroup outputDB = (ViewGroup) outputDBRow.getParent();
                int index = outputDB.indexOfChild(outputDBRow);
                Cursor cursorUpdater = database.query(DBHelper.TABLE_DRINKS, null, null, null, null, null, null);
                if (cursorUpdater != null) {
                    cursorUpdater.moveToPosition(index);
                    summa = summa + cursorUpdater.getFloat(3);
                    totalAmount = "Сумма:\n"+ summa + " рублей";
                }
                totalPrice.setText(valueOf(totalAmount));

                vol = vol + cursorUpdater.getFloat(2);

                totalVol.setText("Литраж:\n" + vol + " л");
                break;
        }
    }
}