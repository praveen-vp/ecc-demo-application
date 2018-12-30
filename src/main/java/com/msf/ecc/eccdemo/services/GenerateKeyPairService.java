package com.msf.ecc.eccdemo.services;

import com.msf.ecc.eccdemo.models.BaseModel;

import java.security.*;

public interface GenerateKeyPairService {

    KeyPair generateKeyPair()
            throws NoSuchAlgorithmException,
            NoSuchProviderException,
            InvalidAlgorithmParameterException;

    BaseModel getPublicKeyString(PublicKey publicKey);

    PublicKey generatePublicKeyFromString(String publicKeyString) throws Exception;
}