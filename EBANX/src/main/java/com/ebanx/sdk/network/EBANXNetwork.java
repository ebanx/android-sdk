package com.ebanx.sdk.network;

import android.util.Log;

import com.ebanx.sdk.EBANX;
import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class EBANXNetwork implements EBANXNetworkingInterface {

    private static final String PROD_URL = "https://api.ebanx.com/ws/";
    private static final String DEV_URL  = "https://sandbox.ebanx.com/ws/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "EBANXNetwork";

    private OkHttpClient client;

    /**
     * Default constructor for EBANXNetwork
     */
    public EBANXNetwork() {
        this.client =  new OkHttpClient();
    }

    private String getBaseURL() {
        return EBANX.getTestMode() ? DEV_URL : PROD_URL;
    }

    /**
     * Request token
     *
     * @param card EBANXCreditCard
     * @param countryType EBANXCountry
     * @param complete EBANXResponseNetwork
     */
    @Override
    public void token(EBANXCreditCard card, EBANXCountry countryType, final EBANXResponseNetwork complete)  {

        JSONObject parameters = new JSONObject();
        JSONObject creditcard = new JSONObject();

        try {
            creditcard.put("card_number", card.getNumber());
            creditcard.put("card_name", card.getName());
            creditcard.put("card_due_date", card.getDueDate());
            creditcard.put("card_cvv", card.getCvv());

            parameters.put("public_integration_key", EBANX.getPublicKey());
            parameters.put("payment_type_code", card.getType().description());
            parameters.put("country", countryType.description());
            parameters.put("creditcard", creditcard);

        } catch (JSONException e) {
            complete.OnFailure(e);
        }

        executeRequest("token", RequestBody.create(JSON, parameters.toString()), complete);
    }

    /**
     * Set CVV with token
     *
     * @param token token
     * @param cvv String
     * @param complete EBANXResponseNetwork
     */
    @Override
    public void setCVV(String token, String cvv, final EBANXResponseNetwork complete) {

        JSONObject parameters = new JSONObject();

        try {
            parameters.put("public_integration_key", EBANX.getPublicKey());
            parameters.put("token", token);
            parameters.put("card_cvv", cvv);

        } catch (JSONException e) {
            complete.OnFailure(e);
        }

        executeRequest("token/setCVV", RequestBody.create(JSON, parameters.toString()), complete);
    }

    private void executeRequest(String path, RequestBody body, final EBANXResponseNetwork complete) {
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(getBaseURL() + path)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, e.toString());
                complete.OnFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d(TAG, response.body().string());

                if (!response.isSuccessful()) {
                    complete.OnFailure(new IOException(response.message()));
                } else {
                    complete.OnSuccess(response.body().string());
                }
            }
        });
    }
}