package com.tistory.seungdols.rest.template;

import java.util.function.Consumer;
import org.springframework.http.ResponseEntity;

/**
 * @PACKAGE com.tistory.seungdols.rest.template
 * @AUTHOR seungdols
 * @DATE 2018-09-24
 */
public class AcceptCompletion extends Completion {

    Consumer<ResponseEntity<String>> con;

    @Override
    void run(ResponseEntity<String> value) {
        con.accept(value);
    }

    public AcceptCompletion(Consumer<ResponseEntity<String>> con) {
        this.con = con;
    }

}
