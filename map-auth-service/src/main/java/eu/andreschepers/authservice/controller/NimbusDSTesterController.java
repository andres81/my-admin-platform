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

package eu.andreschepers.authservice.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import eu.andreschepers.authservice.common.NimbusDSUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NimbusDSTesterController {

    private final NimbusDSUtil nimbusDSUtil;

    @GetMapping("/jwk")
    public Map<String,Object> testJwkEndpoint() throws JOSEException, IOException {

        var is = this.getClass().getResourceAsStream("/certificate.crt");
        String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        var key = nimbusDSUtil.rsaKeyFromX509Certificate(text);

        var publicJwk = key.toPublicJWK();

        return Map.of("keys", List.of(publicJwk.toJSONObject()));
    }

    @GetMapping("/test")
    public String testRemoteJWKRetrieval() throws IOException, ParseException {
        JWKSet publicKeys = JWKSet.load(new URL("http://localhost:8080/api/jwk"));

        return publicKeys.getKeys().get(0).getKeyID();

    }
}
