package com.candescent.PartyService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * OpenAPI/Swagger configuration.
 * 
 * Swagger UI is:
 * - Enabled in non-production environments (default, dev, local)
 * - Disabled in production via springdoc.swagger-ui.enabled=false
 */
@Configuration
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Bean
    public OpenAPI partyServiceOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(getServers());
    }

    /**
     * Groups all product-related APIs.
     */
    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/v1/products/**")
                .build();
    }

    /**
     * Groups all party-related APIs.
     */
    @Bean
    public GroupedOpenApi partyApi() {
        return GroupedOpenApi.builder()
                .group("party")
                .pathsToMatch("/v1/party/**")
                .build();
    }

    /**
     * Groups data loader APIs (only for non-prod).
     */
    @Bean
    @Profile("!prod")
    public GroupedOpenApi dataLoaderApi() {
        return GroupedOpenApi.builder()
                .group("data-loader")
                .pathsToMatch("/v1/data-loader/**")
                .build();
    }

    private List<Server> getServers() {
        if ("prod".equals(activeProfile)) {
            return List.of(
                    new Server()
                            .url("https://api.candescent.com")
                            .description("Production Server")
            );
        }
        return List.of(
                new Server()
                        .url("http://localhost:" + serverPort)
                        .description("Local Development Server"),
                new Server()
                        .url("https://dev-api.candescent.com")
                        .description("Development Server")
        );
    }

    private Info apiInfo() {
        return new Info()
                .title("Party Service API")
                .description(getApiDescription())
                .version("1.0.0")
                .contact(new Contact()
                        .name("Candescent")
                        .email("support@candescent.com")
                        .url("https://www.candescent.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"));
    }

    private String getApiDescription() {
        String baseDescription = "RESTful API for managing party (customer) information";
        if (!"prod".equals(activeProfile)) {
            return baseDescription + "\n\n**Environment:** " + activeProfile + 
                   "\n\n⚠️ **Note:** This is a non-production environment. " +
                   "API execution via Swagger UI is enabled for testing purposes.";
        }
        return baseDescription;
    }
}
