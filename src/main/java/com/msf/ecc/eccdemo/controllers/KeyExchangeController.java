package com.msf.ecc.eccdemo.controllers;


import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.models.KeyPairObj;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import com.msf.ecc.eccdemo.services.imp.GenerateKeyPairServiceImp;
import com.msf.ecc.eccdemo.services.imp.SaveKeyPairServiceImp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.PublicKey;

@RestController
public class KeyExchangeController {

    private final GenerateKeyPairServiceImp generateKeyPairService;
    private final SaveKeyPairServiceImp saveKeyPairService;

    public KeyExchangeController(GenerateKeyPairServiceImp generateKeyPairService, SaveKeyPairServiceImp saveKeyPairService) {
        this.generateKeyPairService = generateKeyPairService;
        this.saveKeyPairService = saveKeyPairService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/initiateKeyExchange")
    public BaseModel intiateKeyMessage(@RequestBody BaseModel baseModel) throws Exception {

        String publicKey = baseModel.getPublicKey();
        System.out.println(" reading the public key from client --- " + publicKey);

        // Generating the keypair for the server.
        PublicKey clientPublicKey = generateKeyPairService.generatePublicKeyFromString(publicKey);

        // generate server side keypair
        KeyPair keyPair = generateKeyPairService.generateKeyPair();

        System.out.println("fetching all key pairs --- " + saveKeyPairService.findAll() + " --- list ends -- ");

        // TODO save the pair using @keypairObj
        KeyPairObj keyPairObj = new KeyPairObj(clientPublicKey, keyPair);
        saveKeyPairService.saveKeyPair(baseModel.getPublicKey(), keyPairObj);

        // return the server public key
        return generateKeyPairService.getPublicKeyString(keyPair);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/testService")
    public BaseModel keyGenerationTest() throws Exception {

        GenerateKeyPairService generateKeyPairService = new GenerateKeyPairServiceImp();
        KeyPair keyPair = generateKeyPairService.generateKeyPair();

        System.out.println(saveKeyPairService.findAll());

        return generateKeyPairService.getPublicKeyString(keyPair);
    }
}
