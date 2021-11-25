package com.cordova.opensslexamplewithc.openssl;

public enum EStateGenerateSecureRandom {
    SUCCESS(1),
    ERROR(0),
    NOT_SUPPORTED(-1);

    private final int code;

    EStateGenerateSecureRandom(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
