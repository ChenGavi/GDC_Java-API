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

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wraps a PDF Document ({@link PDDocument} from PDFBox)
 */
public class PDFDocument implements MutableDocumentSupportingEmbedding,
                                    DocumentSupportingEmbedding,
                                    HashableDocument {

    private final PDDocument document;

    public PDFDocument(PDDocument document) {
        this.document = document;
    }

    public PDDocument getDocument() {
        return document;
    }

    @Override public void embed(String name, String data) throws IOException {
        document.getDocumentInformation().setCustomMetadataValue(name, data);
    }

    @Override public String retrieveEmbeddedString(String name) {
        return document.getDocumentInformation().getCustomMetadataValue(name);
    }

    @Override public InputStream getInputStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        document.save(bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }
}
