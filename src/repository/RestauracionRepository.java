package repository;

import java.util.ArrayList;
import java.util.List;
import model.Restauracion;

public class RestauracionRepository {

    private final List<Restauracion> restauraciones;

    public RestauracionRepository() {
        this.restauraciones = new ArrayList<>();
    }

    public void save(Restauracion restauracion) {
        restauraciones.add(restauracion);
    }

    public List<Restauracion> findAll() {
        return new ArrayList<>(restauraciones);
    }
}