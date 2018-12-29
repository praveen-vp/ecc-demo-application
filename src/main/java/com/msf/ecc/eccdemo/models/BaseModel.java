package com.msf.ecc.eccdemo.models;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseModel {

    protected String publicKey;

    public BaseModel() {
    }

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
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
