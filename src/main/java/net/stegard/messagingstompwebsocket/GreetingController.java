package net.stegard.messagingstompwebsocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

//    @MessageMapping("/hello")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
//    @SendTo("/topic/greetings")
    @MessageMapping("/chat/{roomId}")  // complete endpoint depends on configured application message handler prefix, e.g. /app/hello
    @SendTo("/sub/chatroom/{roomId}")
    public Greeting greeting(@Payload Map<String, Object> params, @DestinationVariable String roomId) throws Exception {
        Thread.sleep((int)(Math.random()*2000)); // simulated delay
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String name = jsonMessage
//        log.info(message.getName());
//        log.info(message.getTest());
        log.info(roomId);
        log.info((String) params.get("name"));
        log.info((String) params.get("test"));
        return new Greeting("Hello, " + HtmlUtils.htmlEscape((String) params.get("name"))+ "!");
    }

}
