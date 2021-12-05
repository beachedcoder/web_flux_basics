package com.roitraining.demos.web_flux_basics.persistence;

import com.roitraining.demos.web_flux_basics.entity.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface AuthorReactiveRepository extends ReactiveCrudRepository<Author, UUID> {
    Flux<Author> findAllByLastNameIsStartingWithOrderByLastName(String startsWith);
}
