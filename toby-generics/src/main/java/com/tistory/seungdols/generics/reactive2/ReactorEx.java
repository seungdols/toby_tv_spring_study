package com.tistory.seungdols.generics.reactive2;

import reactor.core.publisher.Flux;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive2
 * @AUTHOR seungdols
 * @DATE 2018. 8. 21.
 */
public class ReactorEx {

    public static void main(String[] args) {
        Flux.create(
            e -> {
                e.next(1);
                e.next(2);
                e.next(3);
                e.next(4);

            }).log().subscribe(System.out::println);

        Flux.<Integer>create(e -> {
            e.next(1);
            e.next(2);
            e.next(3);
            e.next(4);
        }).log()
          .map(s -> s * 10)
          .reduce(0, (a, b) -> a + b)
          .log()
          .subscribe(System.out::println);
    }
}
