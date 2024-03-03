package fabrico.nova.optics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter
@Setter
@EnableScheduling
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String name;
    private String token;
    private Long ownerId;
}
