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
package com.globaldatachain.certscript;

import java.util.Deque;
import java.util.NoSuchElementException;

public class Swap implements Instruction {

    @Override public String getString() {
        return "SWAP";
    }

    @Override public void apply(Deque<byte[]> stack) throws ScriptFailedException, EmptyStackException {
        try {
            byte[] b = stack.pop();
            byte[] a = stack.pop();

            stack.push(b);
            stack.push(a);

        } catch (NoSuchElementException e) {
            throw new EmptyStackException();
        }
    }
}
