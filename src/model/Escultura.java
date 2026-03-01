package model;
import java.time.LocalDate;
public class Escultura extends ObraArte {

    private String estilo;
    private String material;

    public Escultura(String autor, String periodo, double valorEconomico,
                     LocalDate fechaCreacion, LocalDate fechaIngresoMuseo,
                     String estilo, String material) {
        super(autor, periodo, valorEconomico, fechaCreacion, fechaIngresoMuseo);
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