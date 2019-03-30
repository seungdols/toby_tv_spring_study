package com.seungdols.practice.mono;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class MonoApplication {

	@GetMapping("/")
	Mono<String> hello() {
	    log.info("pos1");
		Mono<String> hello_webFlux = Mono.just("Hello WebFlux").doOnNext(c -> log.info(c)).log();
		log.info("pos2");
		return hello_webFlux;
	}
	public static void main(String[] args) {
		SpringApplication.run(MonoApplication.class, args);
	}

}
