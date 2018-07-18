package com.tistory.seungdols;

import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    static <T extends Comparable<T>> long countGreaterThan(T [] arr, T elem) {
        return Arrays.stream(arr).filter(s -> s.compareTo(elem) > 0).count();
    }

    public static void main(String[] args) {
//        Integer[] arr = new Integer[] {1,2,3,4,5,6,7,8,9};
        String [] arr = new String[] {"a", "b", "c", "d","e"};
        System.out.println(countGreaterThan(arr, "c"));
    }


}
