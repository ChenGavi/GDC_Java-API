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
import java.util.Deque;

import static com.globaldatachain.certscript.ScriptParserTest.SCRIPT;
import static org.junit.Assert.*;

public class ScriptTest {

    @Test
    public void test() {
        ScriptParser parser = new ScriptParser(new BlockchainInfoBitcoinTransactionRetriever());
        Iterable<Instruction> instructions = parser.parse(SCRIPT);
        Deque<byte[]> stack = new ArrayDeque<>();
        stack.push(new HexBinaryAdapter().unmarshal
                ("5df7b6e09b8c3f655c7773ea051148daccabfe03c6e795bf8b200f97188a5f71"));
        instructions.forEach(i -> {
            try {
                i.apply(stack);
            } catch (ScriptFailedException | EmptyStackException e) {
            }
        });
        assertArrayEquals(new HexBinaryAdapter().unmarshal("4ff2bd5226472a790740c50f043863a224cc1b" +
                                                                   "97c4eb6602533ff7d1b3ae54a5"), stack.pop());

        assertTrue(stack.isEmpty());
    }
}
