package com.roitraining.demos.web_flux_basics.handler;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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
}
