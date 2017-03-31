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

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.ArrayDeque;

import static org.junit.Assert.*;

public class BtcTxOutputScriptPubKeyTest {

    public static final byte[] SCRIPT = new HexBinaryAdapter().unmarshal(
            "4104678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1" +
                    "f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5fac");
    @Test
    public void test() throws ScriptFailedException, EmptyStackException {
        BtcTxOutputScriptPubKey instruction = new BtcTxOutputScriptPubKey();
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        stack.push(BtcTxOutputTest.OUTPUT);
        instruction.apply(stack);
        assertArrayEquals(SCRIPT, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void emptyStack() throws ScriptFailedException, EmptyStackException {
        BtcTxOutputScriptPubKey instruction = new BtcTxOutputScriptPubKey();
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        try {
            instruction.apply(stack);
            fail("expected empty stack exception");
        } catch (EmptyStackException e) {
        }
    }
}