package com.tistory.seungdols.rest.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @PACKAGE com.tistory.seungdols.rest.CompletableFuture
 * @AUTHOR seungdols
 * @DATE 2018-11-04
 */
@Slf4j
public class CFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture<Integer> future = CompletableFuture.completedFuture(1);
        System.out.println(future.get());

        CompletableFuture.runAsync(() -> {
            log.info("runAsync");
        }).thenRun(() -> {
            log.info("thenRun");
        });

        CompletableFuture.supplyAsync(() -> {
            log.info("runAsync");
            return 1;
        }).thenApply(s -> {
            log.info("thenRun");
            return s + 1;
        }).thenAccept(s2 -> {
            log.info("thenRun {} ", s2);
        });

        CompletableFuture.supplyAsync(() -> {
            log.info("runAsync");
            return 1;
        }, es).thenCompose(s -> {
            log.info("thenRun");
            return CompletableFuture.completedFuture(s + 2);
        }).thenAcceptAsync(s2 -> {
            log.info("thenRun {} ", s2);
        }, es);

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
