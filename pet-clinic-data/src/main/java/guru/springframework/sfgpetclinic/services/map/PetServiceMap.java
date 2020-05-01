package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {

    private final PetTypeService petTypeService;

    public PetServiceMap(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public Pet save(Pet pet) {
        if (pet == null)
            return null;

        if (pet.getPetType() != null) {
            if (pet.getPetType().getId() == null) {
                PetType savedPetType = petTypeService.save(pet.getPetType());
                pet.getPetType().setId(savedPetType.getId());
            }
        } else {
            throw new RuntimeException("Pet Type is required");
        }
        return super.save(pet);
    }
}
