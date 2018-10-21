package com.aardizio;

import java.util.Collection;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.reactive.config.EnableWebFlux;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebFlux
@Slf4j
public class SpringReactiveApplication implements ApplicationListener<ApplicationReadyEvent>{

	@Qualifier("simpleConsumer")
	@Autowired
	private KafkaReceiver<String,String> receiver; 
	
	@Autowired
	private Collection<KafkaStreams> streams;


	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	public void onApplicationEvent(ApplicationReadyEvent event){
		receiver.receive().subscribe(r -> {
			log.info("Received message: %s\n", r);           
			r.receiverOffset().acknowledge();
		});
		streams.forEach(KafkaStreams::start);
	}
	

}
