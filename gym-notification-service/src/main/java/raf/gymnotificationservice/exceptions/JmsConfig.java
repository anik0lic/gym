package raf.gymnotificationservice.exceptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(t -> {
            // Ovde možete implementirati logiku za rukovanje greškama
            // Na primer, ispisivanje poruke o grešci u konzolu
            System.err.println("JMS listener failed: " + t.getMessage());
        });
        return factory;
    }
}
