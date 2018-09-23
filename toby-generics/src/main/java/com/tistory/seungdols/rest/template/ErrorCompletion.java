package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-24
 */
public class ErrorCompletion<T> extends Completion<T, T> {

    Consumer<Throwable> error;

    public ErrorCompletion(Consumer<Throwable> error) {
        this.error = error;
    }

    @Override
    public void error(Throwable e) {
        error.accept(e);
    }

    @Override
    void run(T value) {
        if (next != null) {
            next.run(value);
        }
    }
}
