package fabrico.nova.optics.repository;

import fabrico.nova.optics.model.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    @Query(value = "SELECT * FROM chat WHERE chat_id =:chat_id", nativeQuery = true)
    Optional<ChatEntity> findChatById(@Param("chat_id") Long chatId);

    @Query(value = "SELECT * FROM chat WHERE chat_name =:chat_name", nativeQuery = true)
    Optional<ChatEntity> findChatByName(@Param("chat_name") String chatName);

}
