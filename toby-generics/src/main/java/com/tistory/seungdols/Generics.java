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
    static long countGreaterThan(Integer[] arr, Integer elem) {
        return Arrays.stream(arr).filter(s -> s > elem).count();
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[] {1,2,3,4,5,6,7,8,9};
        System.out.println(countGreaterThan(arr, 4));
    }


}
