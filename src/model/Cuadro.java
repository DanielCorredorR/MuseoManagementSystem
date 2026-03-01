package model;
import java.time.LocalDate;
public class Cuadro extends ObraArte {

    private String estilo;
    private String tecnica;

    public Cuadro(String autor, String periodo, double valorEconomico,
                  LocalDate fechaCreacion, LocalDate fechaIngresoMuseo,
                  String estilo, String tecnica) {
        super(autor, periodo, valorEconomico, fechaCreacion, fechaIngresoMuseo);
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