package com.practice.data;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface AddressRepository extends R2dbcRepository<AddressEntity, Long> {
	Mono<Long> deleteById(long id);
}
