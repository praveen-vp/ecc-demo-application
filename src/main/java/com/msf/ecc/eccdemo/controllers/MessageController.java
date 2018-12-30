package com.msf.ecc.eccdemo.controllers;

import com.msf.ecc.eccdemo.models.MessageModel;
import com.msf.ecc.eccdemo.services.PrivateKeyEncryptionService;
import com.msf.ecc.eccdemo.services.imp.AESEncryptionServiceImp;
import com.msf.ecc.eccdemo.services.imp.KeyAgreementServiceImp;
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

        PrivateKey serverPrivateKey = this.saveKeyPairService.getServerPrivateKey(messageModel.getPublicKey());

        if (serverPrivateKey == null) {
            return new MessageModel("500", "Invalid Request");
        }

        PublicKey clientPublicKey = saveKeyPairService.getClientPublicKey(messageModel.getPublicKey());
        PrivateKeyEncryptionService encryptionService = new AESEncryptionServiceImp(
                new KeyAgreementServiceImp(serverPrivateKey, clientPublicKey).generateSecretKey()
        );

        String decryptedMsg = encryptionService.decrypt(messageModel.getMessage());
        System.out.println("server decrypts the message as --- " + decryptedMsg);

        // server sending a encrypted message back to client
        String serverMessage = "decrypted your message successfully -- " + decryptedMsg;
        System.out.println(" serverMessage -- " + serverMessage);

        serverMessage = encryptionService.encrypt(serverMessage);
        System.out.println("server encrypted message -- " + serverMessage);

        return new MessageModel(messageModel.getPublicKey(), serverMessage);
    }
}