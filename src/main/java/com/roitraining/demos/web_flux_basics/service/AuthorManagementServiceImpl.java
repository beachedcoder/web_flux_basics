package com.roitraining.demos.web_flux_basics.service;

import com.roitraining.demos.web_flux_basics.entity.Author;
import com.roitraining.demos.web_flux_basics.persistence.AuthorReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthorManagementServiceImpl implements AuthorManagementService {
    private AuthorReactiveRepository repository;

    public AuthorManagementServiceImpl(AuthorReactiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Author> getCurrentAuthors() {
        return this.repository.findAll();
    }

    @Override
    public Flux<Author> getAuthorsByLastName(String term) {
        return repository.findAllByLastNameIsStartingWithOrderByLastName(term);
    }

    @Override
    public Mono<Author> createNewAuthor(Author authorMono) {
        authorMono.setId(UUID.randomUUID());
        authorMono.setCreated(LocalDateTime.now());
        return this.repository.save(authorMono);
    }

    @Override
    public Mono<Author> updateAuthorLastName(Mono<Author> currentAuthor) {
        return repository.findById(currentAuthor.map(c->c.getId()))
                .flatMap(e-> currentAuthor.map( c->{
                    e.setLastName(c.getLastName());
                    return e;
                }))
                .flatMap(e-> repository.save(e));
    }


}
