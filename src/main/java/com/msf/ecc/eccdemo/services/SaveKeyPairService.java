package com.msf.ecc.eccdemo.services;

import com.msf.ecc.eccdemo.models.KeyPairModel;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

public interface SaveKeyPairService <T extends Map> {

    T findAll();

    void saveKeyPair(String clientId, KeyPairModel keyPairModel);

    void delete(String clientId);

    PublicKey getClientPublicKey(String clientId);

    KeyPair getServerKeyPair(String clientId);

    PrivateKey getServerPrivateKey(String clientId);

    PublicKey getServerPublicKey(String clientId);


}
