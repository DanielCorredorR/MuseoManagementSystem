package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    // ==============================
    // METODOS DE RESTAURACION
    // ==============================

    public void enviarARestauracion(String tipo) {

        if (estado == EstadoObra.EN_RESTAURACION) {
            return;
        }

        Restauracion restauracion =
                new Restauracion(tipo, LocalDate.now());

        restauraciones.add(restauracion);
        estado = EstadoObra.EN_RESTAURACION;
    }

    public void finalizarRestauracion() {

        if (estado != EstadoObra.EN_RESTAURACION) {
            return;
        }

        if (restauraciones.isEmpty()) {
            return;
        }

        Restauracion ultima =
                restauraciones.get(restauraciones.size() - 1);

        ultima.finalizarRestauracion(LocalDate.now());

        fechaUltimaRestauracion = LocalDate.now();
        estado = EstadoObra.EXHIBICION;
    }

    public List<Restauracion> getRestauracionesOrdenadas() {

        restauraciones.sort(
                Comparator.comparing(Restauracion::getFechaInicio)
        );

        return restauraciones;
    }

    // ==============================
    // GETTERS Y SETTERS
    // ==============================

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

    public EstadoObra getEstado() {
        return estado;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public void addRestauracion(Restauracion restauracion) {
        restauraciones.add(restauracion);
    }
}