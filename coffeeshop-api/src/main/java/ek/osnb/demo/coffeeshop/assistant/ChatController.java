package ek.osnb.demo.coffeeshop.assistant;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
public class ChatController {
    private final AssistantService assistantService;

    public ChatController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }
    @PostMapping("/chat")
    public ChatReply chat (@RequestBody ChatRequest request){
         String reply = assistantService.answer(request.message());
         return new ChatReply(reply);
    }
}