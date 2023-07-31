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

package eu.andreschepers.springoauth2gateway.adapter.out.client;

import eu.andreschepers.springoauth2gateway.application.port.out.IUserAuthPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MapAuthServiceClient implements IUserAuthPort {

    @Value("${user.authentication.url}")
    private String userAuthenticationUrl;

    @Override
    public Mono<String> userAuthentication(String authorizationHeader) {
        return WebClient.create().get()
                .uri(userAuthenticationUrl)
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .bodyToMono(String.class);
    }
}
