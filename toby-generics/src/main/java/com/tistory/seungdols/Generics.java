package com.tistory.seungdols;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {
    static class Hello<T> { //T -> type parameter

    }

    static void print(String value) {
        System.out.println(value);
    }


    public static void main(String[] args) {
        new Hello<String>(); //type argument
    }
}
