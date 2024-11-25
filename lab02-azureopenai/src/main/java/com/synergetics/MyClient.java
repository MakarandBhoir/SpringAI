package com.synergetics;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
// @Profile("azureopenai")
public class MyClient {
    private final ChatClient client;

    public MyClient(ChatModel model) {
        client = ChatClient.builder(model).build();
    }

    public String call(String input) {
        return client
                .prompt()
                .user(input)
                .call()
                .content();
    }
}