package com.roitraining.demos.web_flux_basics.config;

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
    public RouterFunction<ServerResponse> getMyFunctionalRoutes(CourseHandler handler){
        return RouterFunctions.route()
                .path("fncourse",
                        p -> p.nest( accept(MediaType.APPLICATION_JSON),
                                b -> b.GET("/{topic:[a-z]+}", handler::findByTopic)
                                        .GET("random",handler::getRandomCourse)
                                        .GET("",handler::getCurrentCourses)
                                        .POST("",contentType(MediaType.APPLICATION_JSON),handler::suggestCourse))

                ).build();
    }
}
