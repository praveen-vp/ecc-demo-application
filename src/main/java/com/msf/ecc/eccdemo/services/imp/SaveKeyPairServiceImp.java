package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.models.KeyPairObj;
import com.msf.ecc.eccdemo.services.SaveKeyPairService;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

@Service
public class SaveKeyPairServiceImp implements SaveKeyPairService {

    protected HashMap<String, KeyPairObj> keyPairMap = new HashMap<>();

    public HashMap findAll() {
        return keyPairMap;
    }

    @Override
    public void saveKeyPair(String clientId, KeyPairObj keyPairObj) {
        keyPairMap.put(clientId, keyPairObj);
    }

    @Override
    public void delete(String clientId) {
        keyPairMap.remove(clientId);
    }

    @Override
    public PublicKey getClientPublicKey(String clientId) {
        return keyPairMap.get(clientId).getClientPublicKey();
    }

    @Override
    public KeyPair getServerKeyPair(String clientId) {
        return keyPairMap.get(clientId).getServerKeyPair();
    }

    @Override
    public PrivateKey getServerPrivateKey(String clientId) {
        return keyPairMap.get(clientId).getServerKeyPair().getPrivate();
    }

    @Override
    public PublicKey getServerPublicKey(String clientId) {
        return keyPairMap.get(clientId).getServerKeyPair().getPublic();
    }
}
