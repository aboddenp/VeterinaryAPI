package com.bodden.VeterinaryAPI;

import com.bodden.VeterinaryAPI.Exceptions.InvalidDataException;
import com.bodden.VeterinaryAPI.Exceptions.ResourceNotFoundException;
import com.bodden.VeterinaryAPI.Models.Appointment;
import com.bodden.VeterinaryAPI.Models.Owner;
import com.bodden.VeterinaryAPI.Models.Pet;
import com.bodden.VeterinaryAPI.Repositories.AppointmentRepository;
import com.bodden.VeterinaryAPI.Repositories.PetRepository;
import com.bodden.VeterinaryAPI.Services.AppointmentService;
import com.bodden.VeterinaryAPI.Services.AppointmentServiceDefault;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
public class AppointmentServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private PetRepository petRepository;

    @InjectMocks
    private AppointmentServiceDefault appointmentService;

    Pet pet1;
    Pet pet2;
    Appointment app1;
    Appointment app2;
    Appointment app3;
    Owner owner1;
    Owner owner2;

    @BeforeEach
    private void setUp() {
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
    public void noAppointmentsInThePast() throws Exception{
        app3.setLocalDate(java.time.LocalDate.of(2021, 11, 14));
        Mockito.when(petRepository.findById(pet1.getId())).thenReturn(Optional.of(pet1));
        Mockito.when(appointmentRepository.save(app1)).thenReturn(app1);
        Assertions.assertThrows(InvalidDataException.class,()->{
            appointmentService.createAppointment(pet1.getId(),app1);
        });
    }


}
