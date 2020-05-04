package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    @Override
    public Visit save(Visit visit) {
        if ( visit.getPet() == null || visit.getPet().getId() == 0
        || visit.getPet().getOwner() == null || visit.getPet().getOwner().getId() == 0 ) {
            throw new RuntimeException("Invalid Visit");
        }
        return super.save(visit);
    }
}
