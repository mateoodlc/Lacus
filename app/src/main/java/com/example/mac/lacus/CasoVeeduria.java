package com.example.mac.lacus;

/**
 * Created by jufe9 on 18/01/2018.
 */

public class CasoVeeduria {

    public String id;
    public String categoria;
    public String descripcion;
    public String estado;
    public String geopos;
    public String nombre;
    public String tarea;
    public String zona;

    public CasoVeeduria() {

    }

    public CasoVeeduria(String categoria, String descripcion, String estado, String geopos,
                        String nombre, String tarea, String zona) {

        this.categoria = categoria;
        this.descripcion = descripcion;
        this.estado = estado;
        this.geopos = geopos;
        this.nombre = nombre;
        this.tarea = tarea;
        this.zona = zona;

    }

    public CasoVeeduria(String id, String categoria, String descripcion, String estado, String geopos,
                        String nombre, String tarea, String zona) {

        this.id = id;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.estado = estado;
        this.geopos = geopos;
        this.nombre = nombre;
        this.tarea = tarea;
        this.zona = zona;

    }

    public String getId() {

        return id;

    }

    public void setId(String id) {

        this.id = id;

    }

    public String getCategoria() {

        return categoria;

    }

    public String getDescripcion() {

        return descripcion;

    }

    public String getEstado() {

        return estado;

    }

    public String getGeopos() {

        return geopos;

    }

    public String getNombre() {

        return nombre;

    }

    public String getTarea() {

        return tarea;

    }

    public String getZona() {

        return zona;

    }
}
