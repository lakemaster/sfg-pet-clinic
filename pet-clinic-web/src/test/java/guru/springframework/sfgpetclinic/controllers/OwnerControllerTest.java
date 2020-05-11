package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String LAST_NAME = "Smith";
    private static final String LASTE_NAME_2 = "Baker";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    private Owner owner1;
    private Owner owner2;
    private Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder().id(1L).lastName(LAST_NAME).build();
        owner2 = Owner.builder().id(2L).lastName(LASTE_NAME_2).build();
        owners = Stream.of(owner1, owner2).collect(Collectors.toSet());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listOwners() {
        when(ownerService.findAll()).thenReturn(owners);

        Stream.of("/owners", "/owners.html").forEach( path -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders.get(path))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("owners/index"))
                        .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(2)));
            } catch (Exception e) {
                e.printStackTrace();
                Assertions.fail("Exception when processing path " + path);
            }
        });
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("notimplemented"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(eq(1L))).thenReturn(owner1);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owner/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.equalTo(owner1)));
    }
}