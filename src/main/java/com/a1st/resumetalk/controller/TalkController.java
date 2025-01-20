package com.a1st.resumetalk.controller;

import dev.langchain4j.chain.ConversationalRetrievalChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/v1/talk")
@RequiredArgsConstructor
@Slf4j
public class TalkController {

    private final ConversationalRetrievalChain conversationalRetrievalChain;

    @PostMapping
    public String TalkWithPdf(@RequestBody String text) {
        var response = conversationalRetrievalChain.execute(text);
        log.info("Response: {}", response);
        return response;
    }
}
