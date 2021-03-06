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
package org.mycontroller.restclient.pushbullet;

import java.text.MessageFormat;
import java.util.List;

import org.mycontroller.restclient.core.RestHeader;
import org.mycontroller.restclient.core.RestHttpClient;
import org.mycontroller.restclient.core.RestHttpResponse;
import org.mycontroller.restclient.core.TRUST_HOST_TYPE;
import org.mycontroller.restclient.pushbullet.model.Device;
import org.mycontroller.restclient.pushbullet.model.Devices;
import org.mycontroller.restclient.pushbullet.model.Push;
import org.mycontroller.restclient.pushbullet.model.PushResponse;
import org.mycontroller.restclient.pushbullet.model.User;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 2.1.0
 */

public class PushbulletClient extends RestHttpClient {
    public static final String URL = "https://api.pushbullet.com";
    public static final String VERSION = "v2";

    private String authToken;

    private String baseUrl;
    private RestHeader header;

    public PushbulletClient(String authToken, TRUST_HOST_TYPE trustHostType) {
        super(trustHostType == null ? TRUST_HOST_TYPE.DEFAULT : trustHostType);
        this.authToken = authToken;
        initClient();
    }

    private void initClient() {
        baseUrl = MessageFormat.format("{0}/{1}", URL, VERSION);
        header = RestHeader.getDefault();
        header.addJsonContentType();
        header.put("Access-Token", authToken);
    }

    public PushResponse createPush(Push push) {
        RestHttpResponse response = doPost(baseUrl + "/pushes", header, toJsonString(push), STATUS_CODE.OK.getCode());
        return (PushResponse) readValue(response.getEntity(), simpleResolver().get(PushResponse.class));
    }

    public User currentUser() {
        RestHttpResponse response = doGet(baseUrl + "/users/me", header, STATUS_CODE.OK.getCode());
        return (User) readValue(response.getEntity(), simpleResolver().get(User.class));
    }

    public List<Device> devices() {
        RestHttpResponse response = doGet(baseUrl + "/devices", header, STATUS_CODE.OK.getCode());
        return ((Devices) readValue(response.getEntity(), simpleResolver().get(Devices.class))).getDevices();
    }

}
