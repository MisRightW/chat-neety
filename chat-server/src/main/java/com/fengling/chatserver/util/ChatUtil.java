package com.fengling.chatserver.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fengling.chatserver.pojo.ChatRequest;
import com.fengling.chatserver.pojo.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author fengling
 */
@Component
public class ChatUtil {

//    @Value("${chat.keys}")
    private final static String keys = "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";

    /**
     * curl https://api.openai.com/v1/chat/completions \
     * -H 'Content-Type: application/json' \
     * -H 'Authorization: Bearer YOUR_API_KEY' \
     * -d '{
     * "model": "gpt-3.5-turbo",
     * "messages": [{"role": "user", "content": "Hello!"}]
     * }'
     */
    private static final String API = "https://api.openai.com/v1/chat/completions";

    public static ChatResponse create(ChatRequest request) {
        String result = HttpRequest.post(API)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + keys).body(JSONUtil.toJsonStr(request)).execute().body();
        ChatResponse response = JSONUtil.toBean(result, ChatResponse.class);
        return response;
    }

    /**
     * {
     * "model": "gpt-3.5-turbo",
     * "messages": [{"role": "user", "content": "Hello!"}]
     * }
     *
     * @param args
     */
    public static void main(String[] args) {
        ChatRequest.MessagesDTO dto = new ChatRequest.MessagesDTO();
        dto.setRole("user");
        dto.setContent("你是谁？");
        List<ChatRequest.MessagesDTO> messages = Arrays.asList(dto);
        ChatRequest request = new ChatRequest();
        request.setModel("gpt-3.5-turbo");
        request.setMessages(messages);
        ChatResponse response = create(request);
        System.out.println("response:" + JSONUtil.toJsonStr(response));
    }

}
