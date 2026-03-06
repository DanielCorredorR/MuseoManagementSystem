package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ObraArte {

    private String id;
    private String titulo;
    private String autor;
    private String periodo;
    private double valor;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrada;
    private EstadoObra estado;
    private List<Restauracion> restauraciones;
    private Sala sala;

    public ObraArte(String titulo, String autor, String periodo,
                     double valor, LocalDate fechaCreacion, LocalDate fechaEntrada) {

        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.autor = autor;
        this.periodo = periodo;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrada = fechaEntrada;
        this.estado = EstadoObra.EXPUESTA;
        this.restauraciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public EstadoObra getEstado() {
        return estado;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public List<Restauracion> getRestauraciones() {
        return restauraciones;
    }

    public void agregarRestauracion(Restauracion restauracion) {
        restauraciones.add(restauracion);
    }

    @Override
    public String toString() {
        return "Tipo: " + getClass().getSimpleName() +
                " | ID: " + id +
                " | Titulo: " + titulo +
                " | Autor: " + autor +
                " | Estado: " + estado +
                " | Valor: $" + valor;
    }
}