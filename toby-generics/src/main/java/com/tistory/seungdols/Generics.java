package com.tistory.seungdols;

import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {
    //Bounded Type Parameter

    static <T extends List & Serializable & Comparable & Closeable> void print(T t) { //Multiple Bounded

    }

    public static void main(String[] args) {

    }




}
