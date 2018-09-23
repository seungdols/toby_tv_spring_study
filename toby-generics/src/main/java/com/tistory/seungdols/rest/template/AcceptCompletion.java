package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-24
 */
public class AcceptCompletion<S> extends Completion<S, Void> {

    Consumer<S> con;

    public AcceptCompletion(Consumer<S> con) {
        this.con = con;
    }

    @Override
    void run(S value) {
        con.accept(value);
    }


}
