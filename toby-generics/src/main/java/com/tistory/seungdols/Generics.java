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
        reverse(list);
        System.out.println();
    }
/*
    static <T> void reverse(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, temp.get(list.size() - i - 1));
        }
    }
    */

    /*
    //capture 관련 에러 발생함.
    static void reverse(List<?> list) {
        List<?> temp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, temp.get(list.size() - i - 1));
        }
    }*/

    //아래처럼 Raw Type으로 만들어서 해결 하는 방법
/*    static void reverse(List<?> list) {
        List temp = new ArrayList<>(list);
        List origList = list;
        for (int i = 0; i < list.size(); i++) {
            origList.set(i, temp.get(origList.size() - i - 1));
        }
    }*/

    //아래처럼 강제로 capture 관련한 Helper 메소드로 만들어줌. (모양은 이상하긴 하다.)
    static void reverse(List<?> list) {
        reverseHelper(list);
    }

    private static <T>  void reverseHelper(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, temp.get(list.size() - i - 1));
        }
    }

}
