package com.himera.Messengermongo.services;

import com.himera.Messengermongo.models.Message;
import com.himera.Messengermongo.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessage response = new ResponseMessage(message);
        notificationService.sendGlobalNotification();

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(Message message) throws ParseException {
        ResponseMessage response = new ResponseMessage(message.getMessageContent(),
                "left",
                message.getSender(),
                message.getStringTime(),
                message.getIdChat());

        notificationService.sendPrivateNotification(message.getRecipient());
        messagingTemplate.convertAndSendToUser(message.getRecipient(), "/topic/private-messages", response);
    }
}