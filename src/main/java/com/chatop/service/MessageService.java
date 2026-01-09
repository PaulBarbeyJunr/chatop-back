package com.chatop.service;

import com.chatop.dto.MessageRequest;
import com.chatop.model.Message;
import com.chatop.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message create(MessageRequest request) {
        Message message = new Message();
        message.setMessage(request.message());
        message.setUserId(request.userId());
        message.setRentalId(request.rentalId());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }
}
