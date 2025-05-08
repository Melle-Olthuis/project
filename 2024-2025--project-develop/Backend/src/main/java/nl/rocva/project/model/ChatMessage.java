package nl.rocva.project.model;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;
    private String content;
}
