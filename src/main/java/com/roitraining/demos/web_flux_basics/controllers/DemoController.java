package com.roitraining.demos.web_flux_basics.controllers;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.entity.DemoEvent;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final CourseService courseService;

    public DemoController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<DemoEvent>> streamIt(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(s-> ServerSentEvent.<DemoEvent>builder()
                        .id(String.valueOf(s))
                        .event("server event sent demo")
                        .data(new DemoEvent(UUID.randomUUID().toString()))
                        .build());
    }

    @GetMapping(value = "fluxstream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Course> streamOfCourse(){
        return Flux.interval(Duration.ofSeconds(2))
                .flatMap(s->courseService.getSampleCourse());
    }
}
