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
package org.mycontroller.restclient.influxdb;

import java.util.HashMap;
import java.util.Map;

import org.mycontroller.restclient.core.RestHeader;
import org.mycontroller.restclient.core.RestHttpClient;
import org.mycontroller.restclient.core.RestHttpResponse;
import org.mycontroller.restclient.core.TRUST_HOST_TYPE;
import org.mycontroller.restclient.influxdb.model.Pong;
import org.mycontroller.restclient.influxdb.model.Query;
import org.mycontroller.restclient.influxdb.model.QueryResult;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 2.1.0
 */

public class InfluxDBClient extends RestHttpClient {

    private String username;
    private String password;
    private String database;

    private String baseUrl;
    private RestHeader header;

    private StringBuilder dataBuilder = new StringBuilder();

    public InfluxDBClient(String baseUrl, String database, TRUST_HOST_TYPE trustHostType) {
        this(baseUrl, null, null, database, trustHostType);
    }

    public InfluxDBClient(String baseUrl, String username, String password, String database,
            TRUST_HOST_TYPE trustHostType) {
        super(trustHostType == null ? TRUST_HOST_TYPE.DEFAULT : trustHostType);
        this.username = username;
        this.password = password;
        this.database = database;
        if (baseUrl.endsWith("/")) {
            this.baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        } else {
            this.baseUrl = baseUrl;
        }
        initClient();
    }

    private void initClient() {
        header = RestHeader.getDefault();
        header.addJsonContentType();
        header.addAuthorization(username, password);
    }

    public synchronized String getData(
            String measurement, String tags, String fieldName, String value, Long timestamp) {
        //timestamp format: 1434067467000000000
        dataBuilder.setLength(0);
        dataBuilder.append(measurement);
        if (tags != null && tags.length() > 0) {
            dataBuilder.append(",").append(tags.replaceAll(" ", "_"));
        }
        dataBuilder
                .append(" ").append(fieldName.replaceAll(" ", "_")).append("=").append(value)
                .append(" ").append(timestamp).append("000000");
        return dataBuilder.toString();
    }

    public Pong ping() {
        RestHttpResponse response = doGet(baseUrl + "/ping", null);
        if (response.getResponseCode().equals(STATUS_CODE.NO_CONTENT.getCode())) {
            return Pong.builder()
                    .version(getHeader(response, "X-Influxdb-Version"))
                    .reachable(true)
                    .build();
        } else {
            return Pong.builder()
                    .reachable(false)
                    .statusCode(response.getResponseCode())
                    .errorMessage(response.getEntity())
                    .build();
        }
    }

    public QueryResult query(Query query) {
        query.setDb(database);
        if (query.getEpoch() == null) {
            query.setEpoch("ms");
        }
        RestHttpResponse response = doGet(baseUrl + "/query", query.getQueryMap(), header, STATUS_CODE.OK.getCode());
        return (QueryResult) readValue(response.getEntity(), simpleResolver().get(QueryResult.class));
    }

    public QueryResult queryManagement(Query query) {
        query.setDb(database);
        RestHttpResponse response = doPost(baseUrl + "/query", query.getQueryMap(), header, STATUS_CODE.OK.getCode());
        return (QueryResult) readValue(response.getEntity(), simpleResolver().get(QueryResult.class));
    }

    public void write(String data) {
        Map<String, Object> queryParms = new HashMap<String, Object>();
        queryParms.put("db", database);
        doPost(baseUrl + "/write", queryParms, header, data, STATUS_CODE.NO_CONTENT.getCode());
    }

}
