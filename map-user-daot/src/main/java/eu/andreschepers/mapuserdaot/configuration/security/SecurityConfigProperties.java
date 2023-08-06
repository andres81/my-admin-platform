package eu.andreschepers.mapuserdaot.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.config")
public record SecurityConfigProperties(String jwkUrl) {}
