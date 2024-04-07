package net.stegard.messagingstompwebsocket.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends MongoRepository<ChatEntity,String> {

}
