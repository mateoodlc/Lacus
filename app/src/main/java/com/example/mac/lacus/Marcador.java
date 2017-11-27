package com.example.mac.lacus;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mac.lacus.R.id.senales;

public class Marcador extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private String categoria = "categoria";
    private Button senalizacion;
    private Button cultura;
    private Button seguridad;
    private Button infraestructura;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    /**
     * API MAPAS.
     */

    private GoogleMap mMap;

    private Marker marcador;

    private CameraPosition posicionCamara;

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

        database = FirebaseDatabase.getInstance();

        marcador = null;

        // Cámara del marcador del usuario.
        posicionCamara = null;

        final DatabaseReference temporalRef = database.getReference("temporal").child("usuario 1");

        temporalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ArrayList<String> lista = new ArrayList<String>();
                //Log.d("holi", "Se creó el ArrayList");

                /*for(DataSnapshot ubiSnapshot: dataSnapshot.getChildren()) {

                    String ubicacion = ubiSnapshot.getValue().toString();
                    Log.d("holi", ubicacion);

                }*/

                // Obtener la información de un child en específico.
                /*String ubicacion = dataSnapshot.getValue().toString();
                Log.d("holi", ubicacion);*/

                Log.d("holi", "Número de denuncias: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot denunciasSnapshot: dataSnapshot.getChildren()) {

                    String ubicacion = denunciasSnapshot.getValue().toString();
                    Log.d("holi", ubicacion);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.d("holi", "Se creó el mapa.");

        final DatabaseReference temporalRef = database.getReference("temporal").child("usuario 1");

        temporalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> lista = new ArrayList<String>();
                Log.d("holi", "Se creó el ArrayList");

                for(DataSnapshot ubiSnapshot: dataSnapshot.getChildren()) {

                    String ubicacion = ubiSnapshot.getValue().toString();
                    Log.d("holi", ubicacion);

                }

                //String ubicacion = dataSnapshot.getValue().toString();
                //Log.d("holi", ubicacion);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
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
