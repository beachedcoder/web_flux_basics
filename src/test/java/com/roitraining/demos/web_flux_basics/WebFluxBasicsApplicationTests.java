package com.roitraining.demos.web_flux_basics;

import com.roitraining.demos.web_flux_basics.controllers.CourseController;
import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class WebFluxBasicsApplicationTests {

    private CourseService mockSvc;
    private WebTestClient client;

    @BeforeEach
    void initCourseDependencies() {
        mockSvc = mock(CourseService.class);
        when(mockSvc.getSampleCourse())
                .thenReturn(Mono.just(new Course("Faux Java Reflections",
                        "Fun with Java developement")));
        when(mockSvc.getCurrentCourses())
                .thenReturn(Flux.just(new Course("Introduction to Java"
                                , "Java introduction for programmers"),
                        new Course("Introduction to Kotlin",
                                "Hands on approach to Kotlin"),
                        new Course("Angular for Java developers",
                                "Hands on development introduction to Angular"),
                        new Course("Spring Boot for Microservices",
                                "Hands on introduction to Spring Boot"),
                        new Course("Introduction to Go",
                                "Go for Java developers")));

        client = WebTestClient
                .bindToController(new CourseController(mockSvc))
                .configureClient()
                .baseUrl("/course")
                .build();
    }




    @Test
    void annotationGetCurrentCourses(){

    }

}
