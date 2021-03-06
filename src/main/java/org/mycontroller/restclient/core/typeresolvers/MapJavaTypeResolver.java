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
package org.mycontroller.restclient.core.typeresolvers;

import java.util.Map;

import org.mycontroller.restclient.core.RestObjectMapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 2.0.0
 */

public class MapJavaTypeResolver {

    private final ObjectMapper objectMapper;

    public MapJavaTypeResolver() {
        this.objectMapper = new RestObjectMapper();
    }

    /**
     * Map with Generic Key and Value, i.e.: Map<String, TaggedBucketPoint>
     */
    public JavaType get(@SuppressWarnings("rawtypes") Class<? extends Map> mapClazz, Class<?> mapClazzKey,
            Class<?> mapClazzValue) {
        JavaType mapClazzKeyType = objectMapper.getTypeFactory().constructType(mapClazzKey);
        JavaType mapClazzValueType = objectMapper.getTypeFactory().constructType(mapClazzValue);

        return objectMapper.getTypeFactory().constructMapType(mapClazz, mapClazzKeyType, mapClazzValueType);
    }

    /**
     * Map with Generic Key and Genric Value, i.e.: Map<String, List<TaggedBucketPoint>>
     */
    public JavaType get(@SuppressWarnings("rawtypes") Class<? extends Map> mapClazz, Class<?> mapClazzKey,
            Class<?> mapClazzValue, Class<?> mapClazzParametrizedValue) {
        JavaType mapClazzKeyType = objectMapper.getTypeFactory().constructType(mapClazzKey);
        JavaType parametrizedClazzType = objectMapper.getTypeFactory().constructParametrizedType(mapClazzValue,
                mapClazzValue, mapClazzParametrizedValue);

        return objectMapper.getTypeFactory().constructMapType(mapClazz, mapClazzKeyType, parametrizedClazzType);
    }
}