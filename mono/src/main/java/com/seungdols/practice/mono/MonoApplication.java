package com.seungdols.practice.mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class MonoApplication {

	@GetMapping("/")
	Mono<String> hello() {
		return Mono.just("Hello WebFlux");
	}
	public static void main(String[] args) {
		SpringApplication.run(MonoApplication.class, args);
	}

}
