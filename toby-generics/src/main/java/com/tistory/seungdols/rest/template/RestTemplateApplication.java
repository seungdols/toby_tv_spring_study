package com.tistory.seungdols.rest.template;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
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
public class RestTemplateApplication {

    @RestController
    public static class MyController {

        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        @RequestMapping("/rest")
        public DeferredResult<String> rest(int idx) {
            DeferredResult<String> deferredResult = new DeferredResult<>();
            ListenableFuture<ResponseEntity<String>> res1 = rt.getForEntity("http://localhost:8081/service1?req={req}", String.class, "hello" + idx);

//            res.get() 이렇게 쓰면 블럭킹.

            //Callback 방식으로 비동기 요청에 대한 결과를 가공 하는 방법.
            res1.addCallback(s -> {
                ListenableFuture<ResponseEntity<String>> res2 = rt.getForEntity("http://localhost:8081/service2?req={req}", String.class, s.getBody());
                res2.addCallback(s2 -> {
                    deferredResult.setResult(s2.getBody());
                }, e2 -> {
                    deferredResult.setErrorResult(e2.getMessage());
                });
            }, e -> {
                deferredResult.setErrorResult(e.getMessage());
            });

            return deferredResult; //http body 내에 포함 되게 된다.


        }

    }

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);
    }
}
