package com.roitraining.demos.web_flux_basics.service;

import com.roitraining.demos.web_flux_basics.entity.Author;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorManagementService {
    Flux<Author> getCurrentAuthors();
    Flux<Author> getAuthorsByLastName(String searchWith);
    Mono<Author> createNewAuthor(Author authorNew);
    Mono<Author> updateAuthorLastName(Mono<Author> currentAuthor);
}
