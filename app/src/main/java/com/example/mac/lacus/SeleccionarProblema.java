package com.example.mac.lacus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SeleccionarProblema extends AppCompatActivity {
    private String categoria1;

    ImageView categoriaImg;
    TextView categoriaTxt;
    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_problema);
        categoriaTxt = (TextView) findViewById(R.id.titulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boton1 = (Button) findViewById(R.id.boton1);
        boton2 = (Button) findViewById(R.id.boton2);
        boton3 = (Button) findViewById(R.id.boton3);

        categoriaImg = (ImageView) findViewById(R.id.categoriaImg);
        Intent intent = getIntent();
        String señalizacion = intent.getStringExtra("señalizacionID");
        String infraestructura = intent.getStringExtra("infraestructuraID");
        String cultura = intent.getStringExtra("culturaID");
        String seguridad = intent.getStringExtra("seguridadID");

        System.out.println(señalizacion);
        if(señalizacion != null && señalizacion.contains("señalizacion")){
            categoriaImg.setImageResource(R.drawable.ic_senales);
            categoriaTxt.setText("Señalizacion");
            boton1.setText("Falta de señalizaciones");
            boton2.setText("Señalización errónea");
            boton3.setText("No hay separadores viales");
        }

        if(infraestructura != null && infraestructura.contains("infraestructura")){
            categoriaImg.setImageResource(R.drawable.ic_infraestructura);
            categoriaTxt.setText("Infraestructura");
            boton1.setText("Calle en mal estado");
            boton2.setText("Falta de iluminación");
            boton3.setText("Vías de alto riesgo");
//            boton4.setText("Hueco o cráter peligroso");
        }

        if(cultura != null && cultura.contains("cultura")){
            categoriaImg.setImageResource(R.drawable.ic_culture);
            categoriaTxt.setText("Cultura");
            boton1.setText("Peatones sin cuidado");
            boton2.setText("Irrespeto por el ciclista");
            boton3.setText("Invasión constante de carril");
        }

        if(seguridad != null && seguridad.contains("seguridad")){
            categoriaImg.setImageResource(R.drawable.ic_seguridad);
            categoriaTxt.setText("Seguridad");
            boton1.setText("Alto índice de robo");
            boton2.setText("Locación hostil");
            boton3.setText("Probabilidad de accidentes");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarProblema.this, MapsActivity.class);
                startActivity(intent);
                finish();            }
        });
    }

    // String getName = (String) bd.get("señalizacion");




    public void interpretarCategoria(){
        if(categoria1.contains("cultura")){
        }
    }
}
