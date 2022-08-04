package com.himera.Messengermongo.models;

import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Message {
    private String id = String.valueOf(new ObjectId());
    private String idChat;
    private String messageContent;
    private String sender;
    private String recipient;
    private String messageSide;
    private Date time;

    public Message() {
    }

    public Message(String messageContent, String messageSide) {
        this.messageContent = messageContent;
        this.messageSide = messageSide;
    }

    public Message(String messageContent, String sender, String recipient, String messageSide) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.recipient = recipient;
        this.messageSide = messageSide;
        this.time = new Date();
    }

    public Message(String id, String idChat, String messageContent, String sender, String recipient, String messageSide) {
        this.id = id;
        this.idChat = idChat;
        this.messageContent = messageContent;
        this.sender = sender;
        this.recipient = recipient;
        this.messageSide = messageSide;
        this.time = new Date();
    }

    public Message(String idChat, String messageContent, String sender, String recipient, String messageSide) {
        this.idChat = idChat;
        this.messageContent = messageContent;
        this.sender = sender;
        this.recipient = recipient;
        this.messageSide = messageSide;
        this.time = new Date();
    }


    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getId() {
        return id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageSide() {
        return messageSide;
    }

    public void setMessageSide(String messageSide) {
        this.messageSide = messageSide;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStringTime() throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm");
        return formatDate.format(time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getId().equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChat, messageContent, sender, recipient, messageSide, time);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", idChat='" + idChat + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", messageSide='" + messageSide + '\'' +
                ", time=" + time +
                '}';
    }
}
