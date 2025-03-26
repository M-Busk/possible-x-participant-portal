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

package eu.possiblex.participantportal.business.entity.edc.policy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import eu.possiblex.participantportal.business.entity.common.JsonLdConstants;

import java.util.HashMap;
import java.util.Map;

public enum OdrlAction {
    USE, TRANSFER;

    private static final Map<String, OdrlAction> actionMap = new HashMap<>();

    static {
        actionMap.put("http://www.w3.org/ns/odrl/2/use", USE);
        actionMap.put( JsonLdConstants.ODRL_PREFIX + "use", USE);
        actionMap.put("http://www.w3.org/ns/odrl/2/transfer", TRANSFER);
        actionMap.put(JsonLdConstants.ODRL_PREFIX + "transfer", TRANSFER);
    }

    @JsonCreator
    public static OdrlAction forValue(String value) {

        return actionMap.get(value);
    }

    @JsonValue
    public String toValue() {

        for (Map.Entry<String, OdrlAction> entry : actionMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }
}
