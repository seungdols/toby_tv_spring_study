package com.tistory.seungdols.generics;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 26.
 */
public class IntersectionType implements Serializable{

    interface Hello extends Function{
        default void hello() {
            System.out.println("Hello");
        }
    }

    interface Hi extends Function {
        default void hi() {
            System.out.println("Hi");
        }
    }

    interface Printer {
        default void print(String str) {
            System.out.println(str);
        }
    }


    public static void main(String[] args) {
//        hello((Function & Serializable & Cloneable) s -> s); //casting OK.
        hello((Function & Hello & Hi) s -> s);
        run((Function & Hello & Hi & Printer) s -> s, o -> {
            o.hello();
            o.hi();
            o.print("Lambda");
        });
    }

//    private static void hello(Function t) {
//
//    }
    private static <T extends Function & Hello & Hi> void hello(T t) {
        t.hello();
        t.hi();
    }

    private static <T extends Function> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

}
