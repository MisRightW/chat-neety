package com.fengling.chatserver.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class ChatRequest {


    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<MessagesDTO> messages;

    @NoArgsConstructor
    @Data
    public static class MessagesDTO {
        @JsonProperty("role")
        private String role;
        @JsonProperty("content")
        private String content;
    }
}
