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
public class ChatResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("choices")
    private List<ChoicesDTO> choices;
    @JsonProperty("usage")
    private UsageDTO usage;

    @NoArgsConstructor
    @Data
    public static class UsageDTO {
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;
        @JsonProperty("completion_tokens")
        private Integer completionTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }

    @NoArgsConstructor
    @Data
    public static class ChoicesDTO {
        @JsonProperty("index")
        private Integer index;
        @JsonProperty("message")
        private MessageDTO message;
        @JsonProperty("finish_reason")
        private String finishReason;

        @NoArgsConstructor
        @Data
        public static class MessageDTO {
            @JsonProperty("role")
            private String role;
            @JsonProperty("content")
            private String content;
        }
    }
}
