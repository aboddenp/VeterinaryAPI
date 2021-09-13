package com.bodden.VeterinaryAPI.Queues.Producers;

import com.bodden.VeterinaryAPI.Models.AppointmentHistory;
import com.bodden.VeterinaryAPI.VeterinaryApiApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentProducer {

    private final RabbitTemplate rabbitTemplate;

    public AppointmentProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAppointment(String appointmentData){
        try{
            rabbitTemplate.convertAndSend(VeterinaryApiApplication.topicExchangeName,"vet.app.#",appointmentData);
        }catch (Exception e){
            return;
        }
    }

}
