package com.example.mac.lacus;

import java.util.List;

/**
 * Created by jufe9 on 27/11/2017.
 */

public class Denuncia {

    public String id;
    public String geopos;
    public String categoria;
    public String problema;
    public int cantidad;

    public Denuncia() {

    }

    public Denuncia(String geopos, String categoria, String problema, int cantidad) {

        this.geopos = geopos;
        this.categoria = categoria;
        this.problema = problema;
        this.cantidad = cantidad;

    }

    public Denuncia(String id, String geopos, String categoria, String problema, int cantidad) {

        this.id = id;
        this.geopos = geopos;
        this.categoria = categoria;
        this.problema = problema;
        this.cantidad = cantidad;

    }

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getGeopos() {

        return geopos;

    }

    public String getCategoria() {

        return categoria;

    }

    public String getProblema() {

        return problema;

    }

    public int getCantidad() {

        return cantidad;

    }

}
