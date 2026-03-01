package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ObraArte {

    private final String id;
    private String autor;
    private String periodo;
    private double valorEconomico;
    private LocalDate fechaCreacion;
    private LocalDate fechaIngresoMuseo;
    private EstadoObra estado;
    private int anioCreacion;
    private LocalDate fechaUltimaRestauracion;

    private List<Restauracion> restauraciones;
    private List<Cesion> cesiones;

    public ObraArte(String autor, String periodo, double valorEconomico,
                    LocalDate fechaCreacion, LocalDate fechaIngresoMuseo) {
        this.id = UUID.randomUUID().toString();
        this.autor = autor;
        this.periodo = periodo;
        this.valorEconomico = valorEconomico;
        this.fechaCreacion = fechaCreacion;
        this.fechaIngresoMuseo = fechaIngresoMuseo;
        this.estado = EstadoObra.EXPUESTA;
        this.restauraciones = new ArrayList<>();
        this.cesiones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getAutor() {
        return autor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public double getValorEconomico() {
        return valorEconomico;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaIngresoMuseo() {
        return fechaIngresoMuseo;
    }

    public EstadoObra getEstado() {
        return estado;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public List<Restauracion> getRestauraciones() {
        return List.copyOf(restauraciones);
    }

    public List<Cesion> getCesiones() {
        return List.copyOf(cesiones);
    }

    public void addRestauracion(Restauracion restauracion) {
        this.restauraciones.add(restauracion);
    }

    public void addCesion(Cesion cesion) {
        this.cesiones.add(cesion);
    }
    public int getAnioCreacion() {
        return anioCreacion;
    }

    public void setAnioCreacion(int anioCreacion) {
        this.anioCreacion = anioCreacion;
    }

    public LocalDate getFechaUltimaRestauracion() {
        return fechaUltimaRestauracion;
    }

    public void setFechaUltimaRestauracion(LocalDate fechaUltimaRestauracion) {
        this.fechaUltimaRestauracion = fechaUltimaRestauracion;
    }
    
}