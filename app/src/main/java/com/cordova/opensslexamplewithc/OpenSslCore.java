package com.cordova.opensslexamplewithc;

import com.cordova.opensslexamplewithc.openssl.OpenSslApi;

public class OpenSslCore {

    public static OpenSslApi getOpenSsl() {
        return OpenSslApi.getInstance();
    }
}
