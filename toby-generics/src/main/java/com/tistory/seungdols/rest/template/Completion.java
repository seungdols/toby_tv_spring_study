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
    Consumer<ResponseEntity<String>> con;
    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;

    public Completion() {
    }

    public Completion(Consumer<ResponseEntity<String>> con) {
        this.con = con;
    }

    public Completion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        this.fn = fn;
    }

    public void andAccept(Consumer<ResponseEntity<String>> con) {
        Completion c = new Completion(con);
        this.next = c;
    }

    public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        Completion c = new Completion(fn);
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

    private void error(Throwable e) {
    }

    private void complete(ResponseEntity<String> s) {
        if (next != null) {
            next.run(s);
        }
    }

    void run(ResponseEntity<String> value) {
        if (con != null) {
            con.accept(value);
        } else if (fn != null) {
            ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
            lf.addCallback(s -> complete(s), e -> error(e));
        }
    }


}
