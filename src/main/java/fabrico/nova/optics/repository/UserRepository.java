package fabrico.nova.optics.repository;

import fabrico.nova.optics.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM user_details WHERE chat_id =:chat_id", nativeQuery = true)
    Optional<UserEntity> userWithChatId(@Param("chat_id") Long chatId);

    @Query(value = "SELECT * FROM user_details WHERE number =:number", nativeQuery = true)
    Optional<UserEntity> findUserByNumber(@Param("number") String number);
}
