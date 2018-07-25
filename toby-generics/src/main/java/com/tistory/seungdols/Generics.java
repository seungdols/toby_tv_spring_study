package com.tistory.seungdols;

import java.util.*;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList();
        System.out.println(max(list));
    }

//    static <T extends Comparable<T>> T max(List<T> list) {
//        return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
//    }

    //wildcards
    static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
    }

}
