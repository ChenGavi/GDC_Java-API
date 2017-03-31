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

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;

import static org.junit.Assert.*;

public class HashSha256Test {
    @Test
    public void test() throws ScriptFailedException, EmptyStackException, NoSuchAlgorithmException {
        HashSha256 instruction = new HashSha256();
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        stack.push(new byte[]{1});
        instruction.apply(stack);
        assertArrayEquals(MessageDigest.getInstance("SHA-256").digest(new byte[]{1}), stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void emptyStack() throws ScriptFailedException, EmptyStackException {
        HashSha256 instruction = new HashSha256();
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        try {
            instruction.apply(stack);
            fail("expected empty stack exception");
        } catch (EmptyStackException e) {
        }
    }
}