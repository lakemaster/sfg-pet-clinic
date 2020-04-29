package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T obj) {
        if ( obj != null ) {
            if ( obj.getId() == null ) {
                obj.setId(getNextId());
            }
            map.put(obj.getId(), obj);
        } else {
            throw new RuntimeException("Cannot save null object");
        }
        return obj;
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T obj) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(obj));
    }

    private Long getNextId() {
        if ( map.isEmpty() )
            return 1L;

        return Collections.max(map.keySet()) + 1;
    }
}
