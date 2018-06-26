package com.aardizio.repository;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

import com.aardizio.model.*;

/**
 * Reactive repository for cassandra query on hotels .
 * 
 * @author Alessandro Pio Ardizio
 */
public interface ReactiveHotelRepository extends ReactiveCrudRepository<Hotel, String> {

    /**
     * Derived query selecting by {@code lastname}.
     *
     * @param lastname
     * @return
     */
    Mono<Hotel> findByUuid(String id);

    /**
     * Delete by uuid.
     */
    @Query("DELETE FROM HOTEL WHERE UUID=?0")
    Mono<Hotel> deleteByUuid(String id);

}