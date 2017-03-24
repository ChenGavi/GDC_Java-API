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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Represents a hashed document
 */
public class HashedDocument implements HashableDocument {
    private final HashableDocument document;
    private final MessageDigest digest;
    private byte[] hash;

    /**
     * Constructs the document with a specified hasher ({@link MessageDigest})
     * @param document
     * @param digest
     */
    public HashedDocument(HashableDocument document, MessageDigest digest) {
        this.document = document;
        this.digest = digest;
    }

    /**
     * Returns an input stream with a value of the hash. Will cache the hash
     * on the first call.
     *
     * @return input stream
     * @throws IOException
     */
    @Override public InputStream getInputStream() throws IOException {
        if (hash == null) {
            InputStream inputStream = document.getInputStream();
            int available;
            while ((available = inputStream.available()) > 0) {
                byte[] b = new byte[available];
                inputStream.read(b);
                digest.update(b);
            }
            hash = digest.digest();
        }
        return new ByteArrayInputStream(hash);
    }
}
