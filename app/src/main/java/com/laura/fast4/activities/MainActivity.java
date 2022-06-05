package com.laura.fast4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laura.fast4.R;

public class MainActivity extends AppCompatActivity {

    Button imDriver;
    Button imUser;
    SharedPreferences mpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mpref = getApplicationContext().getSharedPreferences("typeuser", MODE_PRIVATE);
        SharedPreferences.Editor editor = mpref.edit();
        imDriver = findViewById(R.id.btnDriver);
        imUser = findViewById(R.id.btnUser);

        imDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "driver");
                editor.apply();
                goToSelectAuth();

            }
        });

        imUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("user", "client");
                editor.apply();
                goToSelectAuth();
            }
        });
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity2.class);
        startActivity(intent);
    }
}