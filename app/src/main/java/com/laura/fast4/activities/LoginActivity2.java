package com.laura.fast4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.laura.fast4.R;
import com.laura.fast4.activities.Cliente.MapClientActivity;
import com.laura.fast4.activities.Cliente.RegisterActivity2;
import com.laura.fast4.activities.Conductor.MapDriverActivity;
import com.laura.fast4.includes.MyToolBar;

public class LoginActivity2 extends AppCompatActivity {

    SharedPreferences mPref;
    FirebaseAuth mAuth;
    TextInputEditText txtInputEmail;
    TextInputEditText txtInputPassword;
    Button mButtonLogin;
    DatabaseReference mDataBase;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        MyToolBar.show(this, "Login de Usuario", true);

        txtInputEmail = findViewById(R.id.text_inputEmail);
        txtInputPassword = findViewById(R.id.text_InputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        //mDialog = new SpotsDialog.Builder().setContext(LoginActivity2.this).setMessage("Espere un momento").build;

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void login(){
        String email = txtInputEmail.getText().toString();
        String password = txtInputPassword.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()){
            //mDialog.show();
            if(password.length() >= 6) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user = mPref.getString("user", "");
                            if (user.equals("client")){
                                Intent intent = new Intent(LoginActivity2.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(LoginActivity2.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity2.this, "El email o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        //mDialog.dismiss();
                    }
                });
            }else{
                Toast.makeText(this, "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "La contraseña y el email son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }


}