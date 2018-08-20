package com.tistory.seungdols.generics.reactive2;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @PACKAGE com.tistory.seungdols.generics.reactive2
 * @AUTHOR seungdols
 * @DATE 2018. 8. 21.
 */
public class DelegateSub implements Subscriber<Integer> {
    Subscriber sub;

    public DelegateSub(Subscriber subscriber) {
        this.sub = subscriber;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);//중계만 해준다.
    }

    @Override
    public void onNext(Integer integer) {
        sub.onNext(integer);
    }

    @Override
    public void onError(Throwable t) {
        sub.onError(t);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
