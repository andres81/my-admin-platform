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

package eu.andreschepers.authservice.adapter.out.persistence.repository;

import eu.andreschepers.authservice.adapter.out.persistence.jpaentities.OAuth2SubjectToUserIdMappingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OAuth2SubjectToUserIdMappingJpaEntityRepository extends JpaRepository<OAuth2SubjectToUserIdMappingJpaEntity, UUID> {

    Optional<OAuth2SubjectToUserIdMappingJpaEntity> findByOauth2JwtSubject(String oAuth2Subject);
}
