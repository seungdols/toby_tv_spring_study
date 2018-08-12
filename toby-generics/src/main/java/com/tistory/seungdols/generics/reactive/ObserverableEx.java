package com.tistory.seungdols.generics.reactive;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive
 * @AUTHOR seungdols
 * @DATE 2018. 8. 12.
 */
public class ObserverableEx {

    static class InObserverable extends Observable implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                //Push 방식
                notifyObservers(i);
            }
        }
    }

    public static void main(String[] args) {
        // Source ---> Event (Data) -> Observer
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " " + arg);
            }
        };

        InObserverable inObserverable = new InObserverable();
        inObserverable.addObserver(observer);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(inObserverable);

        System.out.println(Thread.currentThread().getName() + " " + "EXIT");

        executorService.shutdown();

        /*
         *
         * Observerable의 Pattern 단점.
         * 1. Complete? 개념이 없다.
         * 2. Error 처리에 대한 대응이 없다.
         * */
    }
}
