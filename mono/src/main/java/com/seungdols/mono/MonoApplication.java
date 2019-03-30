package com.seungdols.mono;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootApplication
@EnableAsync
public class MonoApplication {

    @RestController
    public static class MyController {
        static String url1 = "http://localhost:8081/service1?req={req}";
        static String url2 = "http://localhost:8081/service2?req={req}";

        @Autowired
        MyService myService;

        WebClient webClient = WebClient.builder().build();

        @GetMapping("/rest")
        public Mono<String> rest(int idx) {
            return webClient.get().uri(url1, idx).exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                    .flatMap(res1 -> webClient.get().uri(url2, res1).exchange())
                    .flatMap(c -> c.bodyToMono(String.class))
                    .doOnNext(c -> log.info(c))
                    .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))
                    .doOnNext(c -> log.info(c));
        }
    }


    @Service
    public static class MyService {
        @Async
        public CompletableFuture<String> work (String req) {
            return CompletableFuture.completedFuture(req + "/asyncWork");
        }
    }

    public static void main(String[] args) {
        System.setProperty("reactor.ipc.netty.workerCount", "1");
        System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
        SpringApplication.run(MonoApplication.class, args);
    }

}
