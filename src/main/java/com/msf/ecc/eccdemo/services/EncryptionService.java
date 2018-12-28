package com.msf.ecc.eccdemo.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface EncryptionService {

    String doEncryption() throws NoSuchAlgorithmException,
            InvalidKeyException;

    String doDecryption(String encryptedMsg) throws NoSuchAlgorithmException,
            InvalidKeyException;

    String generateSecretKey() throws NoSuchAlgorithmException,
            InvalidKeyException;
}
