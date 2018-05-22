package com.aardizio.resource;

import com.aardizio.model.Hotels;
import com.aardizio.repository.ReactiveHotelRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Rest controller for a reactive web server.
 * 
 * @author Alessandro Pio Ardizio.
 */
@RestController
public class HotelController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

	@Autowired
	private ReactiveHotelRepository hotelRepo;

	@DeleteMapping(value = "/hotels/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotels> delete(@PathVariable String id) {
		LOGGER.info("deleting a journal");
		return hotelRepo.deleteByUuid(id);
	}

	@PostMapping(value = "/hotels", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody Mono<Hotels> create(@RequestBody Hotels hotel) {
		LOGGER.info("creating a journal");
		return hotelRepo.save(hotel);
	}

	@GetMapping(value = "/hotels/{uuid}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<Hotels> search(@PathVariable String uuid) {
		return hotelRepo.findByUuid(uuid);
	}

}