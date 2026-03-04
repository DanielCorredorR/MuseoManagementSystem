package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sala {

    private String id;
    private String nombre;
    private String descripcion;
    private List<ObraArte> obras;

    public Sala(String nombre, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.obras = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<ObraArte> getObras() {
        return obras;
    }

    public void agregarObra(ObraArte obra) {
        if (!obras.contains(obra)) {
            obras.add(obra);
            obra.setSala(this); // SINCRONIZACION BIDIRECCIONAL
        }
    }

    public void removerObra(ObraArte obra) {
        obras.remove(obra);
        obra.setSala(null);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nombre: " + nombre +
                " | Descripcion: " + descripcion;
    }
}