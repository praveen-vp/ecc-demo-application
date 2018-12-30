package com.msf.ecc.eccdemo.services;

import javax.crypto.spec.SecretKeySpec;

public interface PrivateKeyEncryptionService {

    String encrypt(String strToEncrypt);

    String decrypt(String strToDecrypt);

    SecretKeySpec setKey(String myKey);
}