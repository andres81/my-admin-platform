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

package eu.andreschepers.mapdaot.application.service;

import eu.andreschepers.mapdaot.application.port.in.ICreateDialogueAndOrTextUseCase;
import eu.andreschepers.mapdaot.application.port.out.IDBDialogueAndOrTextOutputPort;
import eu.andreschepers.mapdaot.domain.DialogueAndOrText;
import eu.andreschepers.mapdaot.domain.DialogueAndOrTextLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CreateDialogueAndOrTextService implements ICreateDialogueAndOrTextUseCase {

    private final IDBDialogueAndOrTextOutputPort dbOutputPort;

    @Override
    public UUID createDialogueAndOrText(List<DialogueAndOrTextLineRecord> lines) {
        var mappedLines = mapToDomainEntities(lines);
        var domainDialogueAndOrText = new DialogueAndOrText(mappedLines);
        return dbOutputPort.persistDialogueAndOrText(domainDialogueAndOrText);
    }

    private List<DialogueAndOrTextLine> mapToDomainEntities(List<DialogueAndOrTextLineRecord> lines) {
        var mappedLines = new ArrayList<DialogueAndOrTextLine>();
        for (int index = 0 ; index<lines.size() ; ++index) {
            var lineRecord = lines.get(index);
            mappedLines.add(new DialogueAndOrTextLine(index, lineRecord.actor(), lineRecord.lineText()));
        }
        return mappedLines;
    }
}
