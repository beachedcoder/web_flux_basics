package com.roitraining.demos.web_flux_basics.handler;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CourseHandler {

    private final CourseService courseService;

    public CourseHandler(CourseService courseService) {
        this.courseService = courseService;
    }

    public Mono<ServerResponse> getCurrentCourses(ServerRequest request){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(courseService.getCurrentCourses(), Course.class);
    }

    public Mono<ServerResponse> findByTopic(ServerRequest request) {

        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> suggestCourse(ServerRequest request) {
        //TODO add validator
        Mono<Course> suggested = request.bodyToMono(Course.class);

        return ServerResponse.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(
                      suggested.flatMap(c->courseService.updateCatalog(c.getTitle(),c.getSummary())),
                        Course.class
                        ));
    }

    public Mono<ServerResponse> getRandomCourse(ServerRequest request) {
        return ServerResponse.ok().build();
    }
}
