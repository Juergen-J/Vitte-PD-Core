package ru.vitte.online.helpdesk.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloakAdmin() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.url())
                .realm(keycloakProperties.realm())
                .clientId(keycloakProperties.clientId())
                .username(keycloakProperties.username())
                .password(keycloakProperties.password())
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
