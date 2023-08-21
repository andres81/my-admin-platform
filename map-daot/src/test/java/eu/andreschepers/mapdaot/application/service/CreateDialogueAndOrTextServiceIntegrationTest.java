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

import eu.andreschepers.mapdaot.adapter.out.persistence.repository.DialogueAndOrTextJpaRepository;
import eu.andreschepers.mapdaot.application.port.in.ICreateDialogueAndOrTextUseCase;
import eu.andreschepers.mapdaot.application.port.in.IReadDialogueAndOrTextUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
@ActiveProfiles("integrationtest")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateDialogueAndOrTextServiceIntegrationTest {

    private static final Map<String,String> DIALOGUE_ONE = Map.of(
            "Jan", "Hey Joop, wat een dikke bmw!",
            "Joop", "Ja, daar heb ik wél het geld voor Jan..."
    );
    private static final Map<String,String> DIALOGUE_TWO = Map.of(

    );

    @Autowired
    private DialogueAndOrTextJpaRepository daotRepository;

    @Autowired
    private IReadDialogueAndOrTextUseCase readDialogueAndOrTextUseCase;

    @Autowired
    private CreateDialogueAndOrTextService sut;

    @Test
    void testCreationAndPersistence() {
        sut.createDialogueAndOrText(createDialogueAndOrTextLineRecords());
        var daots = daotRepository.findAll();
        log.info("Daots: {}", daots);
        var readDaot = readDialogueAndOrTextUseCase.readDialogueAndOrTextByHashSum(daots.get(0).getDialogueHash());
        log.info("Daot: [{}]", readDaot);
    }

    private List<ICreateDialogueAndOrTextUseCase.DialogueAndOrTextLineRecord> createDialogueAndOrTextLineRecords() {
        return DIALOGUE_ONE.entrySet().stream()
            .map(entry -> new ICreateDialogueAndOrTextUseCase.DialogueAndOrTextLineRecord(entry.getKey(), entry.getValue()))
            .toList();
    }
}
