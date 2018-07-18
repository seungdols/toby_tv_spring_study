package com.tistory.seungdols;

import java.util.*;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    static class A {}

    static class B extends A{
    }

    public static void main(String[] args) {
        List<B> listB = new ArrayList<B>();
//        List<A> listA = listB;
//        List<? extends A> listA = listB;
//        List<? super B> listA2 = listB;
//        List<? super A> listA = listB;

//        listA.add(new A());
//        listA.add(null);
    }

}
