package com.github.rbaul.rabbithandler;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleRabbitListenerPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().isAssignableFrom(SimpleRabbitListenerContainerFactory.class)) {
			SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = (SimpleRabbitListenerContainerFactory) bean;
			simpleRabbitListenerContainerFactory.setAdviceChain((MethodInterceptor) invocation -> {
						log.info("Custom interceptor called");
						return invocation.proceed();
					},
					// Interceptor to retry and reject if still failed
					RetryInterceptorBuilder.stateless()
							.maxAttempts(3)
							.backOffOptions(1000, 1.0, 10000) // initialInterval, multiplier, maxInterval
							.recoverer((args, cause) -> {
								throw new AmqpRejectAndDontRequeueException("Failed 3 times, message rejected: " + cause.getMessage());
							})
							.build());
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
}
