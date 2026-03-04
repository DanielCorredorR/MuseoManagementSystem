package service;

import model.EstadoObra;
import model.ObraArte;
import model.Restauracion;
import repository.ObraRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class RestauracionService {

    private ObraRepository obraRepository;

    public RestauracionService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    public void enviarARestauracion(String idObra, String tipo) {

        ObraArte obra = obraRepository.buscarPorId(idObra);

        if (obra == null) {
            System.out.println("Obra no encontrada");
            return;
        }

        if (obra.getEstado() == EstadoObra.EN_RESTAURACION) {
            System.out.println("La obra ya está en restauración");
            return;
        }

        Restauracion restauracion =
                new Restauracion(tipo, LocalDate.now());

        obra.agregarRestauracion(restauracion);
        obra.setEstado(EstadoObra.EN_RESTAURACION);

        System.out.println("Obra enviada a restauración");
    }

    public void finalizarRestauracion(String idObra) {

        ObraArte obra = obraRepository.buscarPorId(idObra);

        if (obra == null) {
            System.out.println("Obra no encontrada");
            return;
        }

        List<Restauracion> lista = obra.getRestauraciones();

        if (lista.isEmpty()) {
            System.out.println("No tiene restauraciones");
            return;
        }

        Restauracion ultima = lista.get(lista.size() - 1);
        ultima.finalizarRestauracion(LocalDate.now());

        obra.setEstado(EstadoObra.EXPUESTA);

        System.out.println("Restauración finalizada");
    }

    public List<Restauracion> obtenerRestauracionesOrdenadas(String idObra) {

        ObraArte obra = obraRepository.buscarPorId(idObra);

        if (obra == null) {
            return List.of();
        }

        return obra.getRestauraciones()
                .stream()
                .sorted(Comparator.comparing(Restauracion::getFechaInicio))
                .toList();
    }
}