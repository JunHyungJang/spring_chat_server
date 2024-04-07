package net.stegard.messagingstompwebsocket.service;

import net.stegard.messagingstompwebsocket.jpa.ChatEntity;
import net.stegard.messagingstompwebsocket.jpa.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }


    @Override
    public ChatEntity createChat(ChatEntity chatEntity){
        return chatRepository.save(chatEntity);
    }
}
