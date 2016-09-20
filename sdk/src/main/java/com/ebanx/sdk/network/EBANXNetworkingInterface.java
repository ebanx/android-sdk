package com.ebanx.sdk.network;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;

public interface EBANXNetworkingInterface {
    void token(EBANXCreditCard card, EBANXCountry countryType, EBANXResponseNetwork complete);
    void setCVV(String token, String cvv, EBANXResponseNetwork complete);
}

