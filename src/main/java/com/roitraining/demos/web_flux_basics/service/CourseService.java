package com.roitraining.demos.web_flux_basics.service;

import com.roitraining.demos.web_flux_basics.entity.Course;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService {
    Mono<Course> getSampleCourse();
    Flux<Course> getCurrentCourses();
    Mono<Course> getCourseById(int courseNumber);
    Flux<Course> findCourseByKeyword(String topic);
    Mono<Course> updateCatalog(String courseTitle, String courseSummary);
}
