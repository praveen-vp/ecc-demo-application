package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.models.KeyPairModel;
import com.msf.ecc.eccdemo.services.SaveKeyPairService;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

@Service
public class SaveKeyPairServiceImp implements SaveKeyPairService {

    protected HashMap<String, KeyPairModel> keyPairMap = new HashMap<>();

    public HashMap findAll() {
        return keyPairMap;
    }

    @Override
    public void saveKeyPair(String clientId, KeyPairModel keyPairModel) {
        keyPairMap.put(clientId, keyPairModel);
    }

    @Override
    public void delete(String clientId) {
        keyPairMap.remove(clientId);
    }

    @Override
    public PublicKey getClientPublicKey(String clientId) {
        return keyPairMap.containsKey(clientId) ? keyPairMap.get(clientId).getClientPublicKey() : null;
    }

    @Override
    public KeyPair getServerKeyPair(String clientId) {
        return keyPairMap.containsKey(clientId) ? keyPairMap.get(clientId).getServerKeyPair() : null;
    }

    @Override
    public PrivateKey getServerPrivateKey(String clientId) {
        return keyPairMap.containsKey(clientId) ? keyPairMap.get(clientId).getServerKeyPair().getPrivate() : null;
    }

    @Override
    public PublicKey getServerPublicKey(String clientId) {
        return keyPairMap.containsKey(clientId) ? keyPairMap.get(clientId).getServerKeyPair().getPublic() : null;
    }
}
