package eu.andreschepers.authservice.application.service;

import eu.andreschepers.authservice.application.port.in.IGetJWKMapUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetJWKMapService implements IGetJWKMapUseCase {

    private final JWTClaimSetSignerService signer;

    @Override
    public String getJWKMap() {
        return signer.getJwkMapString();
    }
}
