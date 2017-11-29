package com.example.mac.lacus;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import adapter.WelcomeAdapter;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] layouts = {R.layout.slide_uno, R.layout.slide_dos, R.layout.slide_tres};
    private WelcomeAdapter welcomeAdapter;

    private LinearLayout Puntos_Layout;
    private ImageView[] puntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(Build.VERSION.SDK_INT >= 19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

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
}
