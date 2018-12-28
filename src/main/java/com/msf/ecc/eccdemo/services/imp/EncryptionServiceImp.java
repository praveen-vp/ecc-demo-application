package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.services.AESEncryptionService;
import com.msf.ecc.eccdemo.services.EncryptionService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

// @Service
public class EncryptionServiceImp implements EncryptionService {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String message;
    private AESEncryptionService encryptionService;

    public EncryptionServiceImp(PrivateKey privateKey, PublicKey publicKey, String message) {

        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.message = message;
        this.encryptionService = new AESEncryptionServiceImp();
    }

    public String doEncryption()
            throws NoSuchAlgorithmException,
            InvalidKeyException {

        String secretString = generateSecretKey();
        System.out.println(
                "Secret computed encryption : 0x" + secretString);

        return encryptionService.encrypt(message, secretString);
    }

    public String doDecryption(String encryptedMsg) throws NoSuchAlgorithmException,
            InvalidKeyException {

        String secretString = generateSecretKey();
        System.out.println(
                "Secret computed decryption : 0x" + secretString);

        return encryptionService.decrypt(encryptedMsg, secretString);
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
