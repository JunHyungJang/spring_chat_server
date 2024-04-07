package net.stegard.messagingstompwebsocket.jpa;


import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
public class ChatEntity {
    @Id
    private String id;
    private String name;
    private String roomId;
    private String content;
}
