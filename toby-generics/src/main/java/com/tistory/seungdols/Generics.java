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

    <T> void print(T t) { // Method Level에 Type Paramter
        System.out.println(t.toString());
    }

    public static void main(String[] args) {
        new Generics().print("Hello");
        new Generics().print("Seungdols Company");

    }


}
