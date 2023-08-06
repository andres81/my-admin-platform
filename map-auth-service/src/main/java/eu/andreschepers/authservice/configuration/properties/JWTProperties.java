package eu.andreschepers.authservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JWTProperties(String issuer, String jwkPrivateKeyPKCS1, String jwkPublicKeyX509Cert) {}
