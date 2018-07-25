package com.tistory.seungdols;

import java.util.*;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
//        System.out.println(isEmpty(list));

        System.out.println(frequency(list, 3));
    }

    //generics
//    static <T> long frequency(List<T> list,  T elem) {
//        return list.stream().filter(s -> s.equals(elem)).count();
//    }

    //wildcards
    static long frequency(List<?> list,  Object elem) {
        return list.stream().filter(s -> s.equals(elem)).count();
    }

    //generics
//    private static <T> boolean isEmpty(List<T> list) {
//        return list.size() == 0;
//    }

    //wildcards
//    private static boolean isEmpty(List<?> list) {
//        return list.size() == 0;
//    }

}
