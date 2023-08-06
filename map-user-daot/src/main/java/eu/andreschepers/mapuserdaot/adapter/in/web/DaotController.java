/*
 * Copyright 2023 André Schepers
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.andreschepers.mapuserdaot.adapter.in.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map-user-daot")
public class DaotController {

    @GetMapping
    public Map<String, List<String>> getDaot(HttpServletRequest request) {
        return getHeadersList(request);
    }

    @GetMapping("/test")
    public String getTestString() {
        return "test sub path";
    }

    private HashMap<String, List<String>> getHeadersList(HttpServletRequest request) {
        var map = new HashMap<String, List<String>>();
        var headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            var headerName = headerNames.nextElement();
            map.put(headerName, iteratorToList(request.getHeaders(headerName).asIterator()));
        }
        return map;
    }

    private <T> List<T> iteratorToList(Iterator<T> iterator) {
        List<T> actualList = new ArrayList<>();
        iterator.forEachRemaining(actualList::add);
        return actualList;
    }
}
