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

package eu.andreschepers.mapdaot.adapter.out.persistence.mapper;

import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.DialogueAndOrTextJpaEntity;
import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.DialogueAndOrTextLineJpaEntity;
import eu.andreschepers.mapdaot.domain.DialogueAndOrText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DialogueAndOrTextMapper {

    private final DialogueAndOrTextLineMapper dialogueAndOrTextLineMapper;

    public DialogueAndOrTextJpaEntity mapDialogueAndOrTextToEntity(DialogueAndOrText dialogueAndOrText) {
        var dialogueEntity = new DialogueAndOrTextJpaEntity();
        dialogueEntity.setLines(mapDialogueLines(dialogueEntity, dialogueAndOrText));
        dialogueEntity.setDialogueHash(dialogueAndOrText.getSha512Sum());
        return dialogueEntity;
    }

    public DialogueAndOrText mapEntityToDialogueAndOrText(DialogueAndOrTextJpaEntity entity) {
        var lines = entity.getLines().stream()
            .map(dialogueAndOrTextLineMapper::mapEntityToDialogueAndOrTextLine)
            .toList();
        return new DialogueAndOrText(lines);
    }

    private List<DialogueAndOrTextLineJpaEntity> mapDialogueLines(DialogueAndOrTextJpaEntity dialogueEntity, DialogueAndOrText dialogueAndOrText) {
        return dialogueAndOrText.getLines().stream()
            .map(line -> dialogueAndOrTextLineMapper.mapDialogueLineToEntity(dialogueEntity, line))
            .toList();
    }
}
