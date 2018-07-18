package com.tistory.seungdols;

import java.util.*;

/**
 * @PACKAGE com.tistory.seungdols
 * @AUTHOR seungdols
 * @DATE 2018. 7. 18.
 */
public class Generics {

    static <T> void method(T t,  List<T> list) {

    }


    public static void main(String[] args) {
      Generics.<Integer>method(1, Arrays.asList(1,2,3));

      List<String> str = new ArrayList<>(); //java7
      List<String> c = Collections.emptyList();//arguments가 없어도 알아서 타입을 추론 해줌.
    }

}
