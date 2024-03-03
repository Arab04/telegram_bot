package fabrico.nova.optics.model;

import fabrico.nova.optics.model.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_details")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "number")
    private String number;

    @Column(name = "registered_date")
    private String registeredDate;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

}
