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

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class ScriptParserTest {

    public static final String SCRIPT = "0x5df7b6e09b8c3f655c7773ea051148daccabfe03c6e795bf8b200f97188a5f71\n" +
                         "CONCAT HASH(SHA-256) \n" +
                         "DUP 0x4ff2bd5226472a790740c50f043863a224cc1b97c4eb6602533ff7d1b3ae54a5 \n" +
                         "EQUAL CHECK \n" +
                         "DUP 0x6a20 SWAP CONCAT 0x1067897faf01e160c1adce875cf0582f6ed1cbafd325726ac7b37d12010a7e78\n" +
                         "BTC_TX \n" +
                         "0x00 BTC_TX_OUTPUT BTC_TX_OUTPUT_SCRIPT_PUB_KEY EQUAL CHECK";

    @Test
    public void test() {
        ScriptParser parser = new ScriptParser(new BlockchainInfoBitcoinTransactionRetriever());
        Iterable<Instruction> instructions = parser.parse(SCRIPT);
        Iterator<String> it = StreamSupport.stream(instructions.spliterator(), false).map(Instruction::getString)
                                                 .iterator();
        assertEquals(SCRIPT.replace("\n", " ").replace("  ", " "), Joiner.on(" ").join(it));
    }
}