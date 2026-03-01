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
}
