package com.ebanx.sdk.entities;

import com.ebanx.sdk.EBANX;

/**
 * Used to represent error from API response
 */
public final class EBANXError {

    public enum ErrorType {
        PublicKeyNotSet,
        InvalidPublicKey,
        ParseError,
        GenericError
    }

    /**
     * Status
     */
    public String status;

    /**
     * Code
     */
    public String code;

    /**
     * Message
     */
    public String message;

    /**
     * type
     */
    private ErrorType type;

    /**
     * Create EBANXError
     *
     * @param status String
     * @param code String
     * @param message String
     */
    public EBANXError(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;

        if (this.code.equalsIgnoreCase("DA-1") && this.message.equalsIgnoreCase("Invalid integration key")) {
            this.type = ErrorType.InvalidPublicKey;
        } else {
            this.type = ErrorType.GenericError;
        }
    }

    /**
     * Create EBANXError
     *
     * @param status String
     * @param code String
     * @param message String
     * @param type ErrorType
     */
    public EBANXError(String status, String code, String message, ErrorType type) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public static EBANXError createPublicKeyNotSetError() {
        return new EBANXError("ERROR", "1",  "Public key not set", ErrorType.PublicKeyNotSet);
    }

    public static EBANXError createParseError() {
        return new EBANXError("ERROR", "2",  "Parse error", ErrorType.ParseError);
    }

    @Override
    public String toString() {
        return "EBANXError{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
