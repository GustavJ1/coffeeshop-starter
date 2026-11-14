package ek.osnb.demo.coffeeshop.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
class AssistantService{
    private final ChatClient chatclient;


    AssistantService(ChatClient.Builder builder) {
        this.chatclient = builder.build();
    }
    String answer (String message){
        return chatclient.prompt()
                .system("you are a helpfull assistant. /no_think")
                .user(message)
                .call()
                .content();
    }

}