package ek.osnb.demo.coffeeshop.assistant;

import java.util.List;

public record AiRequest (String model, List<Message> messages, int max_tokens) {
    public record Message(String role, String content){}
}
