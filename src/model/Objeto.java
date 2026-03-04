package model;

import java.time.LocalDate;

public class Objeto extends ObraArte {

    public Objeto(String titulo, String autor, String periodo,
                      double valor, LocalDate fechaCreacion, LocalDate fechaEntrada) {

        super(titulo, autor, periodo, valor, fechaCreacion, fechaEntrada);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}