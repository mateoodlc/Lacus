package com.example.mac.lacus;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;


import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;

/*
   Tutorial "Set Up Google Play Services":
   https://developers.google.com/android/guides/setup

   Tutorial general "Making Your App Location-Aware":
   https://developer.android.com/training/location/index.html?hl=es-419

   Tutorial "Getting the Last Known Location":
   https://developer.android.com/training/location/retrieve-current.html?hl=es-419#play-services

   Tutorial "Changing Location Settings":
   https://developer.android.com/training/location/change-location-settings.html?hl=es-419#location-request

   Tutorial "Receiving Location Updates":
   https://developer.android.com/training/location/receive-location-updates.html?hl=es-419#get-last-location

   Código ejemplo "LocationUpdates":
   https://github.com/googlesamples/android-play-location/tree/master/LocationUpdates
*/

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button buttonComenzarRuta;
    private Button buttonMarcarDenuncia;

    FirebaseAuth auth;
    RelativeLayout dashboard_activity;

    private FMapa.OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FMapa.
     */
    // TODO: Rename and change types and number of parameters
 /*   public static MapsActivity newInstance(String param1, String param2) {
        FMapa fragment = new FMapa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/

    public static MapsActivity newInstance(String s) {
        MapsActivity fragment = new MapsActivity();
        // Set the arguments.
        Bundle bundle = new Bundle();
        bundle.putString(KEY, s);

        //fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * BASE DE DATOS.
     */

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    /**
     * API MAPAS.
     */

    private GoogleMap mMap;

    private Marker marcadorUsuario;

    private Marker marcadorNorte, marcadorSur, marcadorOriente, marcadorOccidente;

    private Circle circuloMarcador;

    private CircleOptions circuloOptions;

    private CameraPosition posicionCamara;

    private boolean rutaActivada;

    /**
     * CONSTANTES.
     */

    // Variable para permitir la localización mediante "My Location".
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    // "Changing Location Settings" Constante usada en el diálogo de las opciones de localización.
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    // El intervalo de tiempo deseado para las actualizaciones de localización. Inexacto.
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // El intervalo más rápido para las actualizaciónes de localización. Exacto.
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // "Receiving Location Updates" Llave para guardar el estado de la actividad en el Bundle.
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates";

    /**
     * VARIABLES.
     */

    // "Getting the Last Known Location" Instancia del API de Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationClient;

    // Provides access to the Location Settings API.
    private SettingsClient mSettingsClient;

    // "Changing Location Settings" Crear el request de ubicación para FusedLocationProviderApi.
    private LocationRequest mLocationRequest;

    // Stores the types of location services the client is interested in using. Used for checking
    // settings to determine if the device has optimal location settings.
    private LocationSettingsRequest mLocationSettingsRequest;

    // "Receiving Location Updates" Llamada de vuelta para los eventos de Location.
    private LocationCallback mLocationCallback;

    // "Receiving Location Updates" Variable global que representa la ubicación geográfica.
    private Location ubicacionActual;

    // Verificar la ubicación anterior.
    private Location ubicacionAnterior;

    // ArrayList donde se guardan las ubicaciones de las denuncias.
    private ArrayList<Location> denunciasMarcadas;

    // ArrayList que almacena las posiciones de la ruta del usuario.
    private ArrayList<LatLng> rutaUsuario;

    private Polyline rutaPolyline;

    // "Receiving Location Updates" Tracks the status of the location updates request. Value changes
    // when the user presses the Start Updates and Stop Updates buttons.
    private Boolean mRequestingLocationUpdates;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                   // setContentView(R.layout.activity_maps);
                    transaction.replace(R.id.content, new FMapa()).commit();
                    buttonComenzarRuta.setVisibility(View.VISIBLE); //To set visible
                    buttonMarcarDenuncia.setVisibility(View.VISIBLE); //To set visible
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_estadisticas:
                    transaction.replace(R.id.content, new FEstadisticas()).commit();
                    buttonComenzarRuta.setVisibility(View.INVISIBLE); //To set invisible
                    buttonMarcarDenuncia.setVisibility(View.INVISIBLE); //To set invisible
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_veeduria:
                    transaction.replace(R.id.content, new FVeeduria()).commit();
                   // Intent intent = new Intent(MapsActivity.this, Veeduria.class);
                    //startActivity(intent);
                    //  finish();
                    buttonComenzarRuta.setVisibility(View.INVISIBLE); //To set invisible
                    buttonMarcarDenuncia.setVisibility(View.INVISIBLE); //To set invisible
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_configuracion:
                    transaction.replace(R.id.content, new FConfiguraciones()).commit();
                    buttonComenzarRuta.setVisibility(View.INVISIBLE); //To set invisible
                    buttonMarcarDenuncia.setVisibility(View.INVISIBLE); //To set invisible
                    // BottomNavigationViewHelper.disableShiftMode(navigation_configuracion);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * Inicialización de la actividad y el mapa.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content);
        mapFragment.getMapAsync(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        /**
         * MATEO. Inicialización de los fragments.
         */

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new FMapa()).commit();


        //dashboard_activity = (RelativeLayout) findViewById(R.id.activity_dashboard_t);
        auth = FirebaseAuth.getInstance();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity.this, "Has cerrado sesión", Toast.LENGTH_LONG).show();
                logOut();
            }
        });
        if (auth.getCurrentUser() != null) {
            Toast.makeText(MapsActivity.this, "Bienvenido, " + auth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        }

        /**
         * Inicialización de variables.
         */

        // "Receiving Location Updates".
        mRequestingLocationUpdates = true;

        // "Receiving Location Updates".
        updateValuesFromBundle(savedInstanceState);

        // "Getting the Last Known Location" Iniciar variable.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mSettingsClient = LocationServices.getSettingsClient(this);

        // Iniciar el marcador del usuario.
        marcadorUsuario = null;

        marcadorNorte = null;
        marcadorSur = null;
        marcadorOriente = null;
        marcadorOccidente = null;

        // Iniciar el círculo que rodea la posición.
        circuloMarcador = null;

        // Opciones del círculo inicialmente.
        circuloOptions = new CircleOptions()
                .center(new LatLng(0, 0))
                .radius(20)
                .fillColor(Color.RED)
                .strokeColor(Color.TRANSPARENT);

        // Cámara del marcador del usuario.
        posicionCamara = null;

        // Seguimiento de la cámara, lineamiento de ruta.
        rutaActivada = false;

        // Ubicación actual vacía.
        ubicacionActual = null;

        // Ubicación anterior vacía.
        ubicacionAnterior = null;

        // Inicialización del ArrayList de ubicaciones marcadas.
        denunciasMarcadas = new ArrayList<Location>();

        // Inicialización del ArrayList de la ruta del usuario.
        rutaUsuario = new ArrayList<LatLng>();

        // Polilínea de la ruta del usuario.
        rutaPolyline = null;

        createLocationCallback();

        // "Changing Location Settings".
        createLocationRequest();

        buildLocationSettingsRequest();

        /**
         * Botones.
         */

        buttonComenzarRuta = (Button) findViewById(R.id.button_comenzar_ruta);
        buttonComenzarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambiar el booleano rutaActivada.
                if (rutaActivada) {

                    rutaActivada = false;

                    rutaPolyline = mMap.addPolyline(new PolylineOptions().addAll(rutaUsuario));

                    // Animación de vuelta a la posición.
                    v.animate().translationY(0).setDuration(500).start();
                    // Cambiar las propiedades.
                    buttonComenzarRuta.setText("Comienza tu ruta");
                    buttonComenzarRuta.setTextColor(Color.parseColor("#000000"));
                    buttonComenzarRuta.setBackgroundResource(R.drawable.button_bg_comenzar_ruta);
                    buttonComenzarRuta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_prender_gps, 0, 0, 0);

                } else {

                    rutaActivada = true;
                    denunciasMarcadas = new ArrayList<Location>();
                    rutaUsuario = new ArrayList<LatLng>();
                    rutaPolyline = null;
                    actualizarCamaraUsuario();

                    // Animación de vuelta a la parte de abajo.
                    v.animate().translationY(400).setDuration(500).start();
                    // Cambiar las propiedades.
                    buttonComenzarRuta.setText("Parar la ruta");
                    buttonComenzarRuta.setTextColor(Color.parseColor("#ffffff"));
                    buttonComenzarRuta.setBackgroundResource(R.drawable.button_bg_comenzar_ruta2);
                    buttonComenzarRuta.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_prender_gps2, 0, 0, 0);

                }

            }
        });

        buttonMarcarDenuncia = (Button) findViewById(R.id.button_marcar_denuncia);
        buttonMarcarDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Marcar el lugar respectivo para realizar la denuncia.
                if (rutaActivada) {

                    guardarDenuncia();

                }

            }
        });

    }

    // "Receiving Location Updates".
    @Override
    protected void onResume() {
        super.onResume();

        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        } else if (!checkPermissions()) {
            //requestPermissions();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Si el permiso fue otorgado.

            actualizarMarcadorUsuario();

        } else {
            // Si el permiso no fue otorgado.

            // Pedir el permiso.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }

    }

    // "Changing Location Settings" Método para pedir la ubicación más precisa.
    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    // Método inicializador para actualizar la ubicación del usuario.
    private void createLocationCallback() {

        // "Receiving Location Updates".
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                ubicacionActual = locationResult.getLastLocation();
                //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                Log.d("holi", "Actualizó ubicación");
                actualizarMarcadorUsuario();
            }

            ;
        };

    }

    // Método para actualizar la ubicación.
    private void actualizarMarcadorUsuario() {

        //onMapReady(mMap);
        obtenerUltimaUbicacion();

    }

    // Método para obtener la posición geográfica del usuario.
    private void obtenerUltimaUbicacion() {

        ubicacionAnterior = ubicacionActual;

        // "Getting the Last Known Location" Obtener última localización.
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        // Obtener la posición.
                        ubicacionActual = location;

                        LatLng ultimaUbicacion = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());

                        // Guardar la posición en la ruta.
                        if (rutaActivada) {
                            rutaUsuario.add(ultimaUbicacion);
                        }

                        // Dibujar la ubicación.
                        if (marcadorUsuario != null) {

                            marcadorUsuario.setPosition(ultimaUbicacion);

                            /*marcadorNorte.setPosition(new LatLng(ubicacionActual.getLatitude() + 0.0002, ubicacionActual.getLongitude()));
                            marcadorSur.setPosition(new LatLng(ubicacionActual.getLatitude() - 0.0002, ubicacionActual.getLongitude()));
                            marcadorOriente.setPosition(new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude() + 0.0002));
                            marcadorOccidente.setPosition(new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude() - 0.0002));*/

                            // Dibujar el círculo para demarcar un límite.
                            //circuloMarcador.setCenter(ultimaUbicacion);

                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ultimaUbicacion, 17));

                            if (rutaActivada) {
                                // Si la ruta está activada, centrar la cámara.
                                actualizarCamaraUsuario();

                            }

                        } else {

                            marcadorUsuario = mMap.addMarker(new MarkerOptions()
                                    .position(ultimaUbicacion)
                                    .title("Tú")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marcador_usuario)));
                            posicionCamara = new CameraPosition.Builder()
                                    .target(ultimaUbicacion)
                                    .zoom(17)
                                    .build();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ultimaUbicacion, 17));

                            //circuloMarcador = mMap.addCircle(circuloOptions);

                            /*marcadorNorte = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ubicacionActual.getLatitude() + 0.0002, ubicacionActual.getLongitude()))
                                    .title("Norte"));
                            marcadorSur = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ubicacionActual.getLatitude() - 0.0002, ubicacionActual.getLongitude()))
                                    .title("Sur"));
                            marcadorOriente = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude() + 0.0002))
                                    .title("Oriente"));
                            marcadorOccidente = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude() - 0.0002))
                                    .title("Occidente"));*/

                        }

                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

    }

    // Método para mover la cámara al punto donde está el usuario.
    private void actualizarCamaraUsuario() {

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(posicionCamara));

    }

    // Método para guardar la denuncia marcada localmente antes de definirla.
    private void guardarDenuncia() {

        // En el caso de no haber denuncias.
        /*if(denunciasMarcadas.size() == 0) {

            denunciasMarcadas.add(ubicacionActual);

            LatLng ultimaUbicacion = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());

            mMap.addMarker(new MarkerOptions()
                    .position(ultimaUbicacion)
                    .title("Marcador" + denunciasMarcadas.size())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

        } else { //Cuando existen denuncias.

            for(int i = 0; i < denunciasMarcadas.size(); i++) {

                if((ubicacionActual.getLatitude() > denunciasMarcadas.get(i).getLatitude() - 0.0002
                        && ubicacionActual.getLatitude() < denunciasMarcadas.get(i).getLatitude() + 0.0002)
                        && (ubicacionActual.getLongitude() > denunciasMarcadas.get(i).getLongitude() - 0.0002
                        && ubicacionActual.getLongitude() < denunciasMarcadas.get(i).getLongitude() + 0.0002)) {

                    //No marcar.
                    Log.d("holi", "No marcar. Está dentro del mismo rango.");

                } else {

                    denunciasMarcadas.add(ubicacionActual);

                    LatLng ultimaUbicacion = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());

                    mMap.addMarker(new MarkerOptions()
                            .position(ultimaUbicacion)
                            .title("Marcador" + denunciasMarcadas.size())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

                    Log.d("holi", "Se marca, está fuera o en un nuevo rango.");

                }

            }

        }*/

        denunciasMarcadas.add(ubicacionActual);

        LatLng ultimaUbicacion = new LatLng(ubicacionActual.getLatitude(), ubicacionActual.getLongitude());

        mMap.addMarker(new MarkerOptions()
                .position(ultimaUbicacion)
                .title("Marcador" + denunciasMarcadas.size())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_denuncia_marcada)));

        Log.d("holi", "Se marca, está fuera o en un nuevo rango.");

    }

    // Uno de los métodos inicializadores.
    private void buildLocationSettingsRequest() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

    }

    // "Receiving Location Updates" Método para reanudar la actualización de la ubicación.
    private void startLocationUpdates() {

        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        actualizarMarcadorUsuario();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        actualizarMarcadorUsuario();
                    }
                });

    }

    // "Receiving Location Updates" Método para pausar la actualización de la ubicación.
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    // "Receiving Location Updates".
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        // ...
        super.onSaveInstanceState(outState);
    }

    // "Receiving Location Updates".
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }

            // ...

            // Update UI to match restored state
            actualizarMarcadorUsuario();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    //Fragments setting
    private void setFragment(Fragment fr) {
        // Getting fragment instance from context
        android.support.v4.app.FragmentManager fManager = getSupportFragmentManager();
        // create Fragment transaction
        android.support.v4.app.FragmentTransaction fTransaction = fManager.beginTransaction();
        // Replacing fragment_container
        fTransaction.replace(R.id.content, fr);
        // Adding to back stack
        fTransaction.addToBackStack(null);
        // Commit so inflates the new fragment
        fTransaction.commit();
    }

    /*public void onClick(View v) {

    }*/

    private void logOut() {
        auth.signOut();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MapsActivity.this, MainActivity.class));
            finish();
        }
    }

}
