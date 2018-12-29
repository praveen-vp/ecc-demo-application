package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.models.BaseModel;
import com.msf.ecc.eccdemo.services.GenerateKeyPairService;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class GenerateKeyPairServiceImp implements GenerateKeyPairService {

    public static final String algorithm = "EC";
    public static final String provider = "SunEC";
    public static final String stdName = "secp192r1";

    public static void main(String args[]) {

        try {

            GenerateKeyPairServiceImp gkpService = new GenerateKeyPairServiceImp();
            KeyPair keyPair = gkpService.generateKeyPair();

            System.out.println("private Key --- " + keyPair.getPrivate().toString());
            System.out.println("public Key --- " + keyPair.getPublic().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KeyPair generateKeyPair()
            throws NoSuchAlgorithmException,
            NoSuchProviderException,
            InvalidAlgorithmParameterException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm, provider);
        ECGenParameterSpec ecsp = new ECGenParameterSpec(stdName);
        kpg.initialize(ecsp);

        KeyPair kp = kpg.genKeyPair();
        return kp;
    }

    public BaseModel getPublicKeyString(KeyPair keyPair) {

        PublicKey publicKey = keyPair.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();

        /*
         String publicKeyFormat = publicKey.getFormat();

        System.out.println("publicKeyFormat --- " + publicKeyFormat);
        System.out.println("public key --- " + publicKey);
        System.out.println("Public Key : " +
                Base64.getEncoder().encodeToString(publicKeyBytes));
       */
        return new BaseModel(Base64.getEncoder().encodeToString(publicKeyBytes));
    }


    public BaseModel getPublicKeyString(PublicKey publicKey) {

        /*
        String publicKeyFormat = publicKey.getFormat();

        System.out.println("publicKeyFormat --- " + publicKeyFormat);
        System.out.println("public key --- " + publicKey);
        System.out.println("Public Key : " +
                Base64.getEncoder().encodeToString(publicKeyBytes));
       */
        return new BaseModel(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }

    public PublicKey generatePublicKeyFromString(String publicKeyStringEncoded) throws Exception {

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStringEncoded));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        // System.out.println("public key generated from bytes : " + publicKey.toString());

        return publicKey;
    }
}