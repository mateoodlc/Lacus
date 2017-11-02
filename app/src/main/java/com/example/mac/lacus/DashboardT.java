/*package com.example.mac.lacus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.net.wifi.p2p.nsd.WifiP2pServiceRequest.newInstance;

public class DashboardT extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    RelativeLayout dashboard_activity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_mapa:
                    transaction.replace(R.id.content, new FMapa()).commit();
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_estadisticas:
                    transaction.replace(R.id.content, new FEstadisticas()).commit();
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_veeduria:
                    transaction.replace(R.id.content, new FVeeduria()).commit();
                    //BottomNavigationViewHelper.disableShiftMode(navigation);
                    return true;
                case R.id.navigation_configuracion:
                    transaction.replace(R.id.content, new FConfiguraciones()).commit();
                   // BottomNavigationViewHelper.disableShiftMode(navigation_configuracion);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_t);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new FMapa()).commit();


        //dashboard_activity = (RelativeLayout) findViewById(R.id.activity_dashboard_t);
        auth = FirebaseAuth.getInstance();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardT.this, "Has cerrado sesión", Toast.LENGTH_LONG).show();
                logOut();
            }
        });
        if(auth.getCurrentUser() !=null){
            Toast.makeText(DashboardT.this, "Bienvenido, "+auth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        }

    }

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

    @Override
    public void onClick(View v) {

    }
    private void logOut(){
        auth.signOut();
        if(auth.getCurrentUser() ==null){
            startActivity(new Intent(DashboardT.this, MainActivity.class));
            finish();
        }
    }
}
*/