package com.crypto.trading.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send") // Този мапинг ще се използва за изпращане на съобщения.
    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/topic/messages", message); // Изпраща съобщението към всички клиенти, свързани с "/topic/messages".
    }
}
