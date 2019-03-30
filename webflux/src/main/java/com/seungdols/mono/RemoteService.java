package com.seungdols.mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018. 8. 22.
 */
@SpringBootApplication
public class RemoteService {

    @RestController
    public static class MyController {

        @RequestMapping("/service1")
        public String service1(String req) throws InterruptedException {

            Thread.sleep(2000);
            return req + " /service1"; //http body 내에 포함 되게 된다.

        }

        @RequestMapping("/service2")
        public String service2(String req) throws InterruptedException {

            Thread.sleep(2000);
            return req + " /service2"; //http body 내에 포함 되게 된다.

        }

    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(RemoteService.class, args);
    }
}
