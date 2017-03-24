/**
 * Copyright 2017, Global Data Chain, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.globaldatachain;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://docs.globaldatachain.com/v1.0/reference#sink-1">Sink endpoint</a>
 */
public class Sink extends AbstractAPICall<SinkResult> {
    private static final String PATH = "/sink";

    private final Map<String, HashableDocument> documents = new HashMap<>();

    public Sink(ApiKeyAuthenticationProvider authenticationProvider) {
        super(authenticationProvider);
        RestTemplate restTemplate = getRestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
    }

    public Map<String, HashableDocument> getDocuments() {
        return documents;
    }

    @Override public SinkResult call(Endpoint endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

        documents.forEach((key, file) -> {
            try {
                HttpHeaders fileHeader = new HttpHeaders();
                fileHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                InputStream inputStream = file.getInputStream();
                int available;
                while ((available = inputStream.available()) > 0) {
                    byte[] b = new byte[available];
                    inputStream.read(b);
                    bos.write(b);
                }

                fileHeader.setContentDispositionFormData(key, key);

                HttpEntity<ByteArrayResource> filePart = new HttpEntity<>(new ByteArrayResource(bos.toByteArray()), fileHeader);
                multipartRequest.add(key, filePart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, headers);
        return getRestTemplate().postForObject(endpoint.getHost() + PATH, requestEntity, SinkResult.class);
    }
}
