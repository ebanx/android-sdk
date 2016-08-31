package com.ebanx.example;

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

        EBANX.configure(getApplicationContext(), "1231000", true);

        EBANX.Token.create(
                new EBANXCreditCard(
                        "Fulano de tal", "4012888888881881", "12/2016", "321", EBANXCreditCardType.Visa
                ),
                EBANXCountry.BR,
                new EBANXTokenRequestComplete() {
                    @Override
                    public void Success(EBANXToken token) {
                        Log.d(TAG, "Success:" + token.toString());
                    }

                    @Override
                    public void APIError(EBANXError error) {
                        Log.d(TAG, "APIError:" + error.toString());
                    }

                    @Override
                    public void NetworkError(Exception e) {
                        Log.d(TAG, "NetworkError:" +  e.toString());
                    }
                }
        );
    }
}
