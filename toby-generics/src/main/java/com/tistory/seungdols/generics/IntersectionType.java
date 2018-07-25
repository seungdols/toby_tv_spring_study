package com.tistory.seungdols.generics;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 26.
 */
public class IntersectionType implements Serializable{

    interface Hello {
        default void hello() {
            System.out.println("Hello");
        }
    }

    interface Hi {
        default void hi() {
            System.out.println("Hi");
        }
    }


    public static void main(String[] args) {
//        hello((Function & Serializable & Cloneable) s -> s); //casting OK.
        hello((Function & Hello & Hi) s -> s);
    }
//
//    private static void hello(Function t) {
//
//    }
    private static <T extends Function & Hello & Hi> void hello(T t) {
        t.hello();
        t.hi();
    }


}
