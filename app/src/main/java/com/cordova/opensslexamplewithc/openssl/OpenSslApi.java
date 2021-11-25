package com.cordova.opensslexamplewithc.openssl;

public class OpenSslApi {

    private static OpenSslApi _INSTANCE;

    static {
        System.loadLibrary("native-lib");
    }

    private OpenSslApi() {}

    public static OpenSslApi getInstance() {
        if (_INSTANCE == null) {
            _INSTANCE = new OpenSslApi();
        }
        return _INSTANCE;
    }

    public EStateGenerateSecureRandom formatResultGenerateSecureRandom(int code) {
        switch (code) {
            case 1:
                return EStateGenerateSecureRandom.SUCCESS;
            case 0:
                return EStateGenerateSecureRandom.ERROR;
            default:
                return EStateGenerateSecureRandom.NOT_SUPPORTED;
        }
    }

    public EStateGenerateSecureRandom intSecureRandom(
        int[] emptyArray,
        final int sizeKey
    ) {
        return formatResultGenerateSecureRandom(
                intGenerateSecureRandom(emptyArray, sizeKey)
        );
    }

    public EStateGenerateSecureRandom bytesSecureRandom(
            byte[] emptyArray,
            final int sizeKey
    ) {
        return formatResultGenerateSecureRandom(
                bytesGenerateSecureRandom(emptyArray, sizeKey)
        );
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private native int intGenerateSecureRandom(
            int[] emptyArray,
            final int sizeKey
    );
    private native int bytesGenerateSecureRandom(
            byte[] emptyArray,
            final int sizeKey
    );
    public native void initSeed(long maxBytesSizeOfSeed);
    public native boolean isSeedInitialize();
    public native void reseed(
            byte[] seed
    );
}
