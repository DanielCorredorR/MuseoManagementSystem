package model;

import java.time.LocalDate;

public class Restauracion {

    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Restauracion(String tipo, LocalDate fechaInicio) {
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
    }

    public void finalizarRestauracion(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getTipo() {
        return tipo;
    }
}