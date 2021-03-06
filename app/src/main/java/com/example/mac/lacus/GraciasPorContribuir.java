package com.example.mac.lacus;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GraciasPorContribuir extends AppCompatActivity implements View.OnClickListener {

    Button continuar;
    ImageView categoriaImg;
    TextView tipoCategoria;

    /**
     * BASE DE DATOS.
     */

    private FirebaseDatabase database;

    /**
     * VARIABLES.
     */

    ArrayList<String> denunciasKey;

    private boolean otroMarcador;

    // ArrayList que almacena todas las denuncias hechas en el mapa.
    private ArrayList<Denuncia> denunciasMapa;

    // Boolean para saber si hay que modificar un marcador en específico.
    private boolean modificar;

    // String que guarda temporalmente el id del marcador a modificar.
    private String idModificar;

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

        otroMarcador = false;

        denunciasKey = new ArrayList<String>();

        // Inicialización del ArrayList de las rutas totales del mapa.
        denunciasMapa = new ArrayList<Denuncia>();

        modificar = false;

        idModificar = "";

        /**
         * SE PIDE LA INFORMACIÓN DE LA BASE DE DATOS Y SE CREA LA DENUNCIA.
         */

        obtenerDenuncias();

        final DatabaseReference temporalRef = database.getReference("temporalDenun").child("usuario 1");

        temporalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Lectura de la denuncia temporal.
                String geopos = dataSnapshot.child("geopos").getValue().toString();
                String categoria = dataSnapshot.child("categoria").getValue().toString();
                String problema = dataSnapshot.child("problema").getValue().toString();

                /**
                 * CONDICIÓN PARA CREAR UNA O MODIFICAR UNA EXISTENTE.
                 */

                for(int i = 0; i < denunciasMapa.size(); i++) {

                    // Obtener las coordenadas de las denuncias guardadas.
                    String[] partes = denunciasMapa.get(i).getGeopos().split(",");

                    float parte1 = Float.parseFloat(partes[0]);
                    float parte2 = Float.parseFloat(partes[1]);

                    // Obtener las coordenadas de la denuncia actual a guardarse.
                    String[] partes2 = geopos.split(",");

                    float parte3 = Float.parseFloat(partes2[0]);
                    float parte4 = Float.parseFloat(partes2[1]);

                    // Si se encuentra en la misma posición.
                    if((parte3 > parte1 - 0.0001 && parte3 < parte1 + 0.0001)
                            && (parte4 > parte2 - 0.0001 && parte4 < parte2 + 0.0001)) {

                        // Si tiene el mismo problema, se modifica la existente.
                        if(denunciasMapa.get(i).getProblema().equals(problema)){

                            modificar = true;

                            idModificar = denunciasMapa.get(i).getId();

                            Log.d("GRACIAS", "ENCONTRÓ UN MARCADOR.");

                            modificarMarcador();

                            break;

                        }

                    } else if(i == (denunciasMapa.size() - 1) ){

                        // Crear una denuncia nueva.
                        DatabaseReference denunciaReference = database.getReference();

                        Denuncia denuncia = new Denuncia(geopos, categoria, problema, 1);

                        denunciaReference.child("denuncias").push().setValue(denuncia);

                    }

                }

                // Crear una denuncia nueva.
                /*DatabaseReference denunciaReference = database.getReference();

                Denuncia denuncia = new Denuncia(geopos, categoria, problema, 1);

                denunciaReference.child("denuncias").push().setValue(denuncia);*/

                //denunciaReference.child("denuncias").push().child("geopos").setValue(geopos);
                //denunciaReference.child("denuncias").push().child("categoria").setValue(categoria);
                //denunciaReference.child("denuncias").push().child("problema").setValue(problema);
                //denunciaReference.child("denuncias").push().child("cantidad").setValue(1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /**
         * VERIFICAR SI HAY MÁS MARCADORES.
         */

        final DatabaseReference temporalRef2 = database.getReference("temporal").child("usuario 1");

        temporalRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("gracias", "Número de denuncias: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot keySnapshot: dataSnapshot.getChildren()) {

                    String keyHijo = keySnapshot.getKey();
                    denunciasKey.add(keyHijo);
                    Log.d("holi", keyHijo);

                }

                Log.d("gracias", "Salío del for");

                // Si hay más denuncias temporales, hay otro marcador y elimina el primer
                // marcador temporal.
                if(denunciasKey.size() > 1){

                    temporalRef2.child(denunciasKey.get(0)).setValue(null);
                    otroMarcador = true;
                    Log.d("gracias", "TRUE");

                } else if(denunciasKey.size() == 1){ // Si queda una denuncia.

                    temporalRef2.child(denunciasKey.get(0)).setValue(null);
                    otroMarcador = false;
                    Log.d("gracias", "FALSE");

                } else { // Si no hay más denuncias, no hay otro marcador.

                    otroMarcador = false;
                    Log.d("gracias", "FALSE");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void obtenerDenuncias() {

        /**
         * SE PIDE LA INFORMACIÓN DE LA BASE DE DATOS.
         */

        final DatabaseReference temporalRef = database.getReference("denuncias");

        temporalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("holi", "Número de denuncias totales: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot denunciasSnapshot: dataSnapshot.getChildren()) {

                    Denuncia denuncia = denunciasSnapshot.getValue(Denuncia.class);
                    denuncia.setId(denunciasSnapshot.getKey());
                    // Se agrega al arreglo de DENUNCIAS.
                    denunciasMapa.add(denuncia);

                    Log.d("holi", denuncia.getId());

                }

                Log.d("holi", "Salío del for");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void modificarMarcador() {

        /**
         * MODIFICAR UN MARCADOR.
         */

        Log.d("GRACIAS", Boolean.toString(modificar));

        if(modificar == true) {

            Log.d("GRACIAS", "ENTRÓ AL MODIFICADOR.");

            final DatabaseReference temporalRef3 = database.getReference("denuncias").child(idModificar);

            temporalRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d("GRACIAS", "MODIFICÓ EL MARCADOR.");

                    Denuncia denuncia = dataSnapshot.getValue(Denuncia.class);

                    float cantidadNueva = denuncia.getCantidad() + 1;

                    // Crear una denuncia nueva.
                    //DatabaseReference denunciaReference = database.getReference("denuncias").child(idModificar);

                    temporalRef3.child("cantidad").setValue(cantidadNueva);

                    modificar = false;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } else {

            Log.d("GRACIAS", "NO SE MODIFICÓ EL MARCADOR.");

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.continuar: /** Start a new Activity MyCards.java */

                if(otroMarcador) {

                    startActivity(new Intent(GraciasPorContribuir.this, Marcador.class));
                    finish();

                } else {

                    final DatabaseReference temporalRef3 = database.getReference("temporalDenun").child("usuario 1");

                    temporalRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            temporalRef3.setValue(null);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra("señalizacionID", "señalizacion");
                    this.startActivity(intent);
                    finish();

                }

                break;
        }

    }
}
