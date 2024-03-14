package net.stegard.messagingstompwebsocket.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
    @Service
    public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @KafkaListener(topics = "chatting", groupId = "chat-group")
//        @SendTo("/sub/chatroom/{roomId}")
    public void listen(String kafkaMessage) {
        log.info("Listender mseesge ==>" + kafkaMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열을 Java 객체로 변환
            Object jsonObject = objectMapper.readValue(kafkaMessage, Object.class);

            // JSON 객체에서 roomId 값을 추출
            String roomId = null;
            if (jsonObject instanceof Map) {
                Map<String, Object> jsonMap = (Map<String, Object>) jsonObject;
                roomId = (String) jsonMap.get("roomId");
            }

            // roomId가 null이 아니면 메시지 전송
            if (roomId != null) {
                log.info("Room ID: " + roomId);
                simpMessagingTemplate.convertAndSend("/sub/chatroom/" + roomId, kafkaMessage);
            } else {
                log.error("Room ID not found in the JSON message.");
            }
        } catch (JsonProcessingException e) {
            // JSON 파싱 오류 처리
            log.info("error");
            e.printStackTrace();
        }
    }
}

