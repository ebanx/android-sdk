package com.ebanx.sdk;

import com.ebanx.sdk.entities.EBANXError;
import com.ebanx.sdk.entities.EBANXToken;

public interface EBANXTokenRequestComplete {
    void Success(EBANXToken token);
    void APIError(EBANXError error);
    void NetworkError(Exception e);
}
