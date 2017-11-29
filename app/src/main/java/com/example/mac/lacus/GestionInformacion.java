package com.example.mac.lacus;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
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
        marcador = (ImageView) findViewById(R.id.marcadoranimado);
        Drawable drawable = marcador.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionInformacion.this, FVeeduria.class);
                startActivity(intent);
                finish();            }
        });

    }
}
