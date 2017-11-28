package com.example.mac.lacus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SeleccionarProblema extends AppCompatActivity implements View.OnClickListener {
    private String categoria1;

    ImageView categoriaImg;
    TextView categoriaTxt;
    Button boton1;
    Button boton2;
    Button boton3;
    Button boton4;
    private boolean señalizacionB;
    private boolean infraestructuraB;
    private boolean culturaB;
    private boolean seguridadB;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_problema);
        categoriaTxt = (TextView) findViewById(R.id.categoriaTxt);
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
            señalizacionB = true;
        } else{
            señalizacionB = false;
        }

        if(infraestructura != null && infraestructura.contains("infraestructura")){
            categoriaImg.setImageResource(R.drawable.ic_infraestructura);
            categoriaTxt.setText("Infraestructura");
            boton1.setText("Calle en mal estado");
            boton2.setText("Falta de iluminación");
            boton3.setText("Vías de alto riesgo");
//            boton4.setText("Hueco o cráter peligroso");
            infraestructuraB = true;
        } else{
            infraestructuraB = false;
        }

        if(cultura != null && cultura.contains("cultura")){
            categoriaImg.setImageResource(R.drawable.ic_culture);
            categoriaTxt.setText("Cultura");
            boton1.setText("Peatones poco cívicos");
            boton2.setText("Irrespeto por el ciclista");
            boton3.setText("Invasión constante de carril");
            culturaB = true;
        } else{
            culturaB = false;
        }

        if(seguridad != null && seguridad.contains("seguridad")){
            categoriaImg.setImageResource(R.drawable.ic_seguridad);
            categoriaTxt.setText("Seguridad");
            boton1.setText("Alto índice de robo");
            boton2.setText("Locación hostil");
            boton3.setText("Probabilidad de accidentes");
            seguridadB = true;
        } else{
            seguridadB = false;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarProblema.this, Marcador.class);
                startActivity(intent);
                finish();            }
        });

        database = FirebaseDatabase.getInstance();

    }

    /*public void interpretarCategoria(){
        if(categoria1.contains("cultura")){
        }
    }*/

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.boton1: /** Start a new Activity MyCards.java */

                Intent intent = new Intent(this, GraciasPorContribuir.class);

                // Referencias base de datos.
                DatabaseReference temporalRef1 = database.getReference();

                if(señalizacionB == true){
                    temporalRef1.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Falta de señalización");
                    intent.putExtra("señalizacionB1", "Falta de señalización");
                }
                if(culturaB == true) {
                    temporalRef1.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Peatones poco cívicos");
                    intent.putExtra("culturaB1", "Peatones poco cívicos");
                }
                if(seguridadB == true){
                    temporalRef1.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Alto índice de robo");
                    intent.putExtra("seguridadB1", "Alto índice de robo");
                }
                if(infraestructuraB == true){
                    temporalRef1.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Calle en mal estado");
                    intent.putExtra("infraestructuraB1", "Calle en mal estado");
                }

                this.startActivity(intent);

                break;

            case R.id.boton2: /** Start a new Activity MyCards.java */

                Intent intent2 = new Intent(SeleccionarProblema.this, GraciasPorContribuir.class);

                // Referencias base de datos.
                DatabaseReference temporalRef2 = database.getReference();

                if(señalizacionB == true){
                    temporalRef2.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Señalización errónea");
                    intent2.putExtra("señalizacionB2", "Señalización errónea");
                }
                if(culturaB == true) {
                    temporalRef2.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Irrespeto por el ciclista");
                    intent2.putExtra("culturaB2", "Irrespeto por el ciclista");
                }
                if(seguridadB == true){
                    temporalRef2.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Locación hostil");
                    intent2.putExtra("seguridadB2", "Locación hostil");
                }
                if(infraestructuraB == true){
                    temporalRef2.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Falta de iluminación");
                    intent2.putExtra("infraestructuraB2", "Falta de iluminación");
                }
                startActivity(intent2);
                break;

            case R.id.boton3: /** Start a new Activity MyCards.java */

                Intent intent3 = new Intent(SeleccionarProblema.this, GraciasPorContribuir.class);

                // Referencias base de datos.
                DatabaseReference temporalRef3 = database.getReference();

                System.out.println("entró");
                if(señalizacionB == true){
                    temporalRef3.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("No hay separadores viales");
                    intent3.putExtra("señalizacionB3", "No hay separadores viales");
                }
                if(culturaB == true) {
                    temporalRef3.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Invasión constante de carril");
                    intent3.putExtra("culturaB3", "Invasión constante de carril");
                }
                if(seguridadB == true){
                    temporalRef3.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Probabilidad de accidentes");
                    intent3.putExtra("seguridadB3", "Probabilidad de accidentes");
                }
                if(infraestructuraB == true){
                    temporalRef3.child("temporalDenun").child("usuario 1").child("problema")
                            .setValue("Vías de alto riesgo");
                    intent3.putExtra("infraestructuraB3", "Vías de alto riesgo");
                }
                startActivity(intent3);
                break;

           /* case R.id.boton4: /** Start a new Activity MyCards.java */
           //     Intent intent4 = new Intent(SeleccionarProblema.this, SeleccionarProblema.class);
             //   intent4.putExtra("seguridadID", "seguridad");
               // startActivity(intent4);
                //break;
        }
    }
}
