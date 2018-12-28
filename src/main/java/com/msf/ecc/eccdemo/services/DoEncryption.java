package com.msf.ecc.eccdemo.services;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DoEncryption {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String message;


    public DoEncryption(PrivateKey privateKey, PublicKey publicKey, String message) {

        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.message = message;
    }

    public String doEncryption()
            throws NoSuchAlgorithmException,
            InvalidKeyException {

        String secretString = generateSecretKey();
        System.out.println(
                "Secret computed encryption : 0x" + secretString);

        return AESEncryption.encrypt(message, secretString);

    }


    public String doDecryption(String encryptedMsg) throws NoSuchAlgorithmException,
            InvalidKeyException {

        String secretString = generateSecretKey();
        System.out.println(
                "Secret computed decryption : 0x" + secretString);

        return AESEncryption.decrypt(encryptedMsg, secretString);

    }

    public String generateSecretKey() throws NoSuchAlgorithmException,
            InvalidKeyException {

        javax.crypto.KeyAgreement keyAgreement = javax.crypto.KeyAgreement.getInstance("ECDH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);

        String secretString = (new BigInteger(1, keyAgreement.generateSecret()).toString(16)).toUpperCase();

        return secretString;
    }

}
