package com.seungdols.mono;

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

@SpringBootApplication
@EnableAsync
public class MonoApplication {

    @RestController
    public static class MyController {
        static String url1 = "http://localhost:8081/service1?req={req}";
        static String url2 = "http://localhost:8081/service2?req={req}";

        @Autowired
        MyService myService;

        WebClient webClient = WebClient.create();

        @GetMapping("/rest")
        public Mono<String> rest(int idx) {
            return webClient.get().uri(url1, idx).exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                    .flatMap(res1 -> webClient.get().uri(url2, res1).exchange())
                    .flatMap(c -> c.bodyToMono(String.class))
                    .flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)));
        }
    }


    @Service
    public static class MyService {

        @Async
        public CompletableFuture<String> work (String req) {
            return CompletableFuture.completedFuture(req + "/asyncWork");
        }
    }
    @Bean
    public ThreadPoolTaskExecutor myThreadTool() {
        /**
         * Core, Max Pool, Queue 총 3가지를 설정 할 수 있다.
         * Core가 다 차면, Queue를 채우고, Max Pool 사이즈까지 채운다. (Java 기본 동작)
         */
        ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();//Queue Size가 무한대임.
        te.setCorePoolSize(1);
        te.setMaxPoolSize(1);
        te.initialize();
        return te;
    }

    public static void main(String[] args) {
        System.setProperty("reactor.ipc.netty.workerCount", "2");
        System.setProperty("reactor.ipc.netty.pool.maxConnections", "2000");
        SpringApplication.run(MonoApplication.class, args);
    }

}
