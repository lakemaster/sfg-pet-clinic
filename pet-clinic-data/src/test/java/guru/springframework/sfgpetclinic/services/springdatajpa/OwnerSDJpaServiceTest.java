package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String LAST_NAME = "Smith";
    private static final String LASTE_NAME_2 = "Baker";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    private Owner owner1;
    private Owner owner2;
    private Set<Owner> owners;


    @BeforeEach
    void setUp() {
        owner1 = Owner.builder().id(1L).lastName(LAST_NAME).build();
        owner2 = Owner.builder().id(2L).lastName(LASTE_NAME_2).build();
        owners = Stream.of(owner1, owner2).collect(Collectors.toSet());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(anyString())).thenReturn(owner1);

        Owner owner = service.findByLastName(LAST_NAME);
        assertThat(owner.getLastName()).isEqualTo(LAST_NAME);
        verify(ownerRepository, times(1)).findByLastName(any());
    }

    @Test
    void findAll() {
        when(ownerRepository.findAll()).thenReturn(owners);

        Set<Owner> owners = service.findAll();
        assertThat(owners.size()).isEqualTo(2);
        assertThat(owners.contains(owner1)).isTrue();
        assertThat(owners.contains(owner2)).isTrue();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(owner1));

        Owner owner = service.findById(1L);

        assertThat(owner).isNotNull();
        assertThat(owner).isEqualTo(owner1);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(owner1));

        Owner owner = service.findById(3L);

        assertThat(owner).isNull();
    }


    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(owner1);

        Owner owner = service.save(owner1);

        assertThat(owner).isNotNull();
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        service.delete(owner1);

        verify(ownerRepository, times(1)).delete(eq(owner1));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);

        verify(ownerRepository, times(1)).deleteById(eq(1L));
    }
}