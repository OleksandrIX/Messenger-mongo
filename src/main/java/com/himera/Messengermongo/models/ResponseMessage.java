package com.himera.Messengermongo.models;

import java.util.Date;
import java.util.Objects;

public class ResponseMessage {
    private String idChat;
    private String messageContent;
    private String sender;
    private String recipient;
    private String messageSide;
    private Date time;

    public ResponseMessage() {
    }

    public ResponseMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    public ResponseMessage(String messageContent, String messageSide) {
        this.messageContent = messageContent;
        this.messageSide = messageSide;
    }

    public ResponseMessage(String messageContent, String sender, String messageSide) {
        this.messageContent = messageContent;
        this.sender = sender;
        this.messageSide = messageSide;
        this.time = new Date();
    }

    public ResponseMessage(String idChat, String messageContent, String sender, String messageSide) {
        this.idChat = idChat;
        this.messageContent = messageContent;
        this.sender = sender;
        this.messageSide = messageSide;
        this.time = new Date();
    }

    public ResponseMessage(String idChat, String messageContent, String sender, String recipient, String messageSide) {
        this.idChat = idChat;
        this.messageContent = messageContent;
        this.sender = sender;
        this.recipient = recipient;
        this.messageSide = messageSide;
        this.time = new Date();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage that = (ResponseMessage) o;
        return Objects.equals(idChat, that.idChat) && Objects.equals(messageContent, that.messageContent) && Objects.equals(sender, that.sender) && Objects.equals(recipient, that.recipient) && Objects.equals(messageSide, that.messageSide) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChat, messageContent, sender, recipient, messageSide, time);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "idChat='" + idChat + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", messageSide='" + messageSide + '\'' +
                ", time=" + time +
                '}';
    }
}
