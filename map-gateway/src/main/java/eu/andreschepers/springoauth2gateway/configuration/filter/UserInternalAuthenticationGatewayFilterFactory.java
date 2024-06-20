/*
 * Copyright 2023 André Schepers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.andreschepers.springoauth2gateway.configuration.filter;

import eu.andreschepers.springoauth2gateway.application.port.out.IUserAuthPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserInternalAuthenticationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    private final IUserAuthPort userAuthPort;

    public UserInternalAuthenticationGatewayFilterFactory(IUserAuthPort userAuthPort) {
        super(Object.class);
        this.userAuthPort = userAuthPort;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            return userAuthPort.userAuthentication(getAuthorizationHeader(request))
                .map(
                    bearerToken -> {
                        exchange.getRequest()
                            .mutate()
                            .headers(h -> h.setBearerAuth(bearerToken))
                            .build();
                        return exchange;
                    }
                ).flatMap(chain::filter);
        };
    }

    private String getAuthorizationHeader(ServerHttpRequest request) {
        var headers = request.getHeaders();
        var authHeaders = headers.get(HttpHeaders.AUTHORIZATION);
        if (authHeaders == null || authHeaders.isEmpty()) {
            return null;
        }
        return authHeaders.get(0);
    }
}
