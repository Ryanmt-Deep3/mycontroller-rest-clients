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
package org.mycontroller.restclient.pushbullet.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 0.0.3
 */
@Getter
@ToString
public class Device {
    private Boolean active;
    private String iden;
    private Float created;
    private Float modified;
    private String icon;
    private String nickname;

    @SerializedName("generated_nickname")
    private Boolean generatedNickname;

    private String manufacturer;
    private String model;

    @SerializedName("app_version")
    private Integer appVersion;

    private String fingerprint;

    @SerializedName("key_fingerprint")
    private String keyFingerprint;

    @SerializedName("push_token")
    private String pushToken;

    @SerializedName("has_sms")
    private String hasSms;

}