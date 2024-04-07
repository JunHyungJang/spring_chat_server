package net.stegard.messagingstompwebsocket.service;

import net.stegard.messagingstompwebsocket.jpa.ChatEntity;

public interface ChatService {

    ChatEntity createChat(ChatEntity chatEntity);
}
