package ru.vitte.online.helpdesk.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "activemq")
public record ActiveMQProperties(
        @NotEmpty String url,
        @NotEmpty String user,
        @NotEmpty String password) {
}
