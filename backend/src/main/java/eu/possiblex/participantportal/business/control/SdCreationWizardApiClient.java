/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.possiblex.participantportal.business.control;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.Map;

public interface SdCreationWizardApiClient {
    @GetExchange("/getAvailableShapesCategorized")
    Map<String, List<String>> getAvailableShapesCategorized(@RequestParam(name = "ecosystem") String ecosystem);

    @GetExchange("/getJSON")
    String getJSON(@RequestParam(name = "ecosystem") String ecosystem, @RequestParam(name = "name") String name);

    // not implemented: GET "/getAvailableShapes"
    // not implemented: POST "/convertFile"
    // not implemented: GET "/getSearchQuery/{ecoSystem}/{query}"
}
