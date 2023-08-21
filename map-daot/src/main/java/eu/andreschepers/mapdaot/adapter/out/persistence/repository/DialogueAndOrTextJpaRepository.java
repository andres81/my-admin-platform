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

package eu.andreschepers.mapdaot.adapter.out.persistence.repository;

import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.DialogueAndOrTextJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DialogueAndOrTextJpaRepository extends JpaRepository<DialogueAndOrTextJpaEntity, UUID> {

    boolean existsDialogueAndOrTextJpaEntityByDialogueHash(String shaSum);
    Optional<DialogueAndOrTextJpaEntity> findByDialogueHash(String shaSum);
}
