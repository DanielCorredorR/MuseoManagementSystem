package repository;

import java.util.ArrayList;
import java.util.List;
import model.Cesion;

public class CesionRepository {

    private final List<Cesion> cesiones = new ArrayList<>();

    public void save(Cesion cesion) {
        cesiones.add(cesion);
    }

    public List<Cesion> findAll() {
        return cesiones;
    }

    public List<Cesion> findByObraId(String idObra) {

        List<Cesion> resultado = new ArrayList<>();

        for (Cesion cesion : cesiones) {
            if (cesion.getIdObra().equals(idObra)) {
                resultado.add(cesion);
            }
        }

        return resultado;
    }
}