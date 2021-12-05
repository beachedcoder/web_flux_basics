package com.roitraining.demos.web_flux_basics.persistence;

import com.roitraining.demos.web_flux_basics.entity.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;


public interface CourseReactiveRepository extends ReactiveCrudRepository<Course, UUID> {
    Flux<Course> findAllByTitleContainsOrderByTitle(String topic);
}
