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

    // ==============================
    // RESTAURACION MANUAL
    // ==============================
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

        if (ultima.getFechaFin() != null) {
            System.out.println("La última restauración ya está finalizada");
            return;
        }

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

    // ==============================
    // RESTAURACION AUTOMATICA
    // ==============================
    public void verificarRestauracionesAutomaticas() {

        List<ObraArte> obras = obraRepository.listarTodas();

        for (ObraArte obra : obras) {

            LocalDate referencia;

            if (obra.getRestauraciones().isEmpty()) {
                referencia = obra.getFechaCreacion();
            } else {

                Restauracion ultima =
                        obra.getRestauraciones()
                                .get(obra.getRestauraciones().size() - 1);

                if (ultima.getFechaFin() == null) {
                    continue; // aún en restauración
                }

                referencia = ultima.getFechaFin();
            }

            if (referencia.plusYears(5).isBefore(LocalDate.now())) {

                if (obra.getEstado() != EstadoObra.EN_RESTAURACION) {

                    Restauracion nueva =
                            new Restauracion(
                                    "Restauracion automatica",
                                    LocalDate.now()
                            );

                    obra.agregarRestauracion(nueva);
                    obra.setEstado(EstadoObra.EN_RESTAURACION);

                    System.out.println(
                            "Obra enviada automaticamente a restauracion: "
                                    + obra.getTitulo()
                    );
                }
            }
        }
    }
}