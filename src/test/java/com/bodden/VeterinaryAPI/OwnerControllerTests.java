package com.bodden.VeterinaryAPI;

import com.bodden.VeterinaryAPI.Controllers.OwnerController;
import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Owner;
import com.bodden.VeterinaryAPI.Repositories.OwnerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    OwnerRepository ownerRepository;

    Owner owner1;
    Owner owner2;
    Owner owner3;

    @BeforeEach
    private void setOwners() {
        owner1 = new Owner(1, "John", "Doe");
        owner2 = new Owner(2, "Bob", "Smith");
        owner3 = new Owner(3, "Jake", "Sully");

    }

    @Test
    public void getAllOwners_succeed() throws Exception {
        List<Owner> owners = new ArrayList<Owner>(Arrays.asList(owner1, owner2, owner3));

        Mockito.when(ownerRepository.findAll()).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("Bob")))
                .andExpect(jsonPath("$[2]lastName", is("Sully"))
                );
    }

    @Test
    public void getOwnerById_succeed() throws Exception {
        Mockito.when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/owners/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id",is((int ) owner1.getId())))
                .andExpect(jsonPath("$.name",is("John")));
    }

    @Test
    public void createOwner_succeed() throws Exception {
        Owner owner = new Owner(4,"new","owner");
        Mockito.when(ownerRepository.save(owner)).thenReturn(owner);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(owner));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name",is(owner.getName())));
    }

    @Test
    public void updateOwner_succeed() throws Exception{
        Owner ownerUpdate = new Owner(3,"update","owner");
        Mockito.when(ownerRepository.findById(owner3.getId())).thenReturn(Optional.of(owner3));
        Mockito.when(ownerRepository.save(ownerUpdate)).thenReturn(ownerUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/owners/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ownerUpdate));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is(ownerUpdate.getName())))
                .andExpect(jsonPath("$.lastName",is(ownerUpdate.getLastName())));
    }

    @Test
    public void updateOwner_noId() throws Exception{
        Owner ownerUpdate = new Owner(3,"update","owner");
        Mockito.when(ownerRepository.findById(owner3.getId())).thenReturn(Optional.of(owner3));
        Mockito.when(ownerRepository.save(ownerUpdate)).thenReturn(ownerUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ownerUpdate));

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateOwner_notFound() throws Exception{
        Owner ownerUpdate = new Owner(100l,"update","owner");
        Mockito.when(ownerRepository.findById(ownerUpdate.getId())).thenReturn(Optional.empty());
        Mockito.when(ownerRepository.save(ownerUpdate)).thenReturn(ownerUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/owners/100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ownerUpdate));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                    assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result ->
                        assertEquals("ownerId " + ownerUpdate.getId() + " not found", result
                                .getResolvedException().getMessage()));
    }

    @Test
    public void deleteOwnerById_succeed() throws Exception {
        Mockito.when(ownerRepository.findById(owner2.getId())).thenReturn(Optional.of(owner2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/owners/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOwnerById_notFound() throws Exception {
        Mockito.when(ownerRepository.findById(5l)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/owners/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result ->
                        assertEquals("ownerId " + 5 + " not found", result.getResolvedException().getMessage()));
    }



}
