package com.ebanx.sdk.entities;

public final class EBANXToken {

    /// Credit card token
    private String token;

    /// Credit card number masked
    private String cardNumberMasked;

    public EBANXToken(String token, String cardNumberMasked) {
        this.token = token;
        this.cardNumberMasked = cardNumberMasked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCardNumberMasked() {
        return cardNumberMasked;
    }

    public void setCardNumberMasked(String cardNumberMasked) {
        this.cardNumberMasked = cardNumberMasked;
    }
}
