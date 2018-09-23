package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-23
 */
public class Completion<S, T> {

    Completion next;


    public void andAccept(Consumer<T> con) {
        Completion<T, Void> c = new AcceptCompletion<>(con);
        this.next = c;
    }

    public Completion<T, T> andError(Consumer<Throwable> error) {
        Completion<T, T> c = new ErrorCompletion(error);
        this.next = c;
        return c;
    }

    //retun type에도 Type parameter를 지정해주어야 한다.
    public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
        Completion<T, V> c = new ApplyCompletion(fn);
        this.next = c;
        return c;
    }

    public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
        Completion<S, T> c = new Completion();
        lf.addCallback(s -> {
            c.complete(s);
        }, e -> {
            c.error(e);
        });

        return c;
    }

    public void error(Throwable e) {
        if (next != null) {
            next.error(e);
        }
    }

    public void complete(T s) {
        if (next != null) {
            next.run(s);
        }
    }

    void run(S value) {

    }


}
