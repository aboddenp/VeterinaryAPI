package com.bodden.VeterinaryAPI;

import com.bodden.VeterinaryAPI.Services.AppointmentService;
import com.bodden.VeterinaryAPI.Services.AppointmentServiceDefault;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {
		SecurityAutoConfiguration.class
})
public class VeterinaryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinaryApiApplication.class, args);
	}

	@Bean
	public AppointmentService mainService(){
		return new AppointmentServiceDefault();
	}
}
