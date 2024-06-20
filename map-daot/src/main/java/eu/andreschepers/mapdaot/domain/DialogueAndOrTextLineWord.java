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

package eu.andreschepers.mapdaot.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DialogueAndOrTextLineWord {

    private final UUID id;
    private final int index;
    private final String word;
    private final String sha512Sum;

    public DialogueAndOrTextLineWord(UUID id,
                                     int index,
                                     String word) {
        this.id = id;
        this.index = index;
        this.word = word;
        this.sha512Sum = ShaSumCalculator.calculateSha512Sum(word);
    }

    @Override
    public String toString() {
        return word;
    }
}
