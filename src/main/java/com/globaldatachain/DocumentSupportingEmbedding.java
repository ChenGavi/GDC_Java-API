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

import java.util.Base64;

/**
 * Exposes an interface that allows retrieval of previously
 * embedded data from a document
 */
public interface DocumentSupportingEmbedding {
    /**
     * Retrieves an embedded string property
     *
     * @param name
     * @return
     */
    String retrieveEmbeddedString(String name);

    /**
     * Retrieves an embedded binary property
     *
     * By defaults, treats the underlying string as Base64
     *
     * @param name
     * @return
     */
    default byte[] retrieveEmbeddedBinary(String name) {
        return Base64.getDecoder().decode(retrieveEmbeddedString(name));
    }
}
