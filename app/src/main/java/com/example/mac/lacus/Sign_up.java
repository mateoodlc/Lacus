package com.example.mac.lacus;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Sign_up extends AppCompatActivity implements View.OnClickListener {

    EditText name, email, password, confirmation;
    Button signUp, alreadyAccount;
    ScrollView activity_sign_up;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_sign_up);



        signUp = (Button) findViewById(R.id.signUp_button);
        alreadyAccount = (Button) findViewById(R.id.alreadyaccount);

        name = (EditText) findViewById(R.id.input_nombre_signup);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password_signup);
        confirmation = (EditText) findViewById(R.id.input_password_signup_confirmation);

        activity_sign_up = (ScrollView) findViewById(R.id.activity_sign_up);

        signUp.setOnClickListener(this);
        alreadyAccount.setOnClickListener(this);

        //Init Firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signUp_button){
            signUpUser(email.getText().toString(), password.getText().toString());
        }
        else if(v.getId() == R.id.alreadyaccount){
            startActivity(new Intent(Sign_up.this, MainActivity.class));
            finish();
        }
    }

    private void signUpUser(String emailL, String passwordL) {
        auth.createUserWithEmailAndPassword(emailL, passwordL)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(password.length() < 8){
                                Toast.makeText(Sign_up.this, "La contraseña debe contener más de 6 caracteres", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(Sign_up.this, "Error al registrar los campos", Toast.LENGTH_LONG).show();
                            }
                    }else if(password.getText().toString().equals(confirmation.getText().toString())){
                            Toast.makeText(Sign_up.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Sign_up.this, MapsActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Sign_up.this, "Las contraseñas deben coincidir", Toast.LENGTH_LONG).show();
                        }
                };
        });
    }
}
