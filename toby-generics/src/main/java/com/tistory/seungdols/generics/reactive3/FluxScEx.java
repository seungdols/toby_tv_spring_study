package com.tistory.seungdols.generics.reactive3;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Flux;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive3
 * @AUTHOR seungdols
 * @DATE 2018. 9. 2.
 */
public class FluxScEx {

    public static void main(String[] args) throws InterruptedException {
/*        Flux.range(1, 10)
            .publishOn(Schedulers.newSingle("pub"))
            .log()
            .subscribeOn(Schedulers.newSingle("sub"))
            .subscribe(System.out::println);

        System.out.println("EXIT");
*/
        // Deamon Thread들은 남아 있더라도, 죽인다.
        // User Thread는 하나라도 있으면, 죽이지 않는다.
        Flux.interval(Duration.ofMillis(500))
            .take(10)
            .log()
            .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(10);
        System.out.println("EXIT");
    }
}
