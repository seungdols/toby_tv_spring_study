package com.tistory.seungdols.generics;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 26.
 */
public class IntersectionType {

    interface DelegateTo<T> {
        T delegate();
    }

    interface Hello extends DelegateTo<String> {
        default void hello() {
            System.out.println("Hello " + delegate());
        }
    }

    interface Uppercase extends DelegateTo<String> {
        default void uppercase() {
            System.out.println(delegate().toUpperCase());
        }
    }

    public static void main(String[] args) {
        run((DelegateTo<String> & Hello & Uppercase) () -> "Seungdols Company", o -> {
            o.hello();
            o.uppercase();
        });
    }

    //Callback 방식
    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

}
