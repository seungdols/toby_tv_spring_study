package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;
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

    public Completion(Consumer<ResponseEntity<String>> con) {
        this.con = con;
    }

    public Completion() {
    }

    public void andAccept(Consumer<ResponseEntity<String>> con) {
        Completion c = new Completion(con);
        this.next = c;
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
        }
    }


}
