package service;

import model.ObraArte;
import repository.ObraRepository;

public class RestauracionService {

    private final ObraRepository repository;

    public RestauracionService(ObraRepository repository) {
        this.repository = repository;
    }

    public void enviarARestauracion(String id, String tipo) {

        ObraArte obra = repository.findById(id);

        if (obra == null) {
            return;
        }

        obra.enviarARestauracion(tipo);
    }

    public void finalizarRestauracion(String id) {

        ObraArte obra = repository.findById(id);

        if (obra == null) {
            return;
        }

        obra.finalizarRestauracion();
    }
}