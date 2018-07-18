package com.tistory.seungdols;

import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    public static void main(String[] args) {
        Integer integer = 10;
        Number number = integer;

        List<Integer> integerList = new ArrayList<>();
//        Quiz1)
//        List<Number> numberList = integerList;

        ArrayList<Integer> arrayList = new ArrayList<>();
//        Quiz2)
//        List<Integer> integers = arrayList;
    }

}
