package com.example.mac.lacus;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ForgotPassword extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_forgot_password);



        ///////////////////////////////////////////////////////////////
        //set del vector animado
        /*if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }*/
        //se cierra la animación
        ////////////////////////////////////////////////////////////////
    }
}
