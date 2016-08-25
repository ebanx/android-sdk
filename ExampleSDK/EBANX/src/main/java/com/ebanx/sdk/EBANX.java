package com.ebanx.sdk;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.network.*;

/**
 * Basic class for EBANX SDK calls
 */
public final class EBANX {

    private String publicKey;
    private boolean testMode;

    private static EBANX instance = new EBANX();

    private EBANXNetwork network = new EBANXNetwork();

    private EBANX() {}

    public static void configure(String publicKey) {
        EBANX.configure(publicKey, false);
    }

    public static void configure(String publicKey, boolean testMode) {
        instance.publicKey = publicKey;
        instance.testMode = testMode;
    }

    public static boolean getTestMode() {
        return instance.testMode;
    }

    public static final class Token {

        private Token() {}

        public static void create(EBANXCreditCard card, EBANXCountry country) {

        }

        public static void setCVV(EBANXToken token, String cvv) {
            setCVV(token.getToken(), cvv);
        }

        public static void setCVV(String token, String cvv) {

        }
    }
}
