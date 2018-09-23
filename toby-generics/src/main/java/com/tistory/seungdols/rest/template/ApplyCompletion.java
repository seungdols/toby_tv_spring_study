package com.tistory.seungdols.rest.template;

import java.util.function.Function;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-24
 */
public class ApplyCompletion<S, T> extends Completion<S, T> {

    Function<S, ListenableFuture<T>> fn;

    public ApplyCompletion(Function<S, ListenableFuture<T>> fn) {
        this.fn = fn;
    }

    @Override
    void run(S value) {
        ListenableFuture<T> lf = fn.apply(value);
        lf.addCallback(s -> complete(s), e -> error(e));
    }

}
