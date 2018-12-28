package com.msf.ecc.eccdemo.models;

public class BaseModel {

    String publicKey;

    public BaseModel(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "publicKey='" + publicKey + '\'' +
                '}';
    }
}
