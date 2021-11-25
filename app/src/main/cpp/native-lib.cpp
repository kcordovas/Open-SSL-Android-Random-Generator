#include <jni.h>
#include <string>

#include <cstdio>
#include <cstdlib>
#include <cassert>
#include <openssl/rand.h>
#include <android/log.h>
#define TAG "MY NATIVE LOG"

bool isSeedInitialize = false;

extern "C" JNIEXPORT void JNICALL
Java_com_cordova_opensslexamplewithc_openssl_OpenSslApi_initSeed(
        JNIEnv *env,
        jobject /* this */,
        jlong max_bytes_size_of_seed) {
    if (!isSeedInitialize) {
        // Not use in some UNIX system
        // RAND_poll can be used to reseed the generator using the system entropy source.
        // RAND_poll();
        int rc = RAND_load_file("/dev/urandom", max_bytes_size_of_seed);
        if (rc != max_bytes_size_of_seed) {
            isSeedInitialize = false;
        } else {
            isSeedInitialize = true;
        }
    }
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_cordova_opensslexamplewithc_openssl_OpenSslApi_isSeedInitialize(
        JNIEnv *env,
        jobject /* this */) {
    if (isSeedInitialize) {
        return JNI_TRUE;
    }
    return JNI_FALSE;
}

extern "C" JNIEXPORT void JNICALL
Java_com_cordova_opensslexamplewithc_openssl_OpenSslApi_reseed(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray seed) {
    RAND_seed(seed, sizeof(*seed));
}

extern "C" JNIEXPORT jint JNICALL
Java_com_cordova_opensslexamplewithc_openssl_OpenSslApi_intGenerateSecureRandom(
        JNIEnv *env,
        jobject /* this */,
        jintArray empty_array,
        jint size_key) {
    jint *secureRandomIntArray = env->GetIntArrayElements(empty_array, JNI_FALSE);
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "array value %d", secureRandomIntArray[0]);
    jint statusRandResult = RAND_bytes((unsigned char *) secureRandomIntArray,
                                       size_key * sizeof(int)
                                       );
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "The value is %d", statusRandResult);
    env->ReleaseIntArrayElements(empty_array, secureRandomIntArray, 0);
    return statusRandResult;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_cordova_opensslexamplewithc_openssl_OpenSslApi_bytesGenerateSecureRandom(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray empty_array,
        jint size_key) {
    jbyte *secureRandomIntArray = env->GetByteArrayElements(empty_array, JNI_FALSE);
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "array value %d", secureRandomIntArray[0]);
    jint statusRandResult = RAND_bytes(
            (unsigned char *) secureRandomIntArray,
            size_key*sizeof(int)
    );
    __android_log_print(ANDROID_LOG_VERBOSE, TAG, "The value is %d", statusRandResult);
    env->ReleaseByteArrayElements(empty_array, secureRandomIntArray, 0);
    return statusRandResult;
}