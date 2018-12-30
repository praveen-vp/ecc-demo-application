package com.msf.ecc.eccdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.models.MessageModel;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import com.msf.ecc.eccdemo.services.PrivateKeyEncryptionService;
import com.msf.ecc.eccdemo.services.imp.AESEncryptionServiceImp;
import com.msf.ecc.eccdemo.services.imp.GenerateECKeyPairServiceImp;
import com.msf.ecc.eccdemo.services.imp.KeyAgreementServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.PublicKey;

@SpringBootApplication
public class EccDemoApplication {

    public static final String message = "Secret message hide this";
    public static final String SAY_HELLO_URL = "http://localhost:8080/sayHello";
    public static final String KEY_EXCHANGE_URL = "http://localhost:8080/initiateKeyExchange";

    public static void main(String[] args) {

        SpringApplication.run(EccDemoApplication.class, args);

        try {

            // Testing API call
            APICallHelper.makeTestServiceApiCall();

            /*
             * STEP 1 keyPair generation
             */
            System.out.println("<<<<---STEP 1--->>>>");
            // Generating keyPairs for the client.
            GenerateKeyPairService generateKeyPairService = new GenerateECKeyPairServiceImp();
            KeyPair clientKeyPair = generateKeyPairService.generateKeyPair();
            System.out.println("generated client side keypair");

            /*
             * STEP 2 - Key Exchange
             */
            System.out.println("<<<<---STEP 2--->>>>");
            // create public key String for client publicKey
            BaseModel clientPublicKeyStringModel = generateKeyPairService.getPublicKeyString(clientKeyPair.getPublic());
            System.out.println("generated client side publicKey String for key exchange : " + clientPublicKeyStringModel);

            // Share the client's public key with server
            // Server will share Server's public key with client in response
            BaseModel serverPublicKeyStringModel = new ObjectMapper().readValue(
                    APICallHelper.postRequest(KEY_EXCHANGE_URL, clientPublicKeyStringModel.toString()), BaseModel.class);

            String serverPublicKeyString = serverPublicKeyStringModel.getPublicKey();
            System.out.println("server public String key, completing key exchange : " + serverPublicKeyString);

            // create server public key from publicKey String
            PublicKey serverPublicKey = generateKeyPairService.generatePublicKeyFromString(serverPublicKeyString);

            /*
             * STEP 3
             */
            System.out.println("<<<<---STEP 3--->>>>");
            // calculate the secret key using server public key and client private key
            // and encrypt the message
            PrivateKeyEncryptionService encryptionService = new AESEncryptionServiceImp(
                    new KeyAgreementServiceImp(clientKeyPair.getPrivate(),
                            serverPublicKey).generateSecretKey()
            );

            // encrypt the message
            String encryptedMessage = encryptionService.encrypt(message);
            System.out.println(" message encrypted : " + encryptedMessage);

            // creating a message model using client publicKeyString and encrypted message
            MessageModel messageModel = new MessageModel(encryptedMessage);
            messageModel.setPublicKey(clientPublicKeyStringModel.getPublicKey());

            /*
             * STEP 4
             */
            System.out.println("<<<<---STEP 4--->>>>");
            // client send a encrypted message to the server using server's public key
            // server will decrypt it and send the encrypted response using client's public key
            MessageModel messageModelResponse = new ObjectMapper().readValue(
                    APICallHelper.postRequest(SAY_HELLO_URL, messageModel.toString()), MessageModel.class);

            System.out.println("sayHello Response --- " + messageModelResponse.toString());

            // client decrypts the message
            System.out.println(messageModelResponse.getMessage());
            System.out.println("client decrypts server message : -- "
                    + encryptionService.decrypt(messageModelResponse.getMessage()));

            // Testing Map Details
            APICallHelper.makeTestServiceApiCall();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

