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
		String m = generateHello();
		Mono<String> hello_webFlux = Mono.just(m).doOnNext(c -> log.info(c)).log();
		String m2 = hello_webFlux.block();
		log.info("pos2", m2);
		return Mono.just(m2);
	}

	private String generateHello() {
		log.info("method generateHello");
		return "Hello Mono";
	}

	public static void main(String[] args) {
		SpringApplication.run(MonoApplication.class, args);
	}

}
