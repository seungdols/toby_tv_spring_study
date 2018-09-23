package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-23
 */
public class Completion {

    Completion next;


    public void andAccept(Consumer<ResponseEntity<String>> con) {
        Completion c = new AcceptCompletion(con);
        this.next = c;
    }

    public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        Completion c = new ApplyCompletion(fn);
        this.next = c;
        return c;
    }

    public static Completion from(ListenableFuture<ResponseEntity<String>> lf) {
        Completion c = new Completion();
        lf.addCallback(s -> {
            c.complete(s);
        }, e -> {
            c.error(e);
        });

        return c;
    }

    public void error(Throwable e) {
    }

    public void complete(ResponseEntity<String> s) {
        if (next != null) {
            next.run(s);
        }
    }

    void run(ResponseEntity<String> value) {

    }


}
