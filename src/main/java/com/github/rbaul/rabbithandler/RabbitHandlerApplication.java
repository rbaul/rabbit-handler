package com.github.rbaul.rabbithandler;

import com.github.rbaul.rabbithandler.dto.Test2Dto;
import com.github.rbaul.rabbithandler.dto.Test3Dto;
import com.github.rbaul.rabbithandler.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class RabbitHandlerApplication {
	
	public static final String RABBIT_HANDLER_EXCHANGE = "rabbit-handler";
	public static final String ROUTING_KEY = "test";
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitHandlerApplication.class, args);
	}
	
	@Bean
	public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(RabbitTemplate rabbitTemplate, ApplicationContext applicationContext) {
		return event -> {
			rabbitTemplate.convertAndSend(RABBIT_HANDLER_EXCHANGE, ROUTING_KEY, TestDto.builder().id("id").build());
			rabbitTemplate.convertAndSend(RABBIT_HANDLER_EXCHANGE, ROUTING_KEY, Test2Dto.builder().id("id").build());
			rabbitTemplate.convertAndSend(RABBIT_HANDLER_EXCHANGE, ROUTING_KEY, Test3Dto.builder().id("id").build());
		};
	}
	
//	@Autowired
//	public void update(SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory) {
//		simpleRabbitListenerContainerFactory.setAdviceChain((MethodInterceptor) invocation -> {
//			log.info("invocation: {}", invocation.getMethod());
//			return invocation.proceed();
//		}, RetryInterceptorBuilder.stateless()
//				.maxAttempts(3)
//				.backOffOptions(1000, 1.0, 10000) // initialInterval, multiplier, maxInterval
//						.recoverer((args, cause) -> {
//							throw new AmqpRejectAndDontRequeueException("Failed");
//						})
//				.build());
//	}
	
//
//	@Bean
//	public TopicExchange topicExchange() {
//		return new TopicExchange(RABBIT_HANDLER_EXCHANGE);
//	}
}
