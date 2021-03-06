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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

    private ArrayList<String> denuncias;

    /**
     * API MAPAS.
     */

    private GoogleMap mMap;

    private Marker marcador;

    //private CameraPosition posicionCamara;

    private String marcadorActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcador);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapamarcador);
        mapFragment.getMapAsync(this);

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

        denuncias = new ArrayList<String>();

        marcador = null;

        // Cámara del marcador del usuario.
        //posicionCamara = null;

        marcadorActual = "";

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

        /**
         * SE PIDE LA INFORMACIÓN DE LA BASE DE DATOS.
         */

        final DatabaseReference temporalRef = database.getReference("temporal").child("usuario 1");

        temporalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Obtener la información de un child en específico.
                /*String ubicacion = dataSnapshot.getValue().toString();
                Log.d("holi", ubicacion);*/

                Log.d("holi", "Número de denuncias: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot denunciasSnapshot: dataSnapshot.getChildren()) {

                    String ubicacion = denunciasSnapshot.getValue().toString();
                    denuncias.add(ubicacion);
                    Log.d("holi", ubicacion);

                }

                Log.d("holi", "Salío del for");

                marcadorActual = denuncias.get(0);

                Log.d("holi", denuncias.get(0));

                String[] partes = marcadorActual.split(",");

                String parte1 = partes[0];
                String parte2 = partes[1];

                LatLng marcador = new LatLng(Double.parseDouble(parte1), Double.parseDouble(parte2));

                mMap.addMarker(new MarkerOptions()
                        .position(marcador)
                        .title("Marcador")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 17));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*marcadorActual = denuncias.get(0);

        String[] partes = marcadorActual.split(",");

        String parte1 = partes[0];
        String parte2 = partes[1];

        LatLng marcador = new LatLng(Double.parseDouble(parte1), Double.parseDouble(parte2));

        mMap.addMarker(new MarkerOptions()
                .position(marcador)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

        posicionCamara = new CameraPosition.Builder()
                .target(marcador)
                .zoom(17)
                .build();*/

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.senales: /** Start a new Activity MyCards.java */

                DatabaseReference temporalRef1 = database.getReference();

                // Guardar en la base de datos.
                temporalRef1.child("temporalDenun").child("usuario 1").child("geopos").setValue(marcadorActual);
                temporalRef1.child("temporalDenun").child("usuario 1").child("categoria").setValue("Señalización");

                Intent intent = new Intent(this, SeleccionarProblema.class);
                intent.putExtra("señalizacionID", "señalizacion");
                this.startActivity(intent);

                break;

            case R.id.cultura: /** Start a new Activity MyCards.java */

                DatabaseReference temporalRef2 = database.getReference();

                // Guardar en la base de datos.
                temporalRef2.child("temporalDenun").child("usuario 1").child("geopos").setValue(marcadorActual);
                temporalRef2.child("temporalDenun").child("usuario 1").child("categoria").setValue("Cultura");

                Intent intent2 = new Intent(Marcador.this, SeleccionarProblema.class);
                intent2.putExtra("culturaID","cultura");
                startActivity(intent2);

                break;

            case R.id.infraestructura: /** Start a new Activity MyCards.java */

                DatabaseReference temporalRef3 = database.getReference();

                // Guardar en la base de datos.
                temporalRef3.child("temporalDenun").child("usuario 1").child("geopos").setValue(marcadorActual);
                temporalRef3.child("temporalDenun").child("usuario 1").child("categoria").setValue("Infraestructura");

                Intent intent3 = new Intent(Marcador.this, SeleccionarProblema.class);
                intent3.putExtra("infraestructuraID","infraestructura");
                startActivity(intent3);

                break;

            case R.id.seguridad: /** Start a new Activity MyCards.java */

                DatabaseReference temporalRef4 = database.getReference();

                // Guardar en la base de datos.
                temporalRef4.child("temporalDenun").child("usuario 1").child("geopos").setValue(marcadorActual);
                temporalRef4.child("temporalDenun").child("usuario 1").child("categoria").setValue("Seguridad");

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
