package com.laura.fast4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laura.fast4.R;
import com.laura.fast4.activities.Cliente.RegisterActivity2;
import com.laura.fast4.activities.Conductor.RegisterDriverActivity;
import com.laura.fast4.includes.MyToolBar;

public class SelectOptionAuthActivity2 extends AppCompatActivity {

    SharedPreferences mPref;
    Button btnLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth2);
        MyToolBar.show(this, "Selecciona una opción", true);

        btnLogin = findViewById(R.id.btnGoLogin);
        btnRegister = findViewById(R.id.btnGoRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
    }

    public void goToLogin(){
        Intent intent= new Intent(SelectOptionAuthActivity2.this, LoginActivity2.class);
        startActivity(intent);
    }

    public void goToRegister(){
        String typeUser = mPref.getString("User", "");
        Intent intent;
        if(typeUser.equals("Client")){
            intent = new Intent(SelectOptionAuthActivity2.this, RegisterActivity2.class);
        }else{
            intent = new Intent(SelectOptionAuthActivity2.this, RegisterDriverActivity.class);
        }
        startActivity(intent);

    }
}