package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObraArte {

    private String id;
    private String titulo;
    private String autor;
    private int anioCreacion;
    private double valorEconomico;
    private EstadoObra estado;
    private LocalDate fechaUltimaRestauracion;
    private List<Restauracion> restauraciones;

    public ObraArte(String titulo, String autor,
                    int anioCreacion, double valorEconomico) {

        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.autor = autor;
        this.anioCreacion = anioCreacion;
        this.valorEconomico = valorEconomico;
        this.estado = EstadoObra.EXHIBICION;
        this.restauraciones = new ArrayList<>();
    }

    public void addRestauracion(Restauracion restauracion) {
        restauraciones.add(restauracion);
    }

    // getters y setters necesarios

    public String getId() {
        return id;
    }

    public int getAnioCreacion() {
        return anioCreacion;
    }

    public double getValorEconomico() {
        return valorEconomico;
    }

    public LocalDate getFechaUltimaRestauracion() {
        return fechaUltimaRestauracion;
    }

    public void setFechaUltimaRestauracion(LocalDate fecha) {
        this.fechaUltimaRestauracion = fecha;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }
}