package com.roitraining.demos.web_flux_basics.controllers;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.entity.RequestError;
import com.roitraining.demos.web_flux_basics.persistence.CourseReactiveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(path = "r2dbcourse",produces = MediaType.APPLICATION_JSON_VALUE)
public class R2CourseController {
    private CourseReactiveRepository courseService;

    public R2CourseController(CourseReactiveRepository courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Mono<ResponseEntity> getCourseSamples(){
        return Mono.just(ResponseEntity.ok(courseService.findAll()));
    }

    @GetMapping("{courseid}")
    public Mono<ResponseEntity> getCourseWithId(@PathVariable UUID courseid){
        return Mono.just(ResponseEntity.accepted()
                .body(courseService.findById(courseid)));
    }

    @GetMapping("area")
    public Mono<ResponseEntity> getCourseByTopicArea(@RequestParam(value = "topic",defaultValue = "nada") String area){
        if (area.equals("nada"))
            return Mono.just(ResponseEntity.badRequest()
                    .body(new RequestError(
                            HttpStatus.NOT_ACCEPTABLE.value(),
                            "Missing topic area for search criteria"
                    )));
        return Mono.just(ResponseEntity
                .status(HttpStatus.FOUND)
                .body(courseService.findAllByTitleContainsOrderByTitle(area)));
    }

    @PostMapping(value="suggestion",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> suggestedCourse(@RequestBody Course courseSuggestion){
        courseSuggestion.setCreated(LocalDateTime.now());
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.save(courseSuggestion)));
    }
}
