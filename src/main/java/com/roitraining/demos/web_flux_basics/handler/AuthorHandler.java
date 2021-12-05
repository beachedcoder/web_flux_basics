package com.roitraining.demos.web_flux_basics.handler;

import com.roitraining.demos.web_flux_basics.entity.Author;
import com.roitraining.demos.web_flux_basics.service.AuthorManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthorHandler {
    private AuthorManagementService dataService;

    public AuthorHandler(AuthorManagementService dataService) {
        this.dataService = dataService;
    }

    public Mono<ServerResponse> getCurrentAuthors(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dataService.getCurrentAuthors(), Author.class);
    }

    public Mono<ServerResponse> getByLastName(ServerRequest serverRequest) {
        String lnChar = serverRequest.queryParam("ln").orElse("Me");
        return ServerResponse.status(HttpStatus.FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(dataService.getAuthorsByLastName(lnChar),Author.class);
    }

    public Mono<ServerResponse> registerNewAuthor(ServerRequest serverRequest) {
        return ServerResponse.accepted()
                .body(serverRequest.bodyToMono(Author.class)
                    .flatMap(dataService::createNewAuthor),Author.class);
    }

    public Mono<ServerResponse> changeAuthorLastName(ServerRequest serverRequest){
        return ServerResponse.accepted()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dataService.updateAuthorLastName(serverRequest.bodyToMono(Author.class)),Author.class);
    }
}
