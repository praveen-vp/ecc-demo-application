package com.msf.ecc.eccdemo.models;

import java.security.KeyPair;
import java.security.PublicKey;

public class KeyPairObj {

    PublicKey clientPublicKey;
    KeyPair serverKeyPair;

    public KeyPairObj() {
    }

    public KeyPairObj(PublicKey clientPublicKey, KeyPair serverKeyPair) {
        this.clientPublicKey = clientPublicKey;
        this.serverKeyPair = serverKeyPair;
    }

    public PublicKey getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(PublicKey clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }

    public KeyPair getServerKeyPair() {
        return serverKeyPair;
    }

    public void setServerKeyPair(KeyPair serverKeyPair) {
        this.serverKeyPair = serverKeyPair;
    }

    @Override
    public String toString() {
        return "KeyPairObj{" +
                "clientPublicKey=" + clientPublicKey +
                ", serverKeyPair=" + serverKeyPair +
                '}';
    }
}
