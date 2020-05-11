package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
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

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder().id(1L).lastName(LAST_NAME).build();
        owner2 = Owner.builder().id(2L).lastName(LASTE_NAME_2).build();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                Stream.of(owner1, owner2).collect(Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))
                .andExpect(MockMvcResultMatchers.model().attribute("selections", Matchers.hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(
                Stream.of(owner1).collect(Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
    }


    @Test
    void displayOwner() throws Exception {
        when(ownerService.findById(eq(1L))).thenReturn(owner1);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.equalTo(owner1)));
    }
}