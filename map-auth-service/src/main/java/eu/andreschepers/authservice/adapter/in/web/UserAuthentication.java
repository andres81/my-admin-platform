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

package eu.andreschepers.authservice.adapter.in.web;

import eu.andreschepers.authservice.application.port.in.ICreateUserIdBearerTokenUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthentication {

    private final ICreateUserIdBearerTokenUseCase createUserIdTokenUseCase;

    @GetMapping("/authenticate")
    public String authenticateUser(@AuthenticationPrincipal Jwt userJwt) {
        return createUserIdTokenUseCase.createBearerToken(userJwt.getSubject());
    }
}
