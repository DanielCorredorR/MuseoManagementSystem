package model;

public class Escultura extends ObraArte {

    private String estilo;
    private String material;

    public Escultura(String titulo, String autor,
                     int anioCreacion, double valorEconomico,
                     String estilo, String material) {

        super(titulo, autor, anioCreacion, valorEconomico);
        this.estilo = estilo;
        this.material = material;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getMaterial() {
        return material;
    }
}