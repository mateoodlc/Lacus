package com.example.mac.lacus;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ForgotPassword extends AppCompatActivity {

     private ImageView marcadoranimado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
