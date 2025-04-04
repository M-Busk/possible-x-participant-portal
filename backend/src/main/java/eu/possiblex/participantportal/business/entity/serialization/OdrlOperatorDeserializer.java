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

package eu.possiblex.participantportal.business.entity.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import eu.possiblex.participantportal.business.entity.edc.policy.OdrlOperator;

import java.io.IOException;

public class OdrlOperatorDeserializer extends StdDeserializer<OdrlOperator> {

    public OdrlOperatorDeserializer() {

        this(null);
    }

    public OdrlOperatorDeserializer(Class<?> vc) {

        super(vc);
    }

    @Override
    public OdrlOperator deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.get("@id") != null) {
            return OdrlOperator.forValue(node.get("@id").textValue());
        }
        return null;
    }
}
