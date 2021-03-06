package com.example.mac.lacus;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class GestionInformacion extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    //ImageView marcador;

    /**
     * API MAPAS.
     */

    private GoogleMap mMap;

    private Marker marcadorA;

    //private CameraPosition posicionCamara;

    private String marcadorActual;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    private String idCaso;

    private LatLng marcador;

    private TextView tituloGestion, estadoGestion, descripcionGestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_informacion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapagestion);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionInformacion.this, MapsActivity.class);
                startActivity(intent);
                finish();            }
        });

        database = FirebaseDatabase.getInstance();

        idCaso = getIntent().getStringExtra("ID_CASO");

        Log.d("GESTIONINFORMACION", idCaso);

        tituloGestion = (TextView) findViewById(R.id.titulo_gestion);
        estadoGestion = (TextView) findViewById(R.id.estado_gestion);
        descripcionGestion = (TextView) findViewById(R.id.descripcion_gestion);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Log.d("holi", "Se creó el mapa.");

        //LatLng marcador = new LatLng(3.4810738753592116, -76.49860471487045);

        /**
         * SE PIDE LA INFORMACIÓN DE LA BASE DE DATOS.
         */

        final DatabaseReference temporalRef = database.getReference("veeduria").child(idCaso);

        temporalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CasoVeeduria caso = dataSnapshot.getValue(CasoVeeduria.class);

                marcadorActual = caso.getGeopos();

                String[] partes = marcadorActual.split(",");

                String parte1 = partes[0];
                String parte2 = partes[1];

                marcador = new LatLng(Double.parseDouble(parte1), Double.parseDouble(parte2));

                mMap.addMarker(new MarkerOptions()
                        .position(marcador)
                        .title("Marcador")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 17));

                tituloGestion.setText(caso.getNombre());
                estadoGestion.setText(caso.getEstado());
                descripcionGestion.setText(caso.getDescripcion());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*mMap.addMarker(new MarkerOptions()
                .position(marcador)
                .title("Marcador")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 17));*/

    }

}
