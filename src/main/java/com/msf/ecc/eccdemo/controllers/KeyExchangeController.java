package com.msf.ecc.eccdemo.controllers;


import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.models.KeyPairModel;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import com.msf.ecc.eccdemo.services.SaveKeyPairService;
import com.msf.ecc.eccdemo.services.imp.GenerateECKeyPairServiceImp;
import com.msf.ecc.eccdemo.services.imp.SaveKeyPairServiceImp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.PublicKey;

@RestController
public class KeyExchangeController {

    private final GenerateKeyPairService generateKeyPairService;
    private final SaveKeyPairService saveKeyPairService;

    public KeyExchangeController(GenerateECKeyPairServiceImp generateKeyPairService, SaveKeyPairServiceImp saveKeyPairService) {
        this.generateKeyPairService = generateKeyPairService;
        this.saveKeyPairService = saveKeyPairService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/initiateKeyExchange")
    public BaseModel intiateKeyMessage(@RequestBody BaseModel baseModel) throws Exception {

        String clientPublicKeyString = baseModel.getPublicKey();

        // regenerating the client side public key.
        PublicKey clientPublicKey = generateKeyPairService.generatePublicKeyFromString(clientPublicKeyString);

        // generate server side keypair
        KeyPair serverKeyPair = generateKeyPairService.generateKeyPair();

        KeyPairModel keyPairModel = new KeyPairModel(clientPublicKey, serverKeyPair);

        if (!saveKeyPairService.findAll().containsKey(clientPublicKeyString))
            saveKeyPairService.saveKeyPair(baseModel.getPublicKey(), keyPairModel);

        BaseModel serverPublicKeyStringModel = generateKeyPairService.getPublicKeyString(serverKeyPair.getPublic());
        System.out.println("server public key String --- " + serverPublicKeyStringModel);

        return serverPublicKeyStringModel;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/testService")
    public BaseModel keyGenerationTest() throws Exception {

        GenerateKeyPairService generateKeyPairService = new GenerateECKeyPairServiceImp();
        KeyPair keyPair = generateKeyPairService.generateKeyPair();

        System.out.println("reading map details --- " + saveKeyPairService.findAll());

        return generateKeyPairService.getPublicKeyString(keyPair.getPublic());
    }
}
