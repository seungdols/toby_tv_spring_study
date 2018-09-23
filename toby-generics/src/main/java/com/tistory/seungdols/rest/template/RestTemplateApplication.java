package com.tistory.seungdols.rest.template;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018. 8. 21.
 */
@SpringBootApplication
@EnableAsync
public class RestTemplateApplication {

    @RestController
    public static class MyController {

        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
        @Autowired
        MyService service;

        @RequestMapping("/rest")
        public DeferredResult<String> rest(int idx) {
            DeferredResult<String> deferredResult = new DeferredResult<>();
            String url1 = "http://localhost:8081/service1?req={req}";
            String url2 = "http://localhost:8081/service2?req={req}";

            Completion.from(rt.getForEntity(url1, String.class, "hello" + idx))
                      .andAccept(s -> deferredResult.setResult(s.getBody()));

//            ListenableFuture<ResponseEntity<String>> res1 = rt.getForEntity(url1, String.class, "hello" + idx);

//            res.get() 이렇게 쓰면 블럭킹.

//            //Callback 방식으로 비동기 요청에 대한 결과를 가공 하는 방법....Callback Hell....!!!Fuck Callback..!
//            res1.addCallback(s -> {
//                ListenableFuture<ResponseEntity<String>> res2 = rt.getForEntity(url2, String.class, s.getBody());
//                res2.addCallback(s2 -> {
//                    ListenableFuture<String> workRes = service.work(s2.getBody());
//                    workRes.addCallback(s3 -> {
//                        deferredResult.setResult(s3);
//                    }, e3 -> {
//                        deferredResult.setErrorResult(e3.getMessage());
//                    });
//                }, e2 -> {
//                    deferredResult.setErrorResult(e2.getMessage());//에러 처리에 대한 중복코드 발생.
//                });
//            }, e -> {
//                deferredResult.setErrorResult(e.getMessage());
//            });

            return deferredResult; //http body 내에 포함 되게 된다.


        }

        @Service
        public static class MyService {

            @Async
            public ListenableFuture<String> work(String req) {
                return new AsyncResult<>(req + "/asyncWork");
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

    }

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);
    }
}

