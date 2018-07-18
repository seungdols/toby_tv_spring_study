package com.tistory.seungdols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    static <T> void print(T t) {
        System.out.println(t.toString());
    }

    public static void main(String[] args) {
        print("Hello");
        print("Seungdols Company");

    }


}
