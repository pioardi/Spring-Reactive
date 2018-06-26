package com.aardizio.resource;

import java.io.IOException;
import java.util.List;

import com.aardizio.client.RestClientExample;
import com.aardizio.model.Hotel;
import com.aardizio.repository.ReactiveHotelRepository;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import brave.Tracer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

/**
 * Rest controller for a reactive web server.
 * 
 * @author Alessandro Pio Ardizio.
 */
@RestController
public class HotelController {

	private static final Logger log = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	@Qualifier("simpleProducer")
	private KafkaSender<String,String> hotelSender;

	@Autowired
	private Tracer tracer;

	@Autowired
	private ReactiveHotelRepository hotelRepo;

	@Autowired
	private ReactiveCassandraTemplate reactiveCassandraTemplate;

	@Autowired
	private RestClientExample hotelRestClient;

	@GetMapping(value = "/clientExample", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
		MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Flux<Hotel> clientExample() {
		log.info("Client example start");
		return hotelRestClient.getAllHotels();
	}

	@DeleteMapping(value = "/hotels/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotel> delete(@PathVariable String id) {
		
		tracer.currentSpan().tag("hotelid", id);
		log.info("deleting a journal");
		return hotelRepo.deleteByUuid(id);
	}

	@PostMapping(value = "/hotels", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Mono<Hotel> create(@RequestBody Hotel hotel) {
		tracer.currentSpan().tag("hotelid", hotel.getId());
		log.info("creating a journal 1");
		
		return  hotelRepo.save(hotel)
			             .flatMapMany(h -> {
					      	ProducerRecord<String,String> record = new ProducerRecord<String,String>("prova", null, hotel.getId(), hotel.toString());
					    	Mono<SenderRecord<String,String,String>> mono = Mono.just(SenderRecord.create(record, null));
					    	return hotelSender.send(mono);
						 })
						.collectList()
				 		.flatMap(m -> Mono.just(hotel));
						
	}


	@GetMapping(value = "/hotels/{uuid}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotel> search(@PathVariable String uuid) {
		tracer.currentSpan().tag("hotelid", uuid);
		log.info("Search gas");
		return hotelRepo.findByUuid(uuid);
	}

	/**
	 * Get all hotels and retry proof settings consistency level to THREE with one cassandra node .
	 */
	@GetMapping(value = "/hotels", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<List<Hotel>> getAllRetryProof() {
		Statement search = QueryBuilder.select()
									   .from("hotel")
									   .setConsistencyLevel(ConsistencyLevel.THREE)
									   .setRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE);
		return reactiveCassandraTemplate.select(search, Hotel.class).collectList();
	}

	@ExceptionHandler
    public ResponseEntity<String> handle(IOException ex) {
        return ResponseEntity.ok("");
    }

}