package com.example.mac.lacus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import static com.example.mac.lacus.R.id.senales;

public class Marcador extends AppCompatActivity implements View.OnClickListener{

    private String categoria = "categoria";
    private Button senalizacion;
    private Button cultura;
    private Button seguridad;
    private Button infraestructura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cultura = (Button) findViewById(R.id.cultura);
        senalizacion = (Button) findViewById(R.id.senales);
        seguridad = (Button) findViewById(R.id.seguridad);
        infraestructura = (Button) findViewById(R.id.infraestructura);

        cultura.setOnClickListener(this);
        senalizacion.setOnClickListener(this);
        seguridad.setOnClickListener(this);
        infraestructura.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Marcador.this, MapsActivity.class);
                startActivity(intent);
                finish();            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.senales: /** Start a new Activity MyCards.java */
                Intent intent = new Intent(this, SeleccionarProblema.class);
                intent.putExtra("señalizacionID", "señalizacion");
                this.startActivity(intent);
                break;

            case R.id.cultura: /** Start a new Activity MyCards.java */
                Intent intent2 = new Intent(Marcador.this, SeleccionarProblema.class);
                intent2.putExtra("culturaID","cultura");
                startActivity(intent2);
                break;

            case R.id.infraestructura: /** Start a new Activity MyCards.java */
                Intent intent3 = new Intent(Marcador.this, SeleccionarProblema.class);
                intent3.putExtra("infraestructuraID","infraestructura");
                startActivity(intent3);
                break;

            case R.id.seguridad: /** Start a new Activity MyCards.java */
                Intent intent4 = new Intent(Marcador.this, SeleccionarProblema.class);
                intent4.putExtra("seguridadID","seguridad");
                startActivity(intent4);
                break;

        }
    }

    public void pasarSeleccionar(){
        Intent intent = new Intent(Marcador.this, SeleccionarProblema.class);
        intent.putExtra("seguridad",categoria);
        startActivity(intent);
    }
}
