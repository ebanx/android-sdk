package com.ebanx.sdk.entities;

/**
 * Used to represent country
 */
public enum EBANXCountry {

    /**
     * Brazil
     */
    BR,

    /**
     * Peru
     */
    PE,

    /**
     * Mexico
     */
    MX,

    /**
     * Colombia
     */
    CO,

    /**
     * Chile
     */
    CL;

    /**
     * Returns the country in lower case
     *
     * @return String
     */
    public String description() {
        return toString().toLowerCase();
    }
}
