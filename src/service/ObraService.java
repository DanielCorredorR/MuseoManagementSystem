package service;

import model.EstadoObra;
import model.ObraArte;
import repository.ObraRepository;
import repository.RestauracionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ObraService {

    private ObraRepository obraRepository;
    private RestauracionRepository restauracionRepository;

    public ObraService(ObraRepository obraRepository,
                       RestauracionRepository restauracionRepository) {

        this.obraRepository = obraRepository;
        this.restauracionRepository = restauracionRepository;
    }

    public void registrarObra(ObraArte obra) {
        obraRepository.guardar(obra);
    }

    public List<ObraArte> listarTodas() {
        return obraRepository.listarTodas();
    }

    public ObraArte buscarPorId(String id) {
        return obraRepository.buscarPorId(id);
    }

    public double calcularValorTotalMuseo() {
        return obraRepository.listarTodas()
                .stream()
                .mapToDouble(ObraArte::getValor)
                .sum();
    }

    // =========================
    // LISTAR OBRAS POR SALA
    // =========================

    public List<ObraArte> listarPorSala(String idSala) {

        return obraRepository.listarTodas()
                .stream()
                .filter(o -> o.getSala() != null &&
                        o.getSala().getId().equals(idSala))
                .collect(Collectors.toList());
    }

    // =========================
    // BUSCAR OBRAS
    // =========================

    public List<ObraArte> buscarPorTitulo(String titulo) {

        return obraRepository.listarTodas()
                .stream()
                .filter(o -> o.getTitulo()
                        .toLowerCase()
                        .contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    // =========================
    // OBRAS EN RESTAURACION
    // =========================

    public List<ObraArte> obrasEnRestauracion() {

        return obraRepository.listarTodas()
                .stream()
                .filter(o -> o.getEstado() == EstadoObra.EN_RESTAURACION)
                .collect(Collectors.toList());
    }
}