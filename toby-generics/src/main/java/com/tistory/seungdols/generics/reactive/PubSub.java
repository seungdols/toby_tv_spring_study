package com.tistory.seungdols.generics.reactive;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive
 * @AUTHOR seungdols
 * @DATE 2018. 8. 12.
 */
public class PubSub {
    public static void main(String[] args) {
        //  Observerable ---- Publisher
        //  Observer    ----- Subsriber

        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

        Publisher publisher = new Publisher() {
            @Override
            public void subscribe(Subscriber s) {
                Iterator<Integer> it = itr.iterator();
                s.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        try {

                            while (n-- > 0) {
                                if (it.hasNext()) {
                                    s.onNext(it.next());
                                } else {
                                    s.onComplete();
                                    break;
                                }
                            }
                        } catch (RuntimeException e) {
                            s.onError(e);
                        }
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
                this.subscription = s;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext " + integer);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        publisher.subscribe(subscriber);
    }
}
