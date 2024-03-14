package net.stegard.messagingstompwebsocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import net.stegard.messagingstompwebsocket.config.KafkaProducer;
import net.stegard.messagingstompwebsocket.dto.Greeting;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;

@Controller
@Slf4j
public class GreetingController {

    KafkaProducer kafkaProducer;
    ObjectMapper objectMapper;
    public GreetingController(KafkaProducer kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    //    @MessageMapping("/hello")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
//    @SendTo("/topic/greetings")
    @MessageMapping("/chat/{roomId}")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
//    @SendTo("/sub/chatroom/{roomId}")
    public void greeting(@Payload Map<String, Object> params, @DestinationVariable String roomId) throws Exception {
        Thread.sleep((int)(Math.random()*2000)); // simulated delay
//        return new Greeting("Hello, " + HtmlUtils.htmlEscape((String) params.get("name"))+ "!");
        log.info("ROOMID" + (String)params.get("roomId"));

        String JsonObject = objectMapper.writeValueAsString(params);
        log.info("JSONOBJECT "+ JsonObject);

        kafkaProducer.send("chatting",JsonObject);

    }

}
