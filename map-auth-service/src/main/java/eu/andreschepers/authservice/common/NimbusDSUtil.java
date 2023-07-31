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

package eu.andreschepers.authservice.common;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.X509CertUtils;
import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;

@Component
public class NimbusDSUtil {

    public RSAKey rsaKeyFromX509Certificate(String encodedCert) throws JOSEException {

        // Parse X.509 certificate
        X509Certificate cert = X509CertUtils.parse(encodedCert);

        // Retrieve public key as RSA JWK

        return RSAKey.parse(cert);
    }
}
