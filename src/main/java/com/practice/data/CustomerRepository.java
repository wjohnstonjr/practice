package com.practice.data;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.practice.model.NameAndState;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<CustomerEntity, Long> {

	Flux<CustomerEntity> findByLastName(String lastName);

	@Query("""
			 SELECT c.first_name AS name, a.state
			 FROM customer c
			 INNER JOIN address a ON c.address_id=a.id
			""")
	Flux<NameAndState> findNameAndState();
	
	Mono<Long> deleteById(long id);
}
