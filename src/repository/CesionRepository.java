package repository;

import java.util.ArrayList;
import java.util.List;
import model.Cesion;

public class CesionRepository {

    private final List<Cesion> cesiones;

    public CesionRepository() {
        this.cesiones = new ArrayList<>();
    }

    public void save(Cesion cesion) {
        cesiones.add(cesion);
    }

    public List<Cesion> findAll() {
        return new ArrayList<>(cesiones);
    }
}