/*
 * Copyright 2015-2018 Jeeva Kandasamy (jkandasa@gmail.com)
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mycontroller.restclient.philipshue.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 2.0.0
 */

@Data
@ToString
public class Pointsymbol {
    @JsonProperty("1")
    private String p_1;
    @JsonProperty("2")
    private String p_2;
    @JsonProperty("3")
    private String p_3;
    @JsonProperty("4")
    private String p_4;
    @JsonProperty("5")
    private String p_5;
    @JsonProperty("6")
    private String p_6;
    @JsonProperty("7")
    private String p_7;
    @JsonProperty("8")
    private String p_8;
}
