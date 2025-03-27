/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *  Copyright 2024-2025 Dataport. All rights reserved. Extended as part of the POSSIBLE project.
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
 *
 * Modifications:
 * - Dataport (part of the POSSIBLE project) - 14 August, 2024 - Adjust package names and imports
 */

package eu.possiblex.participantportal.business.entity.edc.asset.awss3extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.possiblex.participantportal.business.entity.edc.asset.DataAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class AWSS3DataAddress extends DataAddress {

    private static final String TYPE = "AmazonS3";

    private String bucketName;

    private String region;

    private String accessKeyId;
    
    private String secretAccessKey;

    @JsonProperty("type")
    public String getDataType() {

        return TYPE;
    }
}
