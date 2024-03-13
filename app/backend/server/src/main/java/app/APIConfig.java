package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;

//@EnableWebMvc  -- has a side effect of changing JSON LocalDate format, also impacts cors on unmapped paths
@Configuration
public class APIConfig implements WebMvcConfigurer {
    public static final String IP_FORWARDED_FOR = "X-Forwarded-For";

    // path prefixes that will be protected by the authentication filter
    public Set<String> SECURED_PATHS =
            Set.of("");

    // path prefixes that will be unprotected by the authentication filter
    public Set<String> UNSECURED_PATHS =
            Set.of("/authentication/login", "/accounts", "/h2-console", "/favicon.ico");

    // a variable reboot signature can be used as an additional security layer in authentication tokens.
    private static final double REBOOT_CODE = 63.0427; // Math.random();

    // JWT configuration that can be adjusted from application.properties
    @Value("${jwt.issuer:}")
    private String issuer;

    @Value("${jwt.passphrase:}")
    private String passphrase;

    @Value("${jwt.duration-of-validity:}") // default 20 minutes;
    private int tokenDurationOfValidity;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .exposedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, IP_FORWARDED_FOR)
                .allowCredentials(true)
//                .allowedOrigins("*")
        ;
    }

    public String getIssuer() {
        // include a reboot sequence nr in the issuer signature
        //  such that authentication tokens can be revoked after a reboot.
        return String.format("%s-%f", this.issuer, REBOOT_CODE);
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public void setTokenDurationOfValidity(int tokenDurationOfValidity) {
        this.tokenDurationOfValidity = tokenDurationOfValidity;
    }
}