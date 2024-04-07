package net.stegard.messagingstompwebsocket.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import net.stegard.messagingstompwebsocket.config.KafkaProducer;
import net.stegard.messagingstompwebsocket.dto.Greeting;
import net.stegard.messagingstompwebsocket.jpa.ChatEntity;
import net.stegard.messagingstompwebsocket.service.ChatService;
import net.stegard.messagingstompwebsocket.service.ChatServiceImpl;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.UUID;
//
@Controller
@Slf4j
public class GreetingController {

    KafkaProducer kafkaProducer;
    ObjectMapper objectMapper;
    ChatService chatService;
    public GreetingController(KafkaProducer kafkaProducer, ObjectMapper objectMapper,ChatService chatService) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
        this.chatService = chatService;
    }

    //    @MessageMapping("/hello")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
//    @SendTo("/topic/greetings")
    @MessageMapping("/chat/{roomId}")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
//    @SendTo("/sub/chatroom/{roomId}")
    public void greeting(@Payload Map<String, Object> params, @DestinationVariable String roomId) throws Exception {
//        Thread.sleep((int)(Math.random()*2000)); // simulated delay
        String Uid = UUID.randomUUID().toString();
        params.put("id",Uid);
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setContent((String) params.get("content"));
        chatEntity.setId((String) params.get("id"));
        chatEntity.setRoomId((String) params.get("roomId"));
        chatEntity.setName((String) params.get("name"));
        String JsonObject = objectMapper.writeValueAsString(params);


        log.info("chatentity"ㅎㅑㅅ ㅁ + chatEntity.getId() + chatEntity.getName()+chatEntity.getRoomId()+chatEntity.getContent());
        chatService.createChat(chatEntity);
        kafkaProducer.send("chatting",JsonObject);

    }

}
