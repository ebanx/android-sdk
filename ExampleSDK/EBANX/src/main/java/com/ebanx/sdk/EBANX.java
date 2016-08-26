package com.ebanx.sdk;

import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.network.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Basic class for EBANX SDK calls
 */
public final class EBANX {

    private String publicKey;
    private boolean testMode;

    private static EBANX instance = new EBANX();

    private EBANXNetwork network = new EBANXNetwork();

    private EBANX() {}

    /**
     * Configure EBANX SDK
     *
     * @param publicKey String
     */
    public static void configure(String publicKey) {
        EBANX.configure(publicKey, false);
    }

    /**
     * * Configure EBANX SDK and set testMode
     *
     * @param publicKey String
     * @param testMode boolean
     */
    public static void configure(String publicKey, boolean testMode) {
        instance.publicKey = publicKey;
        instance.testMode = testMode;
    }

    /**
     * Return active test mode
     *
     * @return boolean
     */
    public static boolean getTestMode() {
        return instance.testMode;
    }

    /**
     * Return current publick key
     *
     * @return String
     */
    public static String getPublicKey() {
        return instance.publicKey;
    }

    /**
     * Basic class for token calls
     */
    public static final class Token {

        private static final String TAG = "EBANX";

        private Token() {}

        /**
         * Request token with creditcard, country and completionHandler
         *
         * @param card EBANXCreditCard
         * @param country EBANXCountry
         */
        public static void create(EBANXCreditCard card, EBANXCountry country, final EBANXTokenRequestComplete complete) {
            instance.network.token(card, country, new EBANXResponseNetwork() {
                @Override
                public void OnSuccess(String response) {
                    parseResult(response, complete);
                }

                @Override
                public void OnFailure(Exception e) {
                    complete.NetworkError(e);
                }
            });
        }

        /**
         * Set CVV with token object
         *
         * @param token EBANXToken
         * @param cvv String
         */
        public static void setCVV(EBANXToken token, String cvv, EBANXTokenRequestComplete complete) {
            setCVV(token.getToken(), cvv, complete);
        }

        /**
         * Set CVV with token string
         *
         * @param token String
         * @param cvv String
         */
        public static void setCVV(String token, String cvv, final EBANXTokenRequestComplete complete) {
            instance.network.setCVV(token, cvv, new EBANXResponseNetwork() {
                @Override
                public void OnSuccess(String response) {
                    parseResult(response, complete);
                }

                @Override
                public void OnFailure(Exception e) {
                    complete.NetworkError(e);
                }
            });
        }

        private static void parseResult(String response, EBANXTokenRequestComplete complete) {
            Log.d(TAG, "parseResult:" + response);

            try {
                JSONObject JSON = new JSONObject(response);

                if (JSON.has("status")) {
                    if (JSON.get("status").toString().equalsIgnoreCase("ERROR")) {
                        EBANXError error = new EBANXError(JSON.getString("status"), JSON.getString("status_code"), JSON.getString("status_message"));
                        complete.APIError(error);
                    } else {
                        EBANXToken token = new EBANXToken(JSON.getString("token"), JSON.getString("masked_card_number"));
                        complete.Success(token);
                    }
                }
            } catch (JSONException e) {
                complete.NetworkError(e);
            }
        }
    }
}
