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

package eu.possiblex.participantportal.application.entity.policies;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Schema(description = "Policy that restricts the contractual booking to specific participants", example = """
{
  "@type": "ParticipantRestrictionPolicy",
  "allowedParticipants": ["did:web:example.com:participant:someorgltd"]
}
""")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ParticipantRestrictionPolicy extends EnforcementPolicy {
    public static final String EDC_OPERAND = "did";

    @Schema(description = "List of allowed participants", example = "[\"did:web:example.com:participant:someorgltd\"]")
    private List<String> allowedParticipants;
}
