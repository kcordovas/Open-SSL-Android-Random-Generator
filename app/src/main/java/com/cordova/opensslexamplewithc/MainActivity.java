package com.cordova.opensslexamplewithc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cordova.opensslexamplewithc.databinding.ActivityMainBinding;
import com.cordova.opensslexamplewithc.openssl.EStateGenerateSecureRandom;
import com.cordova.opensslexamplewithc.openssl.OpenSslApi;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String METHOD_NAME = "onCreate";

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGoToSecondScreen.setOnClickListener(v -> goToSecondScreen());

        print(METHOD_NAME + ":Init Seed");
        print(METHOD_NAME + ":is first seed Initialize ?" + OpenSslCore.getOpenSsl().isSeedInitialize());

        int sizeKey = 50;
        int[] emptyArray = new int[sizeKey];
        print(METHOD_NAME + ":BEFORE ARRAY:");
        printIntArray(emptyArray);
        EStateGenerateSecureRandom statusRand = OpenSslCore.getOpenSsl().intSecureRandom(emptyArray, sizeKey);
        print(METHOD_NAME + ":AFTER ARRAY:");
        printIntArray(emptyArray);
        print(METHOD_NAME + ":StatusRand:" + statusRand.name());

        print(METHOD_NAME + ":Init Seed");
        long maxBytesSizeOfSeed = 512;
        OpenSslCore.getOpenSsl().initSeed(maxBytesSizeOfSeed);
        print(METHOD_NAME + ":is seed Initialize ?" + OpenSslCore.getOpenSsl().isSeedInitialize());

        if (OpenSslCore.getOpenSsl().isSeedInitialize()) {
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
        }
    }

    private void goToSecondScreen() {
        startActivity(new Intent(this, SecondActivity.class));
        finish();
    }

    private void printIntArray(int[] intArray) {
        int count = 1;
        for (int i : intArray) {
            print("element:" + count + ":value:" + i);
            count++;
        }
    }

    private void print(String message) {
        binding.sampleText.append(message + "\n");
    }
}