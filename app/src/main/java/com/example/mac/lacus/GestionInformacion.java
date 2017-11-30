package com.example.mac.lacus;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class GestionInformacion extends AppCompatActivity {

    Toolbar toolbar;
    ImageView marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_informacion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ///////////////////////////////////////////////////////////////
        //set del vector animado
        ///////////////////////////////////////////////////////////////
        //set del vector animado
        ImageView marcaanimada = (ImageView) findViewById(R.id.marcadoranimado);
        Drawable d = marcaanimada.getDrawable();
        //marcadoranimado.setImageDrawable(drawable);
        if(d instanceof AnimatedVectorDrawableCompat){
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
            avd.start();
        } else if (d instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
            avd.start();
        }
        //se cierra la animación
        ////////////////////////////////////////////////////////////////


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionInformacion.this, FVeeduria.class);
                startActivity(intent);
                finish();            }
        });

    }
}
