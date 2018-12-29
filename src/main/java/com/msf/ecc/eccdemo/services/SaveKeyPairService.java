package com.msf.ecc.eccdemo.services;

import com.msf.ecc.eccdemo.models.KeyPairModel;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface SaveKeyPairService {

    void saveKeyPair(String clientId, KeyPairModel keyPairModel);

    void delete(String clientId);

    PublicKey getClientPublicKey(String clientId);

    KeyPair getServerKeyPair(String clientId);

    PrivateKey getServerPrivateKey(String clientId);

    PublicKey getServerPublicKey(String clientId);


}
