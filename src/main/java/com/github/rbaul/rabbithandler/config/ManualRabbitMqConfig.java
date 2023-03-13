package com.github.rbaul.rabbithandler.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Manual Configuration of Rabbit connection
 */
@Configuration
public class ManualRabbitMqConfig {
	
	/**
	 * RabbitMQ host
	 */
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	/**
	 * RabbitMQ port
	 */
	@Value("${spring.rabbitmq.port}")
	private String port;
	
	/**
	 * RabbitMQ user
	 */
	@Value("${spring.rabbitmq.username}")
	private String user;
	
	/**
	 * RabbitMQ password
	 */
	@Value("${spring.rabbitmq.password}")
	private String password;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setPort(Integer.parseInt(port));
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
}
