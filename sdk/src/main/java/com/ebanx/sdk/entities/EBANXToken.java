package com.ebanx.sdk.entities;

/**
 * Credit card token
 */
public final class EBANXToken {

    /**
     * Credit card token
     */
    private String token;

    /**
     * Credit card number masked
     */
    private String cardNumberMasked;

    /**
     * Create EBANXToken
     * @param token String
     * @param cardNumberMasked String
     */
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

    @Override
    public String toString() {
        return "EBANXToken{" +
                "token='" + token + '\'' +
                ", cardNumberMasked='" + cardNumberMasked + '\'' +
                '}';
    }
}
