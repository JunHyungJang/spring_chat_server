package net.stegard.messagingstompwebsocket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

@Setter
@Getter
@NoArgsConstructor
public class HelloMessage {
    private String name;
    private String test;

    public HelloMessage(String name,String test) {
        this.name = name;
        this.test = test;
    }

    // 필요한 경우 toString(), equals(), hashCode() 메소드를 추가합니다.
}