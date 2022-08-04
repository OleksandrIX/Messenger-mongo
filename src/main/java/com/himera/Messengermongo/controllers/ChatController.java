package com.himera.Messengermongo.controllers;

import com.himera.Messengermongo.models.Chat;
import com.himera.Messengermongo.models.Message;
import com.himera.Messengermongo.models.ResponseMessage;
import com.himera.Messengermongo.models.User;
import com.himera.Messengermongo.services.ChatService;
import com.himera.Messengermongo.services.UserService;
import com.himera.Messengermongo.services.WSService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
public class ChatController {
    private final WSService service;
    private final UserService userService;
    private final ChatService chatService;

    public ChatController(WSService service, UserService userService, ChatService chatService) {
        this.service = service;
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "id", required = false) String id,
                       Principal principal,
                       Model model){
        List<Chat> chatList = userService.findByUsername(principal.getName()).getChats();
        List<User> userList = userService.findAll();
        User user = userService.findByUsername(principal.getName());
        userList.remove(user);

        if (chatList != null){
            for (Chat chat: chatList) {
                User friend = chat.getUsers().stream().filter(usr -> !usr.equals(user)).findAny().get();
                chat.setName(friend.getUsername());
            }
        }

        if(id != null){
            String recipient = chatService.findById(id).getUsers().stream()
                    .filter(usr -> !(usr.equals(user))).findAny().get().getUsername();
            List<Message> messages = chatService.findById(id).getSortMessage();
            for (Message message : messages) {
                if (!message.getSender().equals(principal.getName())) {
                    message.setMessageSide("left");
                }
            }
            model.addAttribute("idChat", id);
            model.addAttribute("chats", chatList);
            model.addAttribute("recipient", recipient);
            model.addAttribute("messages", messages);
        }else {
            model.addAttribute("idChat", null);
            model.addAttribute("users", userList);
            model.addAttribute("user", user);
        }
        return "chat";
    }

    @PostMapping("/mes")
    public String createChat(@RequestParam("recipientId") String id, Principal principal, Model model){
        User user = userService.findByUsername(principal.getName());
        User friend = userService.findById(id);
        if(friend != null){
            Chat chat = chatService.addChat(user, friend);
            if (chat != null) {
                model.addAttribute("chat", chat);
                model.addAttribute("user", user);
                return "redirect:/chat?id=" + chat.getId();
            }else {
                System.out.println("Chat is null");
                return "redirect:/chat";
            }
        }else {
            System.out.println("Friend is null");
            return "redirect:/chat";
        }
    }

    @PostMapping("/open")
    public String openChat(@RequestParam("userId") String id, Model model, Principal principal){
        User user = userService.findById(id);
        User authUser = userService.findByUsername(principal.getName());
        List<Chat> chatList = user.getChats();
        List<User> userList;
        Chat chat = null;
        if (chatList != null) {
            for (Chat cht : chatList) {
                userList = cht.getUsers();
                System.out.println(userList.contains(authUser));
                if (userList.contains(authUser)) {
                    chat = cht;
                    break;
                }
            }
        }else {
            return "redirect:/chat";
        }
        if (chat != null) {
            model.addAttribute("chat", chat);
            model.addAttribute("user", user);
            return "redirect:/chat?id=" + chat.getId();
        } else {
            System.out.println("Chat null");
            return "redirect:/chat";
        }
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message,
                                             final Principal principal) throws InterruptedException, ParseException {
        Thread.sleep(0, 100);
        Message newMessage = new Message(message.getMessageContent(), principal.getName(),
                message.getRecipient(), message.getMessageSide(), message.getIdChat());
        service.notifyUser(newMessage);
        Chat chat = chatService.findById(message.getIdChat());
        chat.setMessage(newMessage);
        chatService.save(chat);
        return new ResponseMessage(principal.getName() + " " + HtmlUtils.htmlEscape(message.getMessageContent()),
                HtmlUtils.htmlEscape(message.getMessageSide()),
                HtmlUtils.htmlEscape(newMessage.getSender()),
                HtmlUtils.htmlEscape(newMessage.getStringTime()),
                HtmlUtils.htmlEscape(newMessage.getIdChat())
        );
    }
}
