package com.aardizio;

import com.aardizio.errors.ApiError;
import com.aardizio.errors.ApiErrorHandler;
import com.aardizio.errors.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebFlux
@Slf4j
public class SpringReactiveApplication implements ApplicationListener<ApplicationReadyEvent> {

	@Qualifier("simpleConsumer")
	@Autowired
	private KafkaReceiver<String, String> receiver;

	@Autowired
	private ApiErrorHandler apiErrorHandler;

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	public void onApplicationEvent(ApplicationReadyEvent event) {
		apiErrorHandler.register(CustomException.class, new ApiError(HttpStatus.BAD_REQUEST, "002", "3"));
		receiver.receive().subscribe(r -> {
			log.info("Received message: %s\n", r);
			r.receiverOffset().acknowledge();
		});
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().registerModules(new ProblemModule().withStackTraces(false), new ConstraintViolationProblemModule());
	}

}
