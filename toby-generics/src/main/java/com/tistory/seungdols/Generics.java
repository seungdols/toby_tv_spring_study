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
    static class Hello<T> { //T -> type parameter

    }

    static void print(String value) {
        System.out.println(value);
    }


    public static void main(String[] args) {
        //Raw Type = Generic으로 타입을 주고 했으나, 받을때 타입 정보를 명시 해주지 않음.
        List list = new ArrayList<Integer>();

        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawInts = ints;
        List<Integer> ints2 = rawInts;
        //compile error는 발생하지 않는다.
        List<String> strs = rawInts;
        String str = strs.get(0);
    }
}
