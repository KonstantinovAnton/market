package com.example.market;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userMenu extends AppCompatActivity implements View.OnClickListener {

    Button buttonBackToAuto;
    Button buttonChMeat;
    Button buttonChDrinks;
    Button buttonRedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        buttonChMeat = findViewById(R.id.buttonChMeat);
        buttonChMeat.setOnClickListener(this);

        buttonChDrinks = findViewById(R.id.buttonChDrinks);
        buttonChDrinks.setOnClickListener(this);

        buttonBackToAuto = findViewById(R.id.buttonBackToAuto_u);
        buttonBackToAuto.setOnClickListener(this);

        buttonRedUser = findViewById(R.id.buttonRedUser);
        buttonRedUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonChMeat:
                startActivity(new Intent(this, chMeat.class));
                break;

            case R.id.buttonChDrinks:
                startActivity(new Intent(this, chDrink.class));
                break;

            case R.id.buttonBackToAuto:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.buttonRedUser:
                startActivity(new Intent(this, redUser.class));
                break;

            case R.id.buttonBackToAuto_u:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }
}