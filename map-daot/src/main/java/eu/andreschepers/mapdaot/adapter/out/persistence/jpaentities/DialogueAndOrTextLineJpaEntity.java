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

package eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities;

import eu.andreschepers.mapdaot.adapter.out.persistence.jpaentities.enums.DialogueAndOrTextLineTypeJpa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "dialogue_and_or_text_line")
@Getter
@Setter
public class DialogueAndOrTextLineJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private long version;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private DialogueAndOrTextJpaEntity dialogueAndOrText;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private LineJpaEntity line;

    private String name;

    @Enumerated(EnumType.STRING)
    private DialogueAndOrTextLineTypeJpa type;

    private int lineIndex;
}
