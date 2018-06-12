package com.aardizio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.kafka.receiver.KafkaReceiver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebFlux
public class SpringReactiveApplication implements ApplicationListener<ApplicationReadyEvent>{

	@Qualifier("simpleConsumer")
	@Autowired
	private KafkaReceiver<String,String> receiver;


	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	public void onApplicationEvent(ApplicationReadyEvent event){
		receiver.receive().subscribe(r -> {
			System.out.printf("Received message: %s\n", r);           
			r.receiverOffset().acknowledge();
	}); 
	}

}
