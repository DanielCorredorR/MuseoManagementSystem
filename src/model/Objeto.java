package model;
import java.time.LocalDate;
public class Objeto extends ObraArte {

    public Objeto(String autor, String periodo, double valorEconomico,
                  LocalDate fechaCreacion, LocalDate fechaIngresoMuseo) {
        super(autor, periodo, valorEconomico, fechaCreacion, fechaIngresoMuseo);
    }
}