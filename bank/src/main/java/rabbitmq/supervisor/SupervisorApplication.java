package rabbitmq.supervisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rabbitmq.client.ClientApplication;
import rabbitmq.client.Payment;

@SpringBootApplication
public class SupervisorApplication {
//    public static final Logger logger = LoggerFactory.getLogger(SupervisorApplication.class);

    @Bean
    TopicExchange paymentExchange(){
        return new TopicExchange("transaction");
    }

    @RabbitListener(queues = "all_payments")
    public void recievePayment(Payment payment) {
        if (payment.getAmount() > 7)
            System.out.println("SUSPICIOUS  " + payment);
//            logger.info("SUSPICIOUS  {}", payment);
    }

    @Bean
    Queue paymentQueue() {
        return new Queue("all_payments");
    }

    @Bean
    Binding paymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(paymentExchange())
                .with("payment.#");
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SupervisorApplication.class, args);
    }

}
