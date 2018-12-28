package com.msf.ecc.eccdemo;

import com.msf.ecc.eccdemo.services.EncryptionService;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import com.msf.ecc.eccdemo.services.imp.EncryptionServiceImp;
import com.msf.ecc.eccdemo.services.imp.GenerateKeyPairServiceImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
public class EccdemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(EccdemoApplication.class, args);

        try {

            GenerateKeyPairService generateKeyPairService = new GenerateKeyPairServiceImp();
            KeyPair keyPairServer = generateKeyPairService.generateKeyPair();
            KeyPair keyPairClient = generateKeyPairService.generateKeyPair();

            System.out.println("public key for client server : " + keyPairClient.getPublic());

            byte[] publicKeyBytes = keyPairClient.getPublic().getEncoded();
            String formatPublicKey = keyPairClient.getPublic().getFormat();

            System.out.println("Public Key bytes for client server : " +
                    Base64.getEncoder().encodeToString(publicKeyBytes));

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

            System.out.println("public key generated from bytes : " + publicKey2.toString());


            String message = "this is a secret message, hide this :)";
            System.out.println("secret message -- " + message);

            /*
             * Generating the secret in server side
             */
            EncryptionService eccKeyAgreement = new EncryptionServiceImp(keyPairServer.getPrivate(), keyPairClient.getPublic(), message);
            String enCryptedMsg = eccKeyAgreement.doEncryption();

            System.out.println("encrypted message --  " + enCryptedMsg);

            /*
             *Generating the secret key in client side
             */
            eccKeyAgreement = new EncryptionServiceImp(keyPairClient.getPrivate(), keyPairServer.getPublic(), message);
            String decryptedMsg = eccKeyAgreement.doDecryption(enCryptedMsg);
            System.out.println("decryptedMsg -- " + decryptedMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

