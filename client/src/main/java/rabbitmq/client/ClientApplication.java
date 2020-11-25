package rabbitmq.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

@SpringBootApplication
@EnableScheduling
public class ClientApplication {
//	public static final Logger logger = LoggerFactory.getLogger(ClientApplication.class);

	@Value("${consumer.queue}")
    private String phoneNumber;

	private int credit = 20;

	// producer
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	TopicExchange paymentExchange(){
		return new TopicExchange("transaction");
	}

	@Scheduled(fixedDelay = 3000)
	public void sendPayment(){
		Payment payment = Payment.getRandomPayment(phoneNumber, "some purpose");

		if(payment.getAmount() > credit)
			return;
		credit-= payment.getAmount();

		var routingKey = "payment." + payment.getSendTo();
		rabbitTemplate.convertAndSend("transaction", routingKey, payment);

		System.out.println("Sended   " + payment + "  \tCredit: " + credit);
//		logger.info("Sended   {}  \tCredit: {}", payment, credit);
	}


	// CONSUMER
	@RabbitListener(queues = "movements." + "${consumer.queue}")
	public void recievePayment(Payment payment){
		credit+=payment.getAmount();
		System.out.println("Recieved " + payment + "  \tCredit: " + credit);
//		logger.info("REcieved {}  \tCredit: {}", payment, credit);
	}

	@Bean
	Queue paymentQueue() {
		return new Queue("movements." + phoneNumber);
	}

	// exchange uz mam
	@Bean
	Binding paymentBinding() {
		return BindingBuilder
				.bind(paymentQueue())
				.to(paymentExchange())
				.with("#." + phoneNumber);
	}

	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}
