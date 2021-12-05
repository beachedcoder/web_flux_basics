package com.roitraining.demos.web_flux_basics.config;

import com.roitraining.demos.web_flux_basics.handler.AuthorHandler;
import com.roitraining.demos.web_flux_basics.handler.CourseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
public class FunctionalRouteConfig {
    @Bean
    public RouterFunction<ServerResponse> getMyFunctionalRoutes(CourseHandler handler, AuthorHandler authorHandler){
        return RouterFunctions.route()
                .path("fncourse", p -> p.nest( accept(MediaType.APPLICATION_JSON),
                                b -> b.GET("random",handler::getRandomCourse)
                                        .GET("{topic:[a-z]+}", handler::findByTopic)
                                        .GET("",handler::getCurrentCourses)
                                        .POST("",contentType(MediaType.APPLICATION_JSON),handler::suggestCourse)

                        ))
                .path("fnauthor",p->p.nest(
                        accept(MediaType.APPLICATION_JSON),
                        builder ->{
                            builder.GET("search",authorHandler::getByLastName)
                                    .PUT("changename",contentType(MediaType.APPLICATION_JSON),authorHandler::changeAuthorLastName)
                                    .POST("",contentType(MediaType.APPLICATION_JSON),authorHandler::registerNewAuthor)
                                    .GET("",authorHandler::getCurrentAuthors);
                        }
                )).build();
    }
}
