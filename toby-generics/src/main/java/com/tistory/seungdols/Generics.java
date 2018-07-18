package com.tistory.seungdols;

import java.util.*;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    static void printList(List<Object> list) {
        list.forEach(s -> System.out.print(s));
    }

    static void printList2(List<?> list) {
        list.forEach(s -> System.out.print(s));
    }


    public static void main(String[] args) {
        printList(Arrays.asList(1,2,3,4,5,6));
        System.out.println();
        printList2(Arrays.asList(1,2,3,4,5,6));

        List<Integer> list = Arrays.asList(1, 2, 3);
        printList2(list);
    }

}
