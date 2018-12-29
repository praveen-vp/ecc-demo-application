package com.msf.ecc.eccdemo.models;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageModel extends BaseModel {

    private String message;

    public MessageModel() {
    }

    public MessageModel(String message) {
        this.message = message;
    }

    public MessageModel(String publicKey, String message) {
        super(publicKey);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
