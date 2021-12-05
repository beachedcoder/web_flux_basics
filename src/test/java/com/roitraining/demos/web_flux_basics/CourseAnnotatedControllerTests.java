package com.roitraining.demos.web_flux_basics;

import com.roitraining.demos.web_flux_basics.controllers.CourseController;
import com.roitraining.demos.web_flux_basics.entity.Course;
import com.roitraining.demos.web_flux_basics.service.CourseService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CourseAnnotatedControllerTests {

    CourseService mockCourseService = mock(CourseService.class);

    WebTestClient webClient = WebTestClient.bindToController(new CourseController(mockCourseService))
            .configureClient().baseUrl("/course").build();

    private Course courseOne, courseTwo, expected, altExpected;
    private LocalDateTime accommodationForJson;

    @BeforeEach
    void initBean(){
        accommodationForJson = LocalDateTime.now();
        courseOne = new Course("web flux test focusing on WebFlux components",
                "by default WebFluxTest also auto-configure a WebTestClient but you can use AutoConfigureWebTestClient");
        courseOne.setCreated(accommodationForJson);
        courseTwo = new Course("Using client to focus on controller logic",
                "Mocking all IoC components is key to isolation");
        courseTwo.setCreated(accommodationForJson);

        expected = new Course("web flux test focusing on WebFlux components",
                "by default WebFluxTest also auto-configure a WebTestClient but you can use AutoConfigureWebTestClient");
        //accommodation for dynamically created values in our demo classes
        expected.setId(courseOne.getId()); expected.setCatalogNumber(courseOne.getCatalogNumber());expected.setCreated(accommodationForJson);
        altExpected = new Course("Using client to focus on controller logic","Mocking all IoC components is key to isolation");
        //accommodation for dynamically created values in our demo classes
        altExpected.setId(courseTwo.getId()); altExpected.setCatalogNumber(courseTwo.getCatalogNumber());altExpected.setCreated(accommodationForJson);

        when(mockCourseService.getCurrentCourses())
                .thenReturn(Flux.just(courseOne,courseTwo));
        when(mockCourseService.getCourseById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(courseTwo));
        when(mockCourseService.updateCatalog(ArgumentMatchers.any(),ArgumentMatchers.anyString()))
                .thenReturn(Mono.just(courseOne));
    }

    @Test
    void getFluxBodyAsListToVerifyReturn(){

        webClient.get()
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Course.class)
                .isEqualTo(List.of(expected,altExpected));
    }

    @Test
    void getFluxBodyWithConsumerFunction(){

        webClient.get()
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Course.class)
                .value(c->{
                    Assertions.assertThat(c).containsAll(List.of(expected,altExpected));
                });
    }

    @Test
    void verfiyMonoBodyReturnWithAssertion(){
        webClient.get().uri("/7777")
                .exchange()
                .expectStatus().isAccepted()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Course.class)
                .value(c->{

                    Assertions.assertThat(c)
                    .isInstanceOf(Course.class)
                            .isNotNull()
                            .isEqualTo(altExpected);

                    // or you could do the following

                    SoftAssertions.assertSoftly( softly ->{
                        softly.assertThat(c.getId()).isEqualTo(altExpected.getId());
                        softly.assertThat(c.getTitle()).isNotNull().isNotBlank().isNotEmpty().startsWith(altExpected.getTitle().substring(0,10));
                        softly.assertThat(c.getCatalogNumber()).isEqualTo(altExpected.getCatalogNumber());
                        softly.assertThat(c.getUpdated()).isNull();
                    });

                });
    }

    @Test
    void verifyMonoBodyWithMatcher(){
        webClient.get().uri("/7777")
                .exchange()
                .expectStatus().isAccepted()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Course.class)
                .value(Course::getId, Matchers.is(altExpected.getId()));
    }

    @Test
    void verifyPostReturnsCourse(){
        webClient.post().uri("/suggestion")
                .bodyValue(courseOne)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Course.class)
                .value(c->{
                    Assertions.assertThat(c)
                            .isInstanceOf(Course.class)
                            .isNotNull()
                            .isEqualTo(expected);
                });
    }

}
