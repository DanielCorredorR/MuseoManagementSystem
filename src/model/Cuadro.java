package model;

public class Cuadro extends ObraArte {

    private String estilo;
    private String tecnica;

    public Cuadro(String titulo, String autor,
                  int anioCreacion, double valorEconomico,
                  String estilo, String tecnica) {

        super(titulo, autor, anioCreacion, valorEconomico);
        this.estilo = estilo;
        this.tecnica = tecnica;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getTecnica() {
        return tecnica;
    }
}