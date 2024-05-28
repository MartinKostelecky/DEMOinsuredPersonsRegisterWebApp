package cz.martinkostelecky.insuredpersonsregisterwebapp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {

    /**
     * Secret key used for issuing TWT
     */
    private String secretKey;

    private Duration tokenDuration;
}
