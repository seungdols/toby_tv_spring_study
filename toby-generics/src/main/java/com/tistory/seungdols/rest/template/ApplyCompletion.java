package com.tistory.seungdols.rest.template;

import java.util.function.Function;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-24
 */
public class ApplyCompletion extends Completion {

    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn;

    @Override
    void run(ResponseEntity<String> value) {
        ListenableFuture<ResponseEntity<String>> lf = fn.apply(value);
        lf.addCallback(s -> complete(s), e -> error(e));
    }

    public ApplyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> fn) {
        this.fn = fn;
    }

}
