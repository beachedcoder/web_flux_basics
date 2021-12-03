package com.roitraining.demos.web_flux_basics.controllers;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/stream")
    public Flux<ServerSentEvent<String>> streamIt(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(s-> ServerSentEvent.<String>builder()
                        .id(String.valueOf(s))
                        .event("periodic event")
                        .data("Streamed Event - "+ LocalTime.now().toString())
                        .build());
    }
}
