package com.seungdols.mono;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018. 8. 22.
 */
@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";

        CyclicBarrier barrier = new CyclicBarrier(100);

        for (int i = 1; i <= 100; i++) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);

                barrier.await();

                log.info("Thread {}", idx);

                StopWatch stopWatch = new StopWatch();
                stopWatch.start();

                String res = restTemplate.getForObject(url, String.class, idx);

                stopWatch.stop();
                log.info("Elapsed: {} {} / {}", idx, stopWatch.getTotalTimeSeconds(), res);

                return null;
            });
        }

//        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);

        main.stop();
        log.info("Total: {}", main.getTotalTimeSeconds());

    }

}
