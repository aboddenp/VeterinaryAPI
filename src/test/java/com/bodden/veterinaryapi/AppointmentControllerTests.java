package com.bodden.veterinaryapi;

import com.bodden.veterinaryapi.models.Appointment;
import com.bodden.veterinaryapi.models.Owner;
import com.bodden.veterinaryapi.models.Pet;
import com.bodden.veterinaryapi.services.interfaces.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    AppointmentService appointmentService;

    Pet pet1;
    Pet pet2;
    Appointment app1;
    Appointment app2;
    Appointment app3;
    Owner owner1;
    Owner owner2;

    @BeforeEach
    private void setOwners() {
        owner1 = new Owner(1, "John", "Smith");
        owner2 = new Owner(2, "John", "Doe");
        pet1 = new Pet();
        pet1.setOwner(owner1);
        pet1.setName("COCO");
        pet1.setType("Dog");
        pet1.setId(1L);

        pet2 = new Pet();
        pet2.setOwner(owner2);
        pet2.setName("Whiskers");
        pet2.setType("Cat");
        pet2.setId(2L);

        app1 = new Appointment();
        app1.setService(Appointment.Service.GROOMING);
        app1.setPet(pet1);
        app1.setId(1);
        app1.setLocalTime(java.time.LocalTime.of(10, 30));
        app1.setLocalDate(java.time.LocalDate.of(2021, 9, 12));

        app2 = new Appointment();
        app2.setService(Appointment.Service.MEDICAL);
        app2.setPet(pet2);
        app2.setId(1);
        app2.setLocalTime(java.time.LocalTime.of(11, 4));
        app2.setLocalDate(java.time.LocalDate.of(2021, 11, 14));

        app3 = new Appointment();
        app3.setService(Appointment.Service.GROOMING);
        app3.setPet(pet2);
        app3.setId(2);
        app3.setLocalTime(java.time.LocalTime.of(11, 4));
        app3.setLocalDate(java.time.LocalDate.of(2021, 11, 14));

    }

    @Test
    public void getAllAppointments_succeed() throws Exception {
        List<Appointment> appointments = new ArrayList<Appointment>(Arrays.asList(app1, app2,app3));

        Mockito.when(appointmentService.getAppointments()).thenReturn(appointments);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].service", is("GROOMING")))
                .andExpect(jsonPath("$[1].service", is("MEDICAL"))
                );
    }

    @Test
    public void getAppointmentById_succeed() throws Exception {
        Mockito.when(appointmentService.getAppointment(1l)).thenReturn(app1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id",is((int ) app1.getId())))
                .andExpect(jsonPath("$.pet.name",is("COCO")));
    }

    @Test
    public void getAppointmentByPetId_succeed() throws Exception {
        List<Appointment> appointments = new ArrayList<Appointment>(Arrays.asList(app2,app3));

        Mockito.when(appointmentService.appointmentByPet(pet2.getId())).thenReturn(appointments);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pets/"+pet2.getId()+"/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].service", is("MEDICAL")))
                .andExpect(jsonPath("$[1].service", is("GROOMING")));
    }

    @Test
    public void createAppointment_succeed() throws Exception {

        Appointment app = new Appointment();
        app.setService(Appointment.Service.MEDICAL);
        app.setPet(pet2);
        app.setLocalTime(java.time.LocalTime.of(11, 4));
        app.setLocalDate(java.time.LocalDate.of(2021, 11, 14));

        Mockito.when(appointmentService.createAppointment(pet2.getId(),app)).thenReturn(app2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/pets/"+pet2.getId()+"/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(app));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id",is((int) app2.getId())))
                .andExpect(jsonPath("$.pet.name",is("Whiskers")));
    }

    @Test
    public void updateAppointment_succeed() throws Exception{
       Appointment app = new Appointment();
        app.setService(Appointment.Service.GROOMING);
        app.setPet(pet2);
        app.setId(1);
        app.setLocalTime(java.time.LocalTime.of(11, 4));
        app.setLocalDate(java.time.LocalDate.of(2021, 11, 14));

        Mockito.when(appointmentService.updateAppointment(app2.getId(), pet2.getId(),app)).thenReturn(app);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/pets/"+pet2.getId()+"/appointments/"+app.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(app));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.service",is(app.getService().toString())));
    }

    @Test
    public void deleteAppointmetById_succeed() throws Exception {
        Mockito.when(appointmentService.deleteAppointment(app1.getId(), pet1.getId())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pets/"+pet1.getId()+"/appointments/"+app1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
