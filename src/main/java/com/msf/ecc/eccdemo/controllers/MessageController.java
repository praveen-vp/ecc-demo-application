package com.msf.ecc.eccdemo.controllers;

import com.msf.ecc.eccdemo.models.MessageModel;
import com.msf.ecc.eccdemo.services.EncryptionService;
import com.msf.ecc.eccdemo.services.imp.EncryptionServiceImp;
import com.msf.ecc.eccdemo.services.imp.SaveKeyPairServiceImp;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

@RestController
public class MessageController {

    private final SaveKeyPairServiceImp saveKeyPairService;

    public MessageController(SaveKeyPairServiceImp saveKeyPairService) {
        this.saveKeyPairService = saveKeyPairService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/sayHello")
    public MessageModel receiveClientMessage(@RequestBody MessageModel messageModel)
            throws InvalidKeyException, NoSuchAlgorithmException {

        System.out.println(messageModel);

        PrivateKey serverPrivateKey = this.saveKeyPairService.getServerPrivateKey(messageModel.getPublicKey());
        System.out.println(serverPrivateKey);

        PublicKey clientPublicKey = saveKeyPairService.getClientPublicKey(messageModel.getPublicKey());
        EncryptionService encryptionService = new EncryptionServiceImp(serverPrivateKey, clientPublicKey);

        String decryptedMsg = encryptionService.doDecryption(messageModel.getMessage());
        System.out.println("server decrypts the message as --- " + decryptedMsg);

        // server sending a encrypted message back to client
        String serverMessage = "decrypted your message successfully -- "  + decryptedMsg;

        System.out.println(" serverMessage -- "  + serverMessage);

        serverMessage = encryptionService.doEncryption(serverMessage);
        System.out.println("server encrypted message -- " + serverMessage);

        return new MessageModel(messageModel.getPublicKey(), serverMessage);
    }
}