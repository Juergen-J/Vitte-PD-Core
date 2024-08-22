package ru.vitte.online.helpdesk.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "gpt")
public record AIProperties(
        @NotEmpty String url,
        @NotEmpty String apiKey,
        @NotEmpty String model
) {
}
