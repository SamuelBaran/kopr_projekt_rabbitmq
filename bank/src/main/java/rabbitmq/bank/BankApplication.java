package rabbitmq.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import rabbitmq.client.ClientApplication;
import rabbitmq.client.Payment;


@SpringBootApplication
@EnableScheduling
public class BankApplication {
//    public static final Logger logger = LoggerFactory.getLogger(BankApplication.class);

    @Value("${bankId}")
    private String bankId;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    TopicExchange paymentExchange() {
        return new TopicExchange("transaction");
    }

    @Scheduled(fixedDelay = 20000)
    public void sendPayment() {
        Payment payment = Payment.getRandomCreditPayment(bankId);
        var routingKey = "credit." + payment.getSendTo();
        rabbitTemplate.convertAndSend("transaction", routingKey, payment);
        System.out.println("Credit: " + payment);
//        logger.info("Credit: {}", payment);
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

}
