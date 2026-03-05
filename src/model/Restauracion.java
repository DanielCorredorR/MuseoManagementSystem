package model;

import java.time.LocalDate;
import java.util.UUID;

public class Restauracion {

    private String id;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Restauracion(String tipo, LocalDate fechaInicio) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void finalizarRestauracion(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Tipo: " + tipo +
                " | Inicio: " + fechaInicio +
                " | Fin: " + (fechaFin != null ? fechaFin : "En proceso");
    }
}