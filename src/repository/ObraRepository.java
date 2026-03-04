package repository;

import model.ObraArte;
import java.util.ArrayList;
import java.util.List;

public class ObraRepository {

    private List<ObraArte> obras;

    public ObraRepository() {
        this.obras = new ArrayList<>();
    }

    public void guardar(ObraArte obra) {
        obras.add(obra);
    }

    public List<ObraArte> listarTodas() {
        return obras;
    }

    public ObraArte buscarPorId(String id) {
        for (ObraArte obra : obras) {
            if (obra.getId().equals(id)) {
                return obra;
            }
        }
        return null;
    }
}