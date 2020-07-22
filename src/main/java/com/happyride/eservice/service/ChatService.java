package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.Chat;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Optional<Chat> findById(Long id) {
        return chatRepository.findById(id);
    }

    public List<Chat> findBySendTo(Users users) {
        return chatRepository.findBySendTo(users);
    }

    public List<Chat> findBySendFrom(Users users) {
        return chatRepository.findBySendFrom(users);
    }

    public List<Chat> findAllChat() {
        return chatRepository.findAll();
    }

    public List<Chat> findBySendToAndSendFrom(Users sendTo, Users sendFrom) {
        return chatRepository.findBySendToAndSendFromOrderByTime(sendTo, sendFrom);
    }

    public List<Chat> findAllMessagesBySenderAndReceiver(Users sendTo, Users sendFrom, Users sendTo1, Users sendFrom1) {
        return chatRepository.findBySendFromAndSendToOrSendToAndSendFromOrderByTime(sendTo, sendFrom, sendTo1, sendFrom1);
    }

    public List<Chat> findAllMessagesBySenderAndReceiverSeen(Users sendTo, Users sendFrom, boolean seen) {
        return chatRepository.findBySendFromAndSendToAndSeen(sendTo, sendFrom, seen);
    }

    public List<Chat> findFriend(Users users, Users users1) {
        return chatRepository.findAllFriendChat(users1.getId());
    }

    public List<Chat> findAllChatForUser(Users distinctUser, Users sendFrom, Users sendTo) {
        return chatRepository.findDistinctBySendToAndSendFromAndSendToOrderByTimeDesc(distinctUser, sendFrom, sendTo);
    }

    public List<Chat> findUnseenMessages(Users sender, Boolean seen, Users receiver) {
        return chatRepository.findBySendFromAndSeenAndSendTo(sender, seen, receiver);
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

}
