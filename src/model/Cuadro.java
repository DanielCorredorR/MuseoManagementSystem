package model;

import java.time.LocalDate;

public class Cuadro extends ObraArte {

    private String estilo;
    private String tecnica;

    public Cuadro(String titulo, String autor, String periodo,
                  double valor, LocalDate fechaCreacion, LocalDate fechaEntrada,
                  String estilo, String tecnica) {

        super(titulo, autor, periodo, valor, fechaCreacion, fechaEntrada);
        this.estilo = estilo;
        this.tecnica = tecnica;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getTecnica() {
        return tecnica;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Estilo: " + estilo +
                " | Tecnica: " + tecnica;
    }
}