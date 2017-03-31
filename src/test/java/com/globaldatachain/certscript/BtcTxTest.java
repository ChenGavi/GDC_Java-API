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

public class BtcTxTest {


    public static final byte[] TX = new HexBinaryAdapter().unmarshal(
            "01000000010000000000000000000000000000000000000000000000000000000000000000" +
                    "ffffffff4d04ffff001d0104455468652054696d65732030332f4a616e2f323030392" +
                    "04368616e63656c6c6f72206f6e206272696e6b206f66207365636f6e64206261696c" +
                    "6f757420666f722062616e6b73ffffffff0100f2052a01000000434104678afdb0fe5" +
                    "548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4" +
                    "f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5fac00000000");

    @Test
    public void test() throws ScriptFailedException, EmptyStackException {
        BtcTx instruction = new BtcTx(new BlockchainInfoBitcoinTransactionRetriever());
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        stack.push(new HexBinaryAdapter().unmarshal("4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b"));
        instruction.apply(stack);
        assertArrayEquals(TX, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void emptyStack() throws ScriptFailedException, EmptyStackException {
        BtcTx instruction = new BtcTx(new BlockchainInfoBitcoinTransactionRetriever());
        ArrayDeque<byte[]> stack = new ArrayDeque<>();
        try {
            instruction.apply(stack);
            fail("expected empty stack exception");
        } catch (EmptyStackException e) {
        }
    }
}