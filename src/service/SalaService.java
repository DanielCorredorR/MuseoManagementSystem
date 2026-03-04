package service;

import model.ObraArte;
import model.Sala;
import repository.ObraRepository;
import repository.SalaRepository;

import java.util.List;

public class SalaService {

    private SalaRepository salaRepository;
    private ObraRepository obraRepository;

    public SalaService(SalaRepository salaRepository,
                       ObraRepository obraRepository) {

        this.salaRepository = salaRepository;
        this.obraRepository = obraRepository;
    }

    public void asignarObraASala(String idObra, String idSala) {

        ObraArte obra = obraRepository.buscarPorId(idObra);
        Sala sala = salaRepository.findById(idSala);

        if (obra == null || sala == null) {
            System.out.println("Obra o sala no encontrada");
            return;
        }

        sala.agregarObra(obra);
    }

    public List<ObraArte> listarObrasPorSala(String idSala) {

        Sala sala = salaRepository.findById(idSala);

        if (sala == null) {
            return List.of();
        }

        return sala.getObras();
    }
}