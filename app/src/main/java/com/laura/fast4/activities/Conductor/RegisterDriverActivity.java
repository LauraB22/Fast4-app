package com.laura.fast4.activities.Conductor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.laura.fast4.R;
import com.laura.fast4.includes.MyToolBar;
import com.laura.fast4.models.Driver;
import com.laura.fast4.models.Provider.AuthProvider;
import com.laura.fast4.models.Provider.DriverProvider;

public class RegisterDriverActivity extends AppCompatActivity {

    Button mButtonRegister;
    TextInputEditText minputname;
    TextInputEditText minputemail;
    TextInputEditText minputpassword;
    TextInputEditText minputModelo;
    TextInputEditText minputPlaca;
    AuthProvider mAuthProvider;
    DriverProvider mDriverProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        mButtonRegister = findViewById(R.id.btnRegister);
        minputname = findViewById(R.id.txtname);
        minputemail = findViewById(R.id.txtInputEmail);
        minputModelo = findViewById(R.id.txtmarcaVehiculo);
        minputPlaca = findViewById(R.id.txtPlacaVehiculo);
        minputpassword = findViewById(R.id.txtInput_Password);

        MyToolBar.show(this, "Registro de Conductor", true);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickRegister();
            }
        });

    }

    public void ClickRegister(){
        final String name = minputname.getText().toString();
        final String email = minputemail.getText().toString();
        final String vehicleBrand = minputModelo.getText().toString();
        final String vehiclePlate = minputPlaca.getText().toString();
        final String password = minputpassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !vehicleBrand.isEmpty() && !vehiclePlate.isEmpty()){
            if(password.length() >= 6){
                register(name, email, password, vehicleBrand, vehiclePlate);
            }else{
                Toast.makeText(this, "La contraseña debe de tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String name, final String email, String password, String vehiculoModelo, String PlacaVehiculo){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Driver driver = new Driver(id, name, email, vehiculoModelo, PlacaVehiculo);
                    create(driver);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Driver driver){
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(RegisterDriverActivity.this, "El registro se realizo exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterDriverActivity.this, MapDriverActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterDriverActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}