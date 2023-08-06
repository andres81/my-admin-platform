package eu.andreschepers.authservice.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import eu.andreschepers.authservice.configuration.properties.JWTProperties;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTClaimSetSignerService {

    private String rsaKeyId;
    private JWSSigner signer;
    @Getter
    private String jwkMapString;

    private final JWTProperties jwtClaimSetSignerProperties;

    @PostConstruct
    public void postConstruct() {
        try {
            var jwk = JWK.parseFromPEMEncodedObjects(jwtClaimSetSignerProperties.jwkPrivateKeyPKCS1());
            signer = new RSASSASigner(jwk.toRSAKey());
            var key = getRSAKey(jwtClaimSetSignerProperties.jwkPublicKeyX509Cert());
            rsaKeyId = key.getKeyID();
            jwkMapString = publicRsaKayToJsonJWKString(key);
        } catch(Exception ex) {
            log.error("Couldn't initialize Nimbus keys situation");
            throw new IllegalStateException("Couldn't initialize Nimbus keys situation");
        }
    }

    public SignedJWT signClaimSet(JWTClaimsSet claimsSet) {

        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS512)
                    .keyID(rsaKeyId)
                    .build(),
            claimsSet);

        // Compute the RSA signature
        try {
            signedJWT.sign(signer);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return signedJWT;
    }

    private RSAKey getRSAKey(String x509CertString) throws JOSEException {
        X509Certificate cert = X509CertUtils.parse(x509CertString);
        return RSAKey.parse(cert);
    }

    private String publicRsaKayToJsonJWKString(RSAKey key) throws JsonProcessingException {
        var map = Map.of("keys", List.of(key.toPublicJWK().toJSONObject()));
        return new ObjectMapper().writeValueAsString(map);
    }
}
