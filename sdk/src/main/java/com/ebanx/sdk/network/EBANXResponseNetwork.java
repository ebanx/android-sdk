package com.ebanx.sdk.network;

public interface EBANXResponseNetwork {
    void OnSuccess(String response);
    void OnFailure(Exception e);
}
