package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.services.KeyAgreementService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class KeyAgreementServiceImp implements KeyAgreementService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyAgreementServiceImp() {
    }

    public KeyAgreementServiceImp(PrivateKey senderPrivateKey, PublicKey receiverPublicKey) {

        this.privateKey = senderPrivateKey;
        this.publicKey = receiverPublicKey;
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
