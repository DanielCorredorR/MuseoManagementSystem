package service;

import java.time.LocalDate;
import java.util.List;
import model.Cesion;
import model.EstadoObra;
import model.Museo;
import model.ObraArte;
import repository.CesionRepository;
import repository.ObraRepository;

public class CesionService {

    private final CesionRepository cesionRepository;
    private final ObraRepository obraRepository;

    public CesionService(CesionRepository cesionRepository,
                         ObraRepository obraRepository) {
        this.cesionRepository = cesionRepository;
        this.obraRepository = obraRepository;
    }

    public void cederObra(String idObra,
                          Museo museo,
                          double importe,
                          LocalDate inicio,
                          LocalDate fin) {

        ObraArte obra = obraRepository.buscarPorId(idObra);

        if (obra == null) {
            return;
        }

        List<Cesion> cesionesExistentes =
                cesionRepository.findByObraId(idObra);

        LocalDate fechaRealInicio = inicio;

        if (!cesionesExistentes.isEmpty()) {

            Cesion ultima =
                    cesionesExistentes.get(cesionesExistentes.size() - 1);

            if (ultima.getFechaFin().isAfter(inicio)) {
                fechaRealInicio = ultima.getFechaFin();
            }
        }

        Cesion nueva =
                new Cesion(idObra, museo,
                        importe, fechaRealInicio, fin);

        obra.setEstado(EstadoObra.CEDIDA);

        cesionRepository.save(nueva);
    }
}