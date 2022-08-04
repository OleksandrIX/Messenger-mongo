package com.himera.Messengermongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Document
public class Chat {
    @Id
    private String id;
    private String name;
    private List<User> users;
    private List<Message> messages;

    public Chat() {
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Chat(String name) {
        this.name = name;
        this.users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Chat(String name, List<User> users) {
        this.name = name;
        this.users = users;
        this.messages = new ArrayList<>();
    }

    public Chat(String id, String name, List<User> users, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUser(User user) {
        users.add(user);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getSortMessage() {

        return messages.stream().sorted(Comparator.comparing(Message::getTime)).collect(Collectors.toList());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return getId().equals(chat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users, messages);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
