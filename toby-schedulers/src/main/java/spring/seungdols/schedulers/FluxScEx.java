package spring.seungdols.schedulers;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * @PACKAGE spring.seungdols.schedulers
 * @AUTHOR seungdols
 * @DATE 05/01/2019
 */
@Slf4j
public class FluxScEx {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .publishOn(Schedulers.newSingle("pub"))
                .log()
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(System.out::println);

        //Deamon Thread
        Flux.interval(Duration.ofMillis(500)).take(10).subscribe(System.out::println);
    }
}
