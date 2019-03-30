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
		Mono<String> hello_webFlux = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();
		log.info("pos2");
		return hello_webFlux;
	}

	private String generateHello() {
		log.info("method generateHello");
		return "Hello Mono";
	}

	public static void main(String[] args) {
		SpringApplication.run(MonoApplication.class, args);
	}

}
