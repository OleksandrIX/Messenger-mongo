package com.himera.Messengermongo.services;

import com.himera.Messengermongo.models.Chat;
import com.himera.Messengermongo.models.User;
import com.himera.Messengermongo.repository.ChatRepository;
import com.himera.Messengermongo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public ChatService(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public Chat findById(String id) {
        return chatRepository.findById(id).get();
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    public Chat addChat(User sender, User recipient) {
        String nameChat = sender.getUsername() + "-" + recipient.getUsername();
        List<User> users = List.of(sender, recipient);
        Chat chatFromDb = chatRepository.findByName(nameChat);
        if (chatFromDb == null) {
            Chat chat = new Chat(nameChat, users);
            chatRepository.save(chat);
            sender.setChat(chat);
            recipient.setChat(chat);
            userRepository.saveAll(users);
            return chat;
        }
        return chatFromDb;
    }
}
