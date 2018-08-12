package com.tistory.seungdols.generics.reactive;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive
 * @AUTHOR seungdols
 * @DATE 2018. 8. 12.
 */
public class IterableEx {

    public static void main(String[] args) {

        //Iterable <----> Observerable (Duality)
        //Pull     vs       Push

        Iterable<Integer> iterable = () ->
                new Iterator<Integer>() {
                    int anInt = 0;
                    final static int MAX = 10;

                    @Override
                    public boolean hasNext() {
                        return anInt < MAX;
                    }

                    @Override
                    public Integer next() {
                        return ++anInt;
                    }
                };

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

        for (Integer integer : iter) { // iterable의 subtype인 경우 for each loop를 쓸 수 있음.
            System.out.println(integer);
        }

        //Before JDK5.
        for (Iterator<Integer> it = iter.iterator(); it.hasNext(); ) {
            System.out.println(it.next());//Pull 방식
        }

    }
}
