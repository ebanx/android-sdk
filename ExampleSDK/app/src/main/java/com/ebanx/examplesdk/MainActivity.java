package com.ebanx.examplesdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ebanx.sdk.EBANX;
import com.ebanx.sdk.EBANXTokenRequestComplete;
import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;
import com.ebanx.sdk.entities.EBANXCreditCardType;
import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EBANX.configure("1231000", true);

        EBANXCreditCard card = new EBANXCreditCard("Fulano de tal", "4111111111111111","12/2016","123", EBANXCreditCardType.Visa);
        EBANX.Token.create(card, EBANXCountry.BR, new EBANXTokenRequestComplete() {
            @Override
            public void Success(EBANXToken token) {
                Log.d(TAG, "Success:" + token.toString() );

                EBANX.Token.setCVV(token, "1234", null);
            }

            @Override
            public void APIError(EBANXError error) {
                Log.d(TAG, "APIError:" + error.toString() );
            }

            @Override
            public void NetworkError(Exception e) {
                Log.d(TAG, "NetworkError:" + e.toString() );
            }
        });
    }
}
