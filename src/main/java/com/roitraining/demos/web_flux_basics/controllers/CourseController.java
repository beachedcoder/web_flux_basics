package com.roitraining.demos.web_flux_basics.controllers;

import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.entity.RequestError;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/course",produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Mono<ResponseEntity> getCourseSamples(){
        return Mono.just(ResponseEntity.ok(courseService.getCurrentCourses()));
    }

    @GetMapping("{courseid:\\d\\d\\d\\d?}")
    public Mono<ResponseEntity> getCourseWithId(@PathVariable int courseid){
        return Mono.just(ResponseEntity.accepted()
                .body(courseService.getCourseById(courseid)));
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
                .body(courseService.findCourseByKeyword(area)));
    }

    @PostMapping(value="suggestion",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity> suggestedCourse(@RequestBody Course courseSuggestion){
        //TODO add validator for Course
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.updateCatalog(courseSuggestion.getTitle(), courseSuggestion.getSummary())));
    }
}
