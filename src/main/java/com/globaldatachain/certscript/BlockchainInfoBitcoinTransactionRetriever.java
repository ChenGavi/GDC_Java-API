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

import info.blockchain.api.APIException;
import info.blockchain.api.HttpClient;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockchainInfoBitcoinTransactionRetriever implements BitcoinTransactionRetriever {

    private final String apiCode;

    public BlockchainInfoBitcoinTransactionRetriever(String apiCode) {
        this.apiCode = apiCode;
    }

    public BlockchainInfoBitcoinTransactionRetriever() {
        this.apiCode = null;
    }

    @Override public byte[] getTransaction(byte[] txid) {

        final StringBuilder builder = new StringBuilder();
        for (byte b : txid) {
            builder.append(String.format("%02x", b));
        }
        String txHash = builder.toString();

        try {
            String response = HttpClient.getInstance().get("rawtx/" + txHash, buildRequest());
            return new HexBinaryAdapter().unmarshal(response);
        } catch (APIException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> buildRequest () {
        Map<String, String> params = new HashMap<>();

        params.put("format", "hex");
        if (apiCode != null) {
            params.put("api_code", apiCode);
        }

        return params;
    }
}
