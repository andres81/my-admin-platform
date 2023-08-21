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

import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.DialogueAndOrTextJpaEntity;
import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.DialogueAndOrTextLineJpaEntity;
import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.LineJpaEntity;
import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.enums.DialogueAndOrTextLineTypeJpa;
import eu.andreschepers.mapdaot.adapter.out.persistence.repository.DialogueAndOrTextJpaRepository;
import eu.andreschepers.mapdaot.adapter.out.persistence.repository.DialogueAndOrTextLineJpaRepository;
import eu.andreschepers.mapdaot.adapter.out.persistence.repository.LineJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dbtest")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DBDialogueAndOrTextOutputAdapterTest {

    @Autowired
    private DialogueAndOrTextJpaRepository daotRepository;
    @Autowired
    private DialogueAndOrTextLineJpaRepository daotLineRepository;
    @Autowired
    private LineJpaRepository lineRepository;

    @Test
    void persistDialogueAndOrText() {
        DialogueAndOrTextJpaEntity daotEntity = new DialogueAndOrTextJpaEntity();
        daotEntity.setDialogueHash(" hash ");
        daotEntity.setLines(createLines(daotEntity));
        daotRepository.save(daotEntity);
    }

    private List<DialogueAndOrTextLineJpaEntity> createLines(DialogueAndOrTextJpaEntity daotEntity) {
        var lines = new ArrayList<DialogueAndOrTextLineJpaEntity>();
        var daotLine = new DialogueAndOrTextLineJpaEntity();
        daotLine.setLineIndex(0);
        daotLine.setDialogueAndOrText(daotEntity);
        daotLine.setType(DialogueAndOrTextLineTypeJpa.DIALOGUE);
        daotLine.setLine(createLine());
        lines.add(daotLine);
        return lines;
    }

    private LineJpaEntity createLine() {
        LineJpaEntity line = new LineJpaEntity();
        return line;
//        return lineRepository.save(line);
    }
}
