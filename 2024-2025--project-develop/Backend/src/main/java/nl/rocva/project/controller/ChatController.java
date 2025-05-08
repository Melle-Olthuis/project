package nl.rocva.project.controller;

import nl.rocva.project.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage send(ChatMessage message) {
        System.out.println("Received message: " + message);
        return message;
    }
}
