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

import java.util.Map;

/**
 * Result of calling {@link Sink}
 */
public class SinkResult {
    private boolean success;
    private Map<String, String> identifiers;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Map<String, String> identifiers) {
        this.identifiers = identifiers;
    }

    public SinkResult(boolean success, Map<String, String> identifiers) {
        this.success = success;
        this.identifiers = identifiers;
    }

    public SinkResult() {
    }

    @Override public String toString() {
        return "SinkResult<success=" + success + ", identifiers=" + identifiers + ">";
    }
}
