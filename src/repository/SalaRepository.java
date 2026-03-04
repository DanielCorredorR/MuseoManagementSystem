package repository;

import model.Sala;

import java.util.ArrayList;
import java.util.List;

public class SalaRepository {

    private List<Sala> salas = new ArrayList<>();

    public void save(Sala sala) {
        salas.add(sala);
    }

    public List<Sala> findAll() {
        return salas;
    }

    public Sala findById(String id) {
        return salas.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}