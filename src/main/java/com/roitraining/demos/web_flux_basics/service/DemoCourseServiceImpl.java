package com.roitraining.demos.web_flux_basics.service;


import com.roitraining.demos.web_flux_basics.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DemoCourseServiceImpl implements CourseService {
    private final Logger log = LoggerFactory.getLogger(CourseService.class);
    private List<Course> fauxData;

    public DemoCourseServiceImpl() {
        this.fauxData = new ArrayList<>(Arrays.asList(
                new Course("Introduction to Java"
                        ,"Java introduction for programmers"),
                new Course("Introduction to Kotlin",
                        "Hands on approach to Kotlin"),
                new Course("Angular for Java developers",
                        "Hands on development introduction to Angular"),
                new Course("Spring Boot for Microservices",
                        "Hands on introduction to Spring Boot"),
                new Course("Introduction to Go",
                        "Go for Java developers"),
                new Course("Reactive Java with Spring",
                        "Building RESTful reactive services"),
                new Course("Azure development boot camp",
                        "rapid development with DevOps and cloud native architecture"),
                new Course("Azure Hyperscale Storage Solutions",
                        "Developing reactive data services with massive scalability"),
                new Course("Azure mobile application development",
                        "Develop multiple platform mobile applications with common code base"),
                new Course("Introduction to Azure SQL",
                        "Azure SQL managed instances with PaaS servcies on Azure Cloud"),
                new Course("Implementing Durable Functions",
                        "Durable Functions can simplify complex, stateful coordination requirements in serverless applications"),
                new Course("Secure Cognitive Services",
                        "Securing Cognitive services help prevent data loss and privacy violations for user data that may be a part of the solution")
                ));
    }

    private Course getRandomCourse(){
        var crsIndex =(int)Math.round( (Math.random()*this.fauxData.size()));
        if (crsIndex<this.fauxData.size())  return this.fauxData.get(crsIndex);
        else return this.fauxData.get(--crsIndex);
    }

    private Mono<Course> generateFauxCourse(){
        return Mono.just(new Course("Alternate source course for Demo",
                "Building data with alternate publisher for resilience"));
    }

    @Override
    public Mono getSampleCourse() {
        return Mono.just(this.getRandomCourse()).switchIfEmpty(generateFauxCourse());
    }

    @Override
    public Flux<Course> getCurrentCourses() {
        return Flux.fromIterable(this.fauxData);
    }

    @Override
    public Mono<Course> getCourseById(int courseNumber) {
        return Mono.justOrEmpty(this.getRandomCourse())
                .switchIfEmpty(generateFauxCourse());
    }

    @Override
    public Flux<Course> findCourseByKeyword(String topic) {
        return Flux.fromStream(this.fauxData.stream().filter(c->c.getTitle().toLowerCase().contains(topic.toLowerCase())));
    }

    public Mono<Course> updateCatalog(String crsTitle, String crsSummary){
        Course nueCourse = new Course(crsTitle,crsSummary);
        log.info(nueCourse.toString());
        this.fauxData.add(nueCourse);
        return Mono.just(nueCourse);
    }
}
