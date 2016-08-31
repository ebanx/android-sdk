package com.ebanx.sdk.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.ebanx.sdk.EBANX;
import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public final class EBANXNetwork implements EBANXNetworkingInterface {

    private static final String PROD_URL = "https://api.ebanx.com/ws/";
    private static final String DEV_URL  = "https://sandbox.ebanx.com/ws/";
    private static final String TAG = "EBANXNetwork";
    private static final int TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private Context context;

    private enum Method {
        GET,
        POST
    }

    public EBANXNetwork(Context context) {
        this.context = context;
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

        final JSONObject parameters = new JSONObject();
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

        executeAsyncTask("token", Method.POST, parameters, complete);
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

        executeAsyncTask("token/setCVV", Method.POST, parameters, complete);
    }

    private void executeAsyncTask(final String path, final Method method, final JSONObject parameters, final EBANXResponseNetwork complete) {

        if (isOnline()) {
            new AsyncTask<Void, Void, String>() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    complete.OnSuccess(s);
                }

                @Override
                protected String doInBackground(Void... voids) {
                    return executeRequest(path, method, parameters);
                }
            }.execute();
        } else {
            complete.OnFailure(new Exception("No internet connection"));
        }
    }

    private HttpsURLConnection connect(String urlString, Method method, JSONObject parameters) throws IOException {
        URL url = new URL(urlString);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setReadTimeout(TIMEOUT);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setRequestMethod(method.name());
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        if (method == Method.POST) {
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            OutputStreamWriter out = new  OutputStreamWriter(connection.getOutputStream());
            out.write(parameters.toString());
            out.close();
        }

        connection.connect();
        return connection;
    }

    private String executeRequest(String path, Method method, JSONObject parameters) {
        try {
            HttpsURLConnection connection = connect(  getBaseURL() + path, method, parameters);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                return parseResponse(is);
            }

        } catch (Exception e) {
            return e.toString();
        }

        return null;
    }

    private String parseResponse(InputStream is) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
