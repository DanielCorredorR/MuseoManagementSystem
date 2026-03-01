package model;

import java.util.UUID;

public class Museo {

    private final String id;
    private String nombre;
    private String ciudad;

    public Museo(String nombre, String ciudad) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    @Override
    public String toString() {
        return nombre + " - " + ciudad;
    }
}