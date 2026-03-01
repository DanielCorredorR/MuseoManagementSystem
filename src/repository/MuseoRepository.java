package repository;

import java.util.ArrayList;
import java.util.List;
import model.Museo;

public class MuseoRepository {

    private final List<Museo> museos = new ArrayList<>();

    public void save(Museo museo) {
        museos.add(museo);
    }

    public List<Museo> findAll() {
        return museos;
    }

    public Museo findById(String id) {
        for (Museo museo : museos) {
            if (museo.getId().equals(id)) {
                return museo;
            }
        }
        return null;
    }
}