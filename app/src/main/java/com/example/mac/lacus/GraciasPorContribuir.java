package com.example.mac.lacus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class GraciasPorContribuir extends AppCompatActivity implements View.OnClickListener {

    Button continuar;
    ImageView categoriaImg;
    TextView tipoCategoria;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gracias_por_contribuir);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        continuar = (Button) findViewById(R.id.continuar);
        categoriaImg = (ImageView) findViewById(R.id.categoriaImg);
        tipoCategoria = (TextView) findViewById(R.id.tipoCaso);


        //Strings para recibir los putExtra
        Intent intent = getIntent();
        String señalizacionB1 = intent.getStringExtra("señalizacionB1");
        String infraestructuraB1 = intent.getStringExtra("infraestructuraB1");
        String culturaB1 = intent.getStringExtra("culturaB1");
        String seguridadB1 = intent.getStringExtra("seguridadB1");

        String señalizacionB2 = intent.getStringExtra("señalizacionB2");
        String infraestructuraB2 = intent.getStringExtra("infraestructuraB2");
        String culturaB2 = intent.getStringExtra("culturaB2");
        String seguridadB2 = intent.getStringExtra("seguridadB2");

        String señalizacionB3 = intent.getStringExtra("señalizacionB3");
        String infraestructuraB3 = intent.getStringExtra("infraestructuraB3");
        String culturaB3 = intent.getStringExtra("culturaB3");
        String seguridadB3 = intent.getStringExtra("seguridadB3");


        //Condicionales para cuando seleccionen el primer botón de cualquier categoría
        if(señalizacionB1 != null && señalizacionB1.contains("Falta de señalización")){
            categoriaImg.setImageResource(R.drawable.ic_senales);
            tipoCategoria.setText("Falta de señalización");
        } else if(señalizacionB2 != null && señalizacionB2.contains("Señalización errónea")){
            categoriaImg.setImageResource(R.drawable.ic_senales);
            tipoCategoria.setText("Señalización errónea");
        } else if(señalizacionB3 != null && señalizacionB3.contains("No hay separadores viales")){
            categoriaImg.setImageResource(R.drawable.ic_senales);
            tipoCategoria.setText("No hay separadores viales");
        }

        if(infraestructuraB1 != null && infraestructuraB1.contains("Calle en mal estado")){
            categoriaImg.setImageResource(R.drawable.ic_infraestructura);
            tipoCategoria.setText("Calle en mal estado");

        } else if(infraestructuraB2 != null && infraestructuraB2.contains("Falta de iluminación")){
            categoriaImg.setImageResource(R.drawable.ic_infraestructura);
            tipoCategoria.setText("Falta de iluminación");
        } else if(infraestructuraB3 != null && infraestructuraB3.contains("Vías de alto riesgo")){
            categoriaImg.setImageResource(R.drawable.ic_infraestructura);
            tipoCategoria.setText("Vías de alto riesgo");
        }

        if(culturaB1 != null && culturaB1.contains("Peatones poco cívicos")){
            categoriaImg.setImageResource(R.drawable.ic_culture);
            tipoCategoria.setText("Peatones poco cívicos");

        } else if(culturaB2 != null && culturaB2.contains("Irrespeto por el ciclista")){
            categoriaImg.setImageResource(R.drawable.ic_culture);
            tipoCategoria.setText("Irrespeto por el ciclista");
        } else if(culturaB3 != null && culturaB3.contains("Invasión constante de carril")){
            categoriaImg.setImageResource(R.drawable.ic_culture);
            tipoCategoria.setText("Invasión constante de carril");
        }

        if(seguridadB1 != null && seguridadB1.contains("Alto índice de robo")){
            categoriaImg.setImageResource(R.drawable.ic_seguridad);
            tipoCategoria.setText("Alto índice de robo");
        } else if(seguridadB2 != null && seguridadB2.contains("Locación hostil")){
            categoriaImg.setImageResource(R.drawable.ic_seguridad);
            tipoCategoria.setText("Locación hostil");
        } else if(seguridadB3 != null && seguridadB3.contains("Probabilidad de accidentes")){
            categoriaImg.setImageResource(R.drawable.ic_seguridad);
            tipoCategoria.setText("Probabilidad de accidentes");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GraciasPorContribuir.this, MapsActivity.class);
                startActivity(intent);
                finish();            }
        });

        database = FirebaseDatabase.getInstance();

        /**
         * SE PIDE LA INFORMACIÓN DE LA BASE DE DATOS.
         */

        final DatabaseReference temporalRef = database.getReference("temporalDenun").child("usuario 1");

        temporalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Lectura de la denuncia temporal.
                String geopos = dataSnapshot.child("geopos").getValue().toString();
                String categoria = dataSnapshot.child("categoria").getValue().toString();
                String problema = dataSnapshot.child("problema").getValue().toString();

                // Crear una denuncia nueva.
                DatabaseReference denunciaReference = database.getReference();

                Denuncia denuncia = new Denuncia(geopos, categoria, problema, 1);

                denunciaReference.child("denuncias").push().setValue(denuncia);

                //denunciaReference.child("denuncias").push().child("geopos").setValue(geopos);
                //denunciaReference.child("denuncias").push().child("categoria").setValue(categoria);
                //denunciaReference.child("denuncias").push().child("problema").setValue(problema);
                //denunciaReference.child("denuncias").push().child("cantidad").setValue(1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.continuar: /** Start a new Activity MyCards.java */
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("señalizacionID", "señalizacion");
                this.startActivity(intent);
                break;
        }

    }
}
