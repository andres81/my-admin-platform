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

import eu.andreschepers.mapdaot.domain.enums.DialogueAndOrTextLineType;
import lombok.Getter;

import java.util.*;

@Getter
public class DialogueAndOrTextLine {

    private final int index;
    private final String personName;
    private final String line;
    private final String sha512Sum;
    private final List<DialogueAndOrTextLineWord> words;
    private final DialogueAndOrTextLineType dialogueAndOrTextLineType;

    public DialogueAndOrTextLine(int index,
                                 String personName,
                                 String line) {

        this(index, personName, line, null);
    }

    public DialogueAndOrTextLine(int index,
                                 String personName,
                                 String line,
                                 List<String> words) {
        this.index = index;
        var isPersonNameBlank = ("".equals(personName) || null == personName);
        this.dialogueAndOrTextLineType = isPersonNameBlank ? DialogueAndOrTextLineType.TEXT : DialogueAndOrTextLineType.DIALOGUE;
        this.personName = personName;
        this.line = line;
        this.sha512Sum = ShaSumCalculator.calculateSha512Sum(line);
        this.words = Collections.unmodifiableList(checkAndMapWordsAsStringToPojo(line, words));
    }

    private List<DialogueAndOrTextLineWord> checkAndMapWordsAsStringToPojo(String line, List<String> words) {

        if (words == null) {
            words = extractWordsFromLineAsStrings(line);
        } else if (!extractWordsFromLineAsStrings(line).equals(words)) {
            throw new IllegalStateException("");
        }

        List<DialogueAndOrTextLineWord> wordList = new ArrayList<>();
        for (int i=0;i<words.size();++i) {
            wordList.add(new DialogueAndOrTextLineWord(
                    UUID.randomUUID(),
                    i,
                    words.get(i)
            ));
        }

        return wordList;
    }

    private List<String> extractWordsFromLineAsStrings(String line) {
        if (line == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(line.split("\\P{L}+"));
    }
}
