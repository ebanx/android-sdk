package com.ebanx.sdk.entities;

public enum EBANXCreditCardType {
    /**
     * Amex type
     */
    Amex,

    /**
     * Aura type
     */
    Aura,

    /**
     * Carnet type
     */
    Carnet,

    /**
     * Diners Diners type
     */
    Diners,

    /**
     * Discover type
     */
    Discover,

    /**
     * Elo type
     */
    Elo,

    /**
     * Hipercard type
     */
    Hipercard,

    /**
     * Mastercard type
     */
    Mastercard,

    /**
     * Visa type
     */
    Visa;

    /**
     * Returns the code in lower case
     */
    public String description() {
        return toString().toLowerCase();
    }
}
