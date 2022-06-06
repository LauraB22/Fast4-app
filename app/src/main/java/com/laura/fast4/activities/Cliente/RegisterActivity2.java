package com.laura.fast4.activities.Cliente;

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
import com.laura.fast4.models.Client;
import com.laura.fast4.models.Provider.AuthProvider;
import com.laura.fast4.models.Provider.ClientProvider;

public class RegisterActivity2 extends AppCompatActivity {
    SharedPreferences mPref;
    Button mButtonRegister;
    TextInputEditText minputnombre;
    TextInputEditText minputemail;
    TextInputEditText minputpassword;
    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mButtonRegister = findViewById(R.id.btnRegister);
        minputnombre = findViewById(R.id.txtNombre);
        minputemail = findViewById(R.id.txtInputCorreo);
        minputpassword = findViewById(R.id.txtInputPassword);
        MyToolBar.show(this, "Registro de Usuario", true);

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickRegister();
            }
        });

    }

    void ClickRegister(){
        String nombre = minputnombre.getText().toString();
        String email = minputemail.getText().toString();
        String password = minputpassword.getText().toString();

        if(!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(password.length() >= 6){
                register(nombre, email, password);
            }else{
                Toast.makeText(this, "La contraseña debe de tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    void register(final String nombre, final String email, String password){
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, nombre, email);
                    create(client);
                }else{
                    Toast.makeText(RegisterActivity2.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(RegisterActivity2.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(RegisterActivity2.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*
    public void saveUser(String id, String nombre, String email){
        String selectedUser = mpref.getString("user", "");
        User user= new User();
        user.setEmail(email);
        user.setName(nombre);

        if(selectedUser.equals("driver")){
            mDatabase.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity2.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity2.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(selectedUser.equals("client")){
            mDatabase.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity2.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity2.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
}