package com.example.mac.lacus;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mac.lacus.R;

/**
 * Created by mac on 29/11/17.
 */

public class PreferenciasManager {

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferenciasManager(Context context){
        this.context = context;
        getSharedPreferences();
    }

    private void getSharedPreferences(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.mi_preferencia), Context.MODE_PRIVATE);
    }

    public void escribirPreferencia(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.mi_preferencia_key), "INIT_OK");
        editor.commit();
    }

    public boolean checkearPreferencias(){
        boolean estado = false;

        if(sharedPreferences.getString(context.getString(R.string.mi_preferencia_key), "null").equals("null")){
            estado = true;
        } else {
            estado = false;
        }
        return estado;
    }

    public void limpiarPreferencias(){
        sharedPreferences.edit().clear().commit();
    }

}
