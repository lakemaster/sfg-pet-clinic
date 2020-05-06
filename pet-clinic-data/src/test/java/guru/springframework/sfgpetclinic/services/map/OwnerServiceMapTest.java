package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerServiceMapTest {

    OwnerServiceMap ownerMapService;

    final Long ownerId = 1L;
    final String ownerLastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerServiceMap(new PetServiceMap(new PetTypeMapService()));
        ownerMapService.save(Owner.builder().id(ownerId).lastName(ownerLastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(ownerId, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
        assertEquals(ownerLastName, owner.getLastName());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void saveExistingId() {
        Long id = 2L;
        Owner owner = ownerMapService.save(Owner.builder().id(id).build());
        assertEquals(id, owner.getId());
    }

    @Test
    void saveNoId() {
        Owner owner = ownerMapService.save(Owner.builder().build());
        assertNotNull(owner);
        assertNotNull(owner.getId());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(ownerLastName);
        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
        assertEquals(ownerLastName, owner.getLastName());
    }
}