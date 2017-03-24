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

import org.springframework.http.ResponseEntity;

/**
 * <a href="https://docs.globaldatachain.com/v1.0/reference#proof">Proof endpoint</a>
 */
public class Proof extends AbstractAPICall<String> {
    private static final String PATH = "/proof";
    private final String identifier;

    public Proof(ApiKeyAuthenticationProvider authenticationProvider, String identifier) {
        super(authenticationProvider);
        this.identifier = identifier;
    }

    @Override public String call(Endpoint endpoint) {
        ResponseEntity<String> responseEntity = getRestTemplate()
                .getForEntity(endpoint.getHost() + "/" + identifier + PATH, String.class);
        return responseEntity.getBody();
    }
}
