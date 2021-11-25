package com.cordova.opensslexamplewithc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cordova.opensslexamplewithc.databinding.ActivitySecondBinding;
import com.cordova.opensslexamplewithc.openssl.EStateGenerateSecureRandom;
import com.cordova.opensslexamplewithc.openssl.OpenSslApi;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecondActivity extends AppCompatActivity {

    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "onCreate";
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGoToMainScreen.setOnClickListener(v -> goToMainActivity());

        int sizeKey = 50;
        byte[] secureRandomArray = new byte[sizeKey];

        if (OpenSslCore.getOpenSsl().isSeedInitialize()) {
            print(METHOD_NAME + ":IS SEED INITIALIZE?:YES");
            print(METHOD_NAME + ":BEFORE BYTES ARRAY:");
            print(METHOD_NAME + ":BYTE ARRAY:" + byteArrayToHexString(secureRandomArray));
            EStateGenerateSecureRandom statusRand = OpenSslCore.getOpenSsl().bytesSecureRandom(secureRandomArray, sizeKey);
            print(METHOD_NAME + ":AFTER BYTES ARRAY:");
            print(METHOD_NAME + ":BYTE ARRAY:" + byteArrayToHexString(secureRandomArray));
            print(METHOD_NAME + ":StatusRand:" + statusRand.name());

            byte[] seedInit = new byte[125];
            print(METHOD_NAME + ":Reseed:Seed Param INIT:" + seedInit[0]);
            try {
                SecureRandom.getInstance("SHA1PRNG").nextBytes(seedInit);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            print(METHOD_NAME + ":Reseed:Seed Param AFTER:" + seedInit[0]);
            OpenSslCore.getOpenSsl().reseed(seedInit);
            print(METHOD_NAME + ":Reseed:OK");
        } else {
            print(METHOD_NAME + ":IS SEED INITIALIZE?NOT");
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void print(String message) {
        binding.sampleTextSecond.append(message + "\n");
    }

    public String byteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}