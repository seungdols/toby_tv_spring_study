package com.tistory.seungdols.generics.reactive3;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive3
 * @AUTHOR seungdols
 * @DATE 2018. 9. 2.
 */
public class FluxScEx {

    public static void main(String[] args) {
        Flux.range(1, 10)
            .publishOn(Schedulers.newSingle("pub"))
            .log()
            .subscribeOn(Schedulers.newSingle("sub"))
            .subscribe(System.out::println);

        System.out.println("EXIT");
    }
}
