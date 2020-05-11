package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }

    @Override
    public Owner save(Owner owner) {
        if (owner != null) {
            owner.getPets().forEach(pet -> {
                if (pet.getId() == null) {
                    Pet savePet = petService.save(pet);
                    pet.setId(savePet.getId());
                }
            });
            return super.save(owner);
        }
        return null;
    }


    @Override
    public Owner findByLastName(String lastName) {
        return findAll().stream().filter(o->o.getLastName().equals(lastName)).findFirst().orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {
        // todo: implementation
        return null;
    }
}
