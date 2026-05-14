package defiut.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration for the application
 * Replaces local @CrossOrigin annotations on endpoints
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Liste blanche des origines autorisées
    private static final String[] ALLOWED_ORIGINS = {
        "https://defiut.fr",
        "http://localhost:3000",   // frontend dev
        "http://localhost:8080"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600); // cache preflight 1h
    }
}
