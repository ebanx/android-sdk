package com.ebanx.sdk.entities;

/**
 * Used to store all credit card properties
 */
public final class EBANXCreditCard {

    /**
     * Credit card number
     */
    private String number;

    /**
     * Credit card name
     */
    private String name;

    /**
     * Credit card validate. Format: 'MM/AAAA'
     */
    private String dueDate;

    /**
     * Credit card CVV
     */
    private String cvv;

    /**
     * Credit card payment type code
     */
    private EBANXCreditCardType type;

    /**
     * Create EBANXCreditCard
     * @param name String
     * @param number String
     * @param dueDate String
     * @param cvv String
     * @param type EBANXCreditCardType
     */
    public EBANXCreditCard(String name, String number, String dueDate, String cvv, EBANXCreditCardType type) {
        this.name = name;
        this.number = number;
        this.dueDate = dueDate;
        this.cvv = cvv;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public EBANXCreditCardType getType() {
        return type;
    }

    public void setType(EBANXCreditCardType type) {
        this.type = type;
    }
}
