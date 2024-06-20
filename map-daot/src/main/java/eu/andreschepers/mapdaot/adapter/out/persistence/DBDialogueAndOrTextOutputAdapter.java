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

package eu.andreschepers.mapdaot.adapter.out.persistence;

import eu.andreschepers.mapdaot.adapter.out.persistence.mapper.DialogueAndOrTextMapper;
import eu.andreschepers.mapdaot.adapter.out.persistence.repository.DialogueAndOrTextJpaRepository;
import eu.andreschepers.mapdaot.application.port.out.IDBDialogueAndOrTextOutputPort;
import eu.andreschepers.mapdaot.domain.DialogueAndOrText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DBDialogueAndOrTextOutputAdapter implements IDBDialogueAndOrTextOutputPort {

    private final DialogueAndOrTextJpaRepository daotRepository;
    private final DialogueAndOrTextMapper dialogueAndOrTextMapper;

    @Override
    @Transactional
    public UUID persistDialogueAndOrText(DialogueAndOrText dialogueAndOrText) {

        if (daotRepository.existsDialogueAndOrTextJpaEntityByDialogueHash(dialogueAndOrText.getSha512Sum())) {
            return daotRepository.findByDialogueHash(dialogueAndOrText.getSha512Sum()).get().getId();
        }

        var dialogueEntity = dialogueAndOrTextMapper.mapDialogueAndOrTextToEntity(dialogueAndOrText);

        return daotRepository.save(dialogueEntity).getId();
    }

    @Override
    @Transactional
    public DialogueAndOrText readDialogueAndOrTextByHashSum(String hashSum) {
        return daotRepository.findByDialogueHash(hashSum)
            .map(dialogueAndOrTextMapper::mapEntityToDialogueAndOrText)
            .orElse(null);
    }

    @Override
    @Transactional
    public DialogueAndOrText readDialogueAndOrTextById(UUID id) {
        return daotRepository.findById(id)
            .map(dialogueAndOrTextMapper::mapEntityToDialogueAndOrText)
            .orElse(null);
    }
}
