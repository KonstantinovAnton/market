package com.example.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button buttonBackToAuto;
    Button buttonAddMeat;
    Button buttonAddDrinks;

    Button buttonChangeMeat;
    Button buttonChangeDrinks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonAddMeat = findViewById(R.id.buttonAddMeat);
        buttonAddMeat.setOnClickListener(this);

        buttonChangeMeat = findViewById(R.id.buttonChangeMeat);
        buttonChangeMeat.setOnClickListener(this);

        buttonChangeDrinks = findViewById(R.id.buttonChangeDrinks);
        buttonChangeDrinks.setOnClickListener(this);


        buttonAddDrinks = findViewById(R.id.buttonAddDrinks);
        buttonAddDrinks.setOnClickListener(this);

        buttonBackToAuto = findViewById(R.id.buttonBackToAuto);
        buttonBackToAuto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonAddMeat:
                startActivity(new Intent(this, addMeat.class));
                break;

            case R.id.buttonChangeMeat:
                startActivity(new Intent(this, redMeat.class));
                break;

            case R.id.buttonChangeDrinks:
                startActivity(new Intent(this, redDrinks.class));
                break;

            case R.id.buttonAddDrinks:
                startActivity(new Intent(this, addDrinks.class));
                break;

            case R.id.buttonBackToAuto:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}