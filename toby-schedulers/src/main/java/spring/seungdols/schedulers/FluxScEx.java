package spring.seungdols.schedulers;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @PACKAGE spring.seungdols.schedulers
 * @AUTHOR seungdols
 * @DATE 05/01/2019
 */
public class FluxScEx {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .publishOn(Schedulers.newSingle("pub"))
                .log()
                .subscribeOn(Schedulers.newSingle("sub"))
                .subscribe(System.out::println);
    }
}
