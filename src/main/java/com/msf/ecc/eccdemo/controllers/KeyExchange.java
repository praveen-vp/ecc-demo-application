package com.msf.ecc.eccdemo.controllers;


import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.services.GenerateKeyPair;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RestController
public class KeyExchange {


    @RequestMapping("/initiateKeyExchange/{key}")
    public String intiateKeyMessage(@PathVariable String key) throws Exception {

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

        System.out.println("public key generated from bytes : " + publicKey2.toString());

        return publicKey2.toString();

    }


    @RequestMapping(method = RequestMethod.POST, value = "/initiateKeyExchange")
    public BaseModel intiateKeyMessage() throws Exception {

        GenerateKeyPair generateKeyPair = new GenerateKeyPair();
        KeyPair keyPair = generateKeyPair.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String formatPublicKey = publicKey.getFormat();

        System.out.println("Public Key : " +
                Base64.getEncoder().encodeToString(publicKeyBytes));

        return new BaseModel(Base64.getEncoder().encodeToString(publicKeyBytes));
    }
}
