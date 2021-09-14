package com.bodden.veterinaryapi.queues.Producers;

import com.bodden.veterinaryapi.models.AppointmentHistory;
import com.bodden.veterinaryapi.VeterinaryApiApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentProducer {

    private final RabbitTemplate rabbitTemplate;

    public AppointmentProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAppointment(AppointmentHistory appointmentData){
        try{
             rabbitTemplate.convertAndSend(VeterinaryApiApplication.topicExchangeName,"vet.app.#",appointmentData);
        }catch (Exception e){
            return;
        }
    }

}
