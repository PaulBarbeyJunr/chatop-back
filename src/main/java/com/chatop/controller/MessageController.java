package com.chatop.controller;

import com.chatop.dto.MessageRequest;
import com.chatop.dto.MessageResponse;
import com.chatop.service.MessageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public MessageResponse createMessage(@RequestBody MessageRequest request) {
        messageService.create(request);
        return new MessageResponse("Message send with success");
    }
}
