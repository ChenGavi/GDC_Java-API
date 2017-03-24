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

import org.springframework.web.client.RestTemplate;

public abstract class AbstractAPICall<T> {

    private final ApiKeyAuthenticationProvider authenticationProvider;
    private final RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public AbstractAPICall(ApiKeyAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        restTemplate.getInterceptors().add(authenticationProvider);
    }

    public abstract T call(Endpoint endpoint);
}
