/*
 * Copyright 2023 André Schepers
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.andreschepers.authservice.application.service;

import com.nimbusds.jwt.JWTClaimsSet;
import eu.andreschepers.authservice.application.port.in.ICreateUserIdBearerTokenUseCase;
import eu.andreschepers.authservice.application.port.out.IRetrieveUserIdFromOAuthSubjectPort;
import eu.andreschepers.authservice.configuration.properties.JWTProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class CreateUserIdBearerTokenService implements ICreateUserIdBearerTokenUseCase {

    private final JWTClaimSetSignerService jwtClaimSetSignerService;
    private final JWTProperties jwtProperties;
    private final IRetrieveUserIdFromOAuthSubjectPort retrieveUserIdFromOAuthSubjectPort;

    @Override
    public String createBearerToken(String incomingSubject) {

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(retrieveUserIdFromOAuthSubjectPort.retrieveUserIdFromOAuthSubject(incomingSubject))
                .issuer(jwtProperties.issuer())
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        return jwtClaimSetSignerService.signClaimSet(claimsSet).serialize();
    }
}
