package repository;

import model.Sala;

import java.util.ArrayList;
import java.util.List;

public class SalaRepository {

    private List<Sala> salas;

    public SalaRepository() {
        this.salas = new ArrayList<>();
    }

    public void save(Sala sala) {
        if (sala == null) return;
        salas.add(sala);
    }

    public Sala findById(String id) {
        if (id == null) return null;

        for (Sala sala : salas) {
            if (sala.getId().equals(id)) {
                return sala;
            }
        }
        return null;
    }

    public List<Sala> findAll() {
        return new ArrayList<>(salas);
    }
}