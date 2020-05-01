package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;
import guru.springframework.sfgpetclinic.services.CrudService;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> implements CrudService<T, ID> {

    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(ID id) {
        return map.get(id);
    }

    public T save(T obj) {
        if (obj != null) {
            if (obj.getId() == null) {
                obj.setId(getNextId());
            }
            map.put(obj.getId(), obj);
        } else {
            throw new RuntimeException("Cannot save null object");
        }
        return obj;
    }

    public void deleteById(ID id) {
        map.remove(id);
    }

    public void delete(T obj) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(obj));
    }

    private Long getNextId() {
        if (map.isEmpty())
            return 1L;

        return Collections.max(map.keySet()) + 1;
    }
}
