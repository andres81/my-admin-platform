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

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.andreschepers.mapdaot.adapter.out.persistence.DBDialogueAndOrTextOutputAdapter;
import eu.andreschepers.mapdaot.application.port.in.ICreateDialogueAndOrTextUseCase;
import eu.andreschepers.mapdaot.domain.DialogueAndOrText;
import eu.andreschepers.mapdaot.domain.DialogueAndOrTextLine;
import eu.andreschepers.mapdaot.domain.DialogueAndOrTextLineWord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CreateDialogueAndOrTextServiceTest {

    private static final String DAOT_FILE = "/daot-tests/test-daots.json";

    @Mock
    private DBDialogueAndOrTextOutputAdapter dbOutputPort;

    @InjectMocks
    private CreateDialogueAndOrTextService sut;

    @Captor
    ArgumentCaptor<DialogueAndOrText> dialogueTextCaptor;

    @Test
    void testCreationOfDialogueAndOrText() throws IOException {
        when(dbOutputPort.persistDialogueAndOrText(any())).thenReturn(UUID.randomUUID());
        var testDaots = getTestDaots();
        for (JsonNode testDaot : testDaots) {

            sut.createDialogueAndOrText(createDialogueTextList(testDaot));
            verify(dbOutputPort).persistDialogueAndOrText(dialogueTextCaptor.capture());
            var dialogueText = dialogueTextCaptor.getValue();

            assertEquals("ce97e5981473f24e37cb629388281a17aa93eb89e10be2e436a0d07ca1023ef27a70fcfb635c1858c99e40523e3e18cfba8e4e3d2a7260abbc7b15b6581f81d2", dialogueText.getSha512Sum());
            var lines = dialogueText.getLines();
            assertLines(testDaot, lines);
        }
    }

    private void assertLines(JsonNode testDaot, List<DialogueAndOrTextLine> dialogueAndOrTextLines) {
        var testDaotLines = testDaot.at(JsonPointer.compile("/expected/lines"));
        assertEquals(testDaotLines.size(), dialogueAndOrTextLines.size());
        for (int index = 0; index < testDaotLines.size(); ++index) {
            var testDaotLine = testDaotLines.get(index);
            var createdLine = dialogueAndOrTextLines.get(index);
            assertEquals(testDaotLine.get("actor").textValue(), createdLine.getPersonName());
            assertEquals(testDaotLine.get("line-text").textValue(), createdLine.getLine());
            assertEquals(testDaotLine.get("sha512sum").textValue(), createdLine.getSha512Sum());
            log.info("words of index: [{}], words: [{}]", index, createdLine.getWords());

            var lineWords = testDaot.at(JsonPointer.compile("/expected/line-words")).get(index).elements();
            Iterable<JsonNode> iterable = () -> lineWords;
            List<String> actualList = StreamSupport
                    .stream(iterable.spliterator(), false)
                    .map(JsonNode::textValue).toList();

            List<String> expectedList = createdLine.getWords()
                    .stream()
                    .map(DialogueAndOrTextLineWord::toString)
                    .toList();

            assertEquals(actualList, expectedList);
        }
    }

    private JsonNode getTestDaots() throws IOException {
        var dialoguesJsonInputstream = this.getClass().getResourceAsStream(DAOT_FILE);
        return new ObjectMapper().reader().readTree(dialoguesJsonInputstream);
    }

    private List<ICreateDialogueAndOrTextUseCase.DialogueAndOrTextLineRecord> createDialogueTextList(JsonNode testDaot) {

        var input = testDaot.get("input");

        var linesArrayNode = input.get("lines");
        var recordList = new ArrayList<ICreateDialogueAndOrTextUseCase.DialogueAndOrTextLineRecord>();
        linesArrayNode.forEach(line -> {
            var record = new ICreateDialogueAndOrTextUseCase.DialogueAndOrTextLineRecord(line.get("actor").textValue(), line.get("line-text").textValue());
            recordList.add(record);
        });

        return recordList;
    }
}
