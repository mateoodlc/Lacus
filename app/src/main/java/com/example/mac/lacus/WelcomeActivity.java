package com.example.mac.lacus;

import android.content.Intent;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import adapter.WelcomeAdapter;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private int[] layouts = {R.layout.slide_uno, R.layout.slide_dos, R.layout.slide_tres};
    private WelcomeAdapter welcomeAdapter;

    private LinearLayout Puntos_Layout;
    private ImageView[] puntos;

    private Button siguiente;
    private Button saltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(new PreferenciasManager(this).checkearPreferencias()){
            //intentLogin();
        }

        setContentView(R.layout.activity_welcome);

        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        siguiente = (Button) findViewById(R.id.siguientebtn);
        saltar = (Button) findViewById(R.id.saltarbtn);

        siguiente.setOnClickListener(this);
        saltar.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.mviewpager);
        welcomeAdapter = new WelcomeAdapter(layouts, this);
        viewPager.setAdapter(welcomeAdapter);

        Puntos_Layout = (LinearLayout) findViewById(R.id.puntosLayout);
        crearPuntos(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                crearPuntos(position);
                if(position == layouts.length -1){
                    siguiente.setText("Empezar");
                    saltar.setVisibility(View.INVISIBLE);
                } else {
                    siguiente.setText("Siguiente");
                    saltar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void crearPuntos (int posicion_actual){

        if(Puntos_Layout != null){
            Puntos_Layout.removeAllViews();

            puntos = new ImageView[layouts.length];

            for (int i = 0; i < layouts.length; i++){
                puntos[i] = new ImageView(this);

                if(i == posicion_actual){
                    puntos[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.puntos_activos));
                } else {
                    puntos[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_puntos));

                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);

                Puntos_Layout.addView(puntos[i], params);

            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.siguientebtn:
                cargarSlideSiguiente();
                break;

            case R.id.saltarbtn:
                //intentLogin();
                new PreferenciasManager(this).escribirPreferencia();
                break;
        }
    }
    private void intentLogin (){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void cargarSlideSiguiente(){
        int slideSiguiente = viewPager.getCurrentItem()+1;

        if(slideSiguiente < layouts.length){
            viewPager.setCurrentItem(slideSiguiente);

        } else {
            System.out.println("ME ENTRÓ HASTA EL FONDO");
            intentLogin();
            new PreferenciasManager(this).escribirPreferencia();
        }

    }
}
