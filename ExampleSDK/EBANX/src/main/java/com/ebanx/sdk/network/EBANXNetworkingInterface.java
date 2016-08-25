package com.ebanx.sdk.network;

import com.ebanx.sdk.entities.EBANXCountry;
import com.ebanx.sdk.entities.EBANXCreditCard;

interface EBANXNetworkingInterface {
    void token(EBANXCreditCard card, EBANXCountry countryType, EBANXResponseNetwork complete);
}

interface EBANXResponseNetwork {
    Void OnSuccess();
    Void OnFailure();
}
