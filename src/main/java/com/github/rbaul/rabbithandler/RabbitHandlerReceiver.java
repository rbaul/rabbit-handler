package com.github.rbaul.rabbithandler;

import com.github.rbaul.rabbithandler.dto.Test2Dto;
import com.github.rbaul.rabbithandler.dto.Test3Dto;
import com.github.rbaul.rabbithandler.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = "rabbit-handler", durable = "true"),
		exchange = @Exchange(value = RabbitHandlerApplication.RABBIT_HANDLER_EXCHANGE, type = ExchangeTypes.TOPIC), key = RabbitHandlerApplication.ROUTING_KEY))
public class RabbitHandlerReceiver {
	
	@RabbitHandler
	public void receive1(TestDto testDto) {
		log.info("Received 1: {}", testDto);
		throw new RuntimeException("RuntimeException 1");
	}
	
	@RabbitHandler
	public void receive2(Test2Dto testDto) {
		log.info("Received 2: {}", testDto);
	}
	
	@RabbitHandler
	public void receive3(Test3Dto testDto) {
		log.info("Received 3: {}", testDto);
	}
}
