package model;

import java.time.LocalDate;

public class Escultura extends ObraArte {

    private String estilo;
    private String material;

    public Escultura(String titulo, String autor, String periodo,
                     double valor, LocalDate fechaCreacion, LocalDate fechaEntrada,
                     String estilo, String material) {

        super(titulo, autor, periodo, valor, fechaCreacion, fechaEntrada);
        this.estilo = estilo;
        this.material = material;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return super.toString() +
                " | Estilo: " + estilo +
                " | Material: " + material;
    }
}