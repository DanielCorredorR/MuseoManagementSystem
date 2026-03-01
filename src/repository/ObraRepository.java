package repository;

import java.util.ArrayList;
import java.util.List;
import model.ObraArte;

public class ObraRepository {

    private final List<ObraArte> obras;

    public ObraRepository() {
        this.obras = new ArrayList<>();
    }

    public void save(ObraArte obra) {
        obras.add(obra);
    }

    public List<ObraArte> findAll() {
        return obras;
    }

    public ObraArte findById(String id) {

        for (ObraArte obra : obras) {
            if (obra.getId().equals(id)) {
                return obra;
            }
        }

        return null;
    }
}