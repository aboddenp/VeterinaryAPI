package com.bodden.veterinaryapi;

import com.bodden.veterinaryapi.queues.Recievers.AppointmentReceiver;
import com.bodden.veterinaryapi.services.AppointmentService;
import com.bodden.veterinaryapi.services.AppointmentServiceDefault;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
public class VeterinaryApiApplication {

    public static final String topicExchangeName = "spring-boot-exchange";
    public static final String queueName = "appointment-log";

    public static void main(String[] args) {
        SpringApplication.run(VeterinaryApiApplication.class, args);
    }

    @Bean
    public AppointmentService mainService() {
        return new AppointmentServiceDefault();
    }

    // Queue Setup
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("vet.app.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(AppointmentReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveAppointment");
    }

}
