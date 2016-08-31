package com.ebanx.sdk;

import android.content.Context;
import android.util.Log;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;
import com.ebanx.sdk.network.*;
import com.ebanx.sdk.storage.EBANXStorage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Basic class for EBANX SDK calls
 */
public final class EBANX {

    public static final String TAG = "EBANX";
    private String publicKey;
    private boolean testMode;
    private Context context;

    private static boolean isSDKInitialized = false;
    private static EBANX instance = new EBANX();

    private EBANXNetwork network;

    private EBANX() {}

    /**
     * Configure EBANX SDK
     *
     * @param publicKey String
     */
    public static void configure(Context context, String publicKey) {
        if (isSDKInitialized) {
            return;
        }

        EBANX.configure(context, publicKey, false);
    }

    /**
     * * Configure EBANX SDK and set testMode
     *
     * @param publicKey String
     * @param testMode boolean
     */
    public static void configure(Context context, String publicKey, boolean testMode) {
        if (isSDKInitialized) {
            return;
        }

        Log.d(TAG, publicKey);
        instance.context = context;
        instance.publicKey = publicKey;
        instance.testMode = testMode;
        instance.network = new EBANXNetwork(instance.context);
        isSDKInitialized = true;
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

    public static boolean publicKeyIsSet() {
        if (instance.publicKey != null && !instance.publicKey.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    /**
     * Basic class for token calls
     */
    public static final class Token {

        private static final String TAG = "EBANX.Token";
        private static EBANXStorage storage = new EBANXStorage(instance.context);

        private Token() {}

        /**
         * Request token with creditcard, country and completionHandler
         *
         * @param card EBANXCreditCard
         * @param country EBANXCountry
         */
        public static void

        create(EBANXCreditCard card, EBANXCountry country, final EBANXTokenRequestComplete complete) {
            if (!publicKeyIsSet()) {
                complete.APIError(EBANXError.createPublicKeyNotSetError());
                return;
            }

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
            if (!publicKeyIsSet()) {
                complete.APIError(EBANXError.createPublicKeyNotSetError());
                return;
            }

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

        /**
         * Retrive all tokens in EBANXStorage
         *
         * @return List<EBANXToken>
         */
        public static List<EBANXToken> getTokens() {
            return storage.getTokens();
        }

        /**
         * Retrive token from card number masked
         *
         * @param cardNumberMasked
         * @return EBANXToken
         * @throws Exception
         */
        public static EBANXToken getToken(String cardNumberMasked) throws Exception {
            return storage.getToken(cardNumberMasked);
        }

        /**
         * Delete token
         *
         * @param token
         */
        public static void deleteToken(EBANXToken token) {
            storage.deleteToken(token);
        }

        /**
         * Delete all token
         *
         */
        public static void  deleteAllTokens() {
            storage.deleteAllTokens();
        }

        private static void parseResult(String response, EBANXTokenRequestComplete complete) {
            try {
                JSONObject JSON = new JSONObject(response);

                if (JSON.has("status")) {
                    if (JSON.get("status").toString().equalsIgnoreCase("ERROR")) {
                        EBANXError error = new EBANXError(JSON.getString("status"), JSON.getString("status_code"), JSON.getString("status_message"));
                        complete.APIError(error);
                    } else {
                        EBANXToken token = new EBANXToken(JSON.getString("token"), JSON.getString("masked_card_number"));
                        storage.saveToken(token);
                        complete.Success(token);
                    }
                }
            } catch (JSONException e) {
                complete.APIError(EBANXError.createParseError());
            }
        }
    }
}
