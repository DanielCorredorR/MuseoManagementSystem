package model;

import java.time.LocalDate;
import java.util.UUID;

public class Cesion {

    private final String id;
    private final String idObra;
    private final Museo museoDestino;
    private final double importe;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;

    public Cesion(String idObra,
                  Museo museoDestino,
                  double importe,
                  LocalDate fechaInicio,
                  LocalDate fechaFin) {

        this.id = UUID.randomUUID().toString();
        this.idObra = idObra;
        this.museoDestino = museoDestino;
        this.importe = importe;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getId() {
        return id;
    }

    public String getIdObra() {
        return idObra;
    }

    public Museo getMuseoDestino() {
        return museoDestino;
    }

    public double getImporte() {
        return importe;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    @Override
    public String toString() {
        return "Museo: " + museoDestino.getNombre()
                + " | Inicio: " + fechaInicio
                + " | Fin: " + fechaFin
                + " | Importe: $" + importe;
    }
}
