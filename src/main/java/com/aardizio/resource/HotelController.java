package com.aardizio.resource;

import com.aardizio.model.Hotels;
import com.aardizio.repository.ReactiveHotelRepository;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	private KafkaSender<String,String> hotelSender;

	@Autowired
	private Tracer tracer;

	@Autowired
	private ReactiveHotelRepository hotelRepo;

	@Autowired
	private ReactiveCassandraTemplate reactiveCassandraTemplate;

	@DeleteMapping(value = "/hotels/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotels> delete(@PathVariable String id) {
		tracer.currentSpan().tag("hotelid", id);
		LOGGER.info("deleting a journal");
		return hotelRepo.deleteByUuid(id);
	}

	@PostMapping(value = "/hotels", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Mono<Hotels> create(@RequestBody Hotels hotel) {
		tracer.currentSpan().tag("hotelid", hotel.getId());
		LOGGER.info("creating a journal 1");
		
		ProducerRecord<String,String> record = new ProducerRecord<String,String>("prova", null, hotel.getId(), hotel.toString());
		Mono<SenderRecord<String,String,String>> mono = Mono.just(SenderRecord.create(record, null));

		
		return hotelRepo.save(hotel)
				 .then()
				 .and(hotelSender.send(mono)
								 .doOnError(e -> LOGGER.error(e.toString()))
								 .doOnNext(m -> LOGGER.info("Produced event : {}" , m.toString())))
				 .map(v -> hotel);
						
	}

	@GetMapping(value = "/hotels/{uuid}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotels> search(@PathVariable String uuid) {
		tracer.currentSpan().tag("hotelid", uuid);
		LOGGER.info("Search gas");
		return hotelRepo.findByUuid(uuid);
	}

	/**
	 * Get all hotels and retry proof settings consistency level to THREE with one cassandra node .
	 */
	@GetMapping(value = "/hotels/", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Flux<Hotels> getAllRetryProof() {
		Statement search = QueryBuilder.select()
									   .from("hotels")
									   .setConsistencyLevel(ConsistencyLevel.THREE)
									   .setRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE);
		return reactiveCassandraTemplate.select(search, Hotels.class);
	}

}