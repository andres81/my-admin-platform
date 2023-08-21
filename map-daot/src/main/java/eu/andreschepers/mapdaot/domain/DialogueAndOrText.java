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

import java.util.Collections;
import java.util.List;

@Getter
public class DialogueAndOrText {

    private final List<DialogueAndOrTextLine> lines;
    private final String sha512Sum;

    public DialogueAndOrText(List<DialogueAndOrTextLine> lines) {
        this.lines = Collections.unmodifiableList(lines);
        this.sha512Sum = calculateSha512Sum(lines);
    }

    private String calculateSha512Sum(List<DialogueAndOrTextLine> lines) {
        StringBuilder sb = new StringBuilder();
        lines.forEach(line -> sb.append(line.getSha512Sum()));
        return ShaSumCalculator.calculateSha512Sum(sb.toString());
    }
}
