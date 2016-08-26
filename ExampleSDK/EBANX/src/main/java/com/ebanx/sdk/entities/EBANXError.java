package com.ebanx.sdk.entities;

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
