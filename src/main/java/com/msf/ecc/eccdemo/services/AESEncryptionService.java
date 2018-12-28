package com.msf.ecc.eccdemo.services;

public interface AESEncryptionService {

    String encrypt(String strToEncrypt, String secret);

    String decrypt(String strToDecrypt, String secret);

    void setKey(String myKey);
}