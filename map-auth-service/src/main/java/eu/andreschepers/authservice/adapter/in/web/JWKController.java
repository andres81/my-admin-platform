package eu.andreschepers.authservice.adapter.in.web;

import eu.andreschepers.authservice.application.port.in.IGetJWKMapUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JWKController {

    private final IGetJWKMapUseCase getJWKMapUseCase;

    @GetMapping("/jwk")
    public String getJwk() {
        return getJWKMapUseCase.getJWKMap();
    }
}
