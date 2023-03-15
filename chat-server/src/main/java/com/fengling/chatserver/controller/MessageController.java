package com.fengling.chatserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengling
 */

@RestController("/api")
@Slf4j
public class MessageController {

    @PostMapping("/question")
    public String getQuestion(String msg) {
        return "";
    }

}
