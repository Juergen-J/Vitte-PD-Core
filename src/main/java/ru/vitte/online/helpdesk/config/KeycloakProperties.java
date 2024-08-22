package ru.vitte.online.helpdesk.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "keycloak.admin")
public record KeycloakProperties(
        @NotEmpty String url,
        @NotEmpty String realm,
        @NotEmpty String clientId,
        @NotEmpty String username,
        @NotEmpty String password) {
}
