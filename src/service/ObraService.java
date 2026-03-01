package service;

import java.time.LocalDate;
import java.util.List;
import model.EstadoObra;
import model.ObraArte;
import model.Restauracion;
import repository.ObraRepository;
import repository.RestauracionRepository;

public class ObraService {

    private final ObraRepository obraRepository;
    private final RestauracionRepository restauracionRepository;

    public ObraService(ObraRepository obraRepository,
                       RestauracionRepository restauracionRepository) {
        this.obraRepository = obraRepository;
        this.restauracionRepository = restauracionRepository;
    }

    public void registrarObra(ObraArte obra) {
        obraRepository.save(obra);
    }

    public double calcularValorTotalMuseo() {
        double total = 0;
        List<ObraArte> obras = obraRepository.findAll();
        for (ObraArte obra : obras) {
            total += obra.getValorEconomico();
        }
        return total;
    }

    public void enviarARestauracionPorDanio(String idObra, String tipo) {
        ObraArte obra = obraRepository.findById(idObra);

        if (obra == null) {
            return;
        }

        Restauracion restauracion =
                new Restauracion(tipo, LocalDate.now());

        obra.setEstado(EstadoObra.EN_RESTAURACION);
        obra.addRestauracion(restauracion);
        restauracionRepository.save(restauracion);
    }
    public void verificarRestauracionesAutomaticas() {
    List<ObraArte> obras = obraRepository.findAll();
    int anioActual = LocalDate.now().getYear();

    for (ObraArte obra : obras) {

        int anioReferencia;

        if (obra.getFechaUltimaRestauracion() != null) {
            anioReferencia = obra.getFechaUltimaRestauracion().getYear();
        } else {
            anioReferencia = obra.getAnioCreacion();
        }

        if (anioActual - anioReferencia >= 5) {

            Restauracion restauracion =
                    new Restauracion("PREVENTIVA", LocalDate.now());

            obra.setEstado(EstadoObra.EN_RESTAURACION);
            obra.setFechaUltimaRestauracion(LocalDate.now());
            obra.addRestauracion(restauracion);

            restauracionRepository.save(restauracion);
        }
    }
}
}