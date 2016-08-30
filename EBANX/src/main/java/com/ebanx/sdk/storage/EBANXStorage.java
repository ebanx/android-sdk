package com.ebanx.sdk.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ebanx.sdk.entities.EBANXToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class EBANXStorage {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String PREFERENCES_KEY = "com.ebanx.sdk";
    private static final String TOKENS = "tokens";
    private List<EBANXToken> currentTokens = new ArrayList<>();

    public EBANXStorage(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();

        String tokenString = preferences.getString(TOKENS, "");
        if (!tokenString.equalsIgnoreCase("")) {
            try {
                JSONObject object = new JSONObject(tokenString);

                Iterator<String> iter = object.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    EBANXToken token = new EBANXToken(object.get(key).toString(), key);
                    currentTokens.add(token);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToken(EBANXToken token) {
        int index = 0;
        boolean haveToken = false;
        for (EBANXToken t : currentTokens) {
            if (t.getCardNumberMasked().equalsIgnoreCase(token.getCardNumberMasked())) {
                currentTokens.set(index, token);
                haveToken = true;
                return;
            }
            index++;
        }

        if (!haveToken) {
            currentTokens.add(token);
        }

        updateTokens();
    }

    public EBANXToken getToken(String cardNumberMasked)  {
        for (EBANXToken token : currentTokens) {
            if (token.getCardNumberMasked().equalsIgnoreCase(cardNumberMasked)) {
                return token;
            }
        }

        return null;
    }

    public void deleteToken(EBANXToken token) {
        for (EBANXToken t : currentTokens) {
            if (t.getCardNumberMasked().equalsIgnoreCase(token.getCardNumberMasked())) {
                currentTokens.remove(t);
                return;
            }
        }

        updateTokens();
    }

    public List<EBANXToken> getTokens() {
        return currentTokens;
    }

    public void deleteAllTokens()  {
        editor.remove(TOKENS);
        editor.commit();
        currentTokens.clear();
    }

    private void updateTokens() {
        JSONObject object = new JSONObject();
        for (EBANXToken token : currentTokens) {
            try {
                object.put(token.getCardNumberMasked(), token.getToken());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        editor.putString(TOKENS, object.toString());
        editor.commit();
    }
}
