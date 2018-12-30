package com.msf.ecc.eccdemo.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface KeyAgreementService {

    String generateSecretKey() throws NoSuchAlgorithmException,
            InvalidKeyException;
}
