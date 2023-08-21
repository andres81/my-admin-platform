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

import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.LineJpaEntity;
import eu.andreschepers.mapdaot.domain.DialogueAndOrTextLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LineMapper {

    private final LineWordMapper lineWordMapper;

    public LineJpaEntity mapLine(DialogueAndOrTextLine dialogueAndOrTextLine) {
        LineJpaEntity lineJpaEntity;
        lineJpaEntity = new LineJpaEntity();
        lineJpaEntity.setLine(dialogueAndOrTextLine.getLine());
        lineJpaEntity.setLineShaSum(dialogueAndOrTextLine.getSha512Sum());
        lineJpaEntity.setLineWords(lineWordMapper.mapWords(dialogueAndOrTextLine));
        return lineJpaEntity;
    }
}
