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

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScriptParser {
    private final BitcoinTransactionRetriever bitcoinTransactionRetriever;

    public ScriptParser(BitcoinTransactionRetriever bitcoinTransactionRetriever) {
        this.bitcoinTransactionRetriever = bitcoinTransactionRetriever;
    }

    public Iterable<Instruction> parse(String script) {
        Iterable<String> instructions = Splitter.on(Pattern.compile("(\\s|\\n)")).split(script);
        List<Instruction> parsed = new ArrayList<>();
        for (String instruction : instructions) {
            if (instruction.startsWith("0x")) {
                parsed.add(new Push(new HexBinaryAdapter().unmarshal(instruction.substring(2))));
            } else if (instruction.contentEquals("SWAP")) {
                parsed.add(new Swap());
            } else if (instruction.contentEquals("HASH(SHA-256)")) {
                parsed.add(new HashSha256());
            } else if (instruction.contentEquals("EQUAL")) {
                parsed.add(new Equal());
            } else if (instruction.contentEquals("DUP")) {
                parsed.add(new Dup());
            } else if (instruction.contentEquals("CONCAT")) {
                parsed.add(new Concat());
            } else if (instruction.contentEquals("CHECK")) {
                parsed.add(new Check());
            } else if (instruction.contentEquals("BTC_TX_OUTPUT_SCRIPT_PUB_KEY")) {
                parsed.add(new BtcTxOutputScriptPubKey());
            } else if (instruction.contentEquals("BTC_TX_OUTPUT")) {
                parsed.add(new BtcTxOutput());
            } else if (instruction.contentEquals("BTC_TX")) {
                parsed.add(new BtcTx(bitcoinTransactionRetriever));
            } else if (instruction.isEmpty()) {
            } else {
                throw new IllegalArgumentException(instruction);
            }
        }
        return parsed;
    }
}
