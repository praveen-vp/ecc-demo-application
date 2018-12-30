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
public class GenerateECKeyPairServiceImp implements GenerateKeyPairService {

    public static final String algorithm = "EC";
    public static final String provider = "SunEC";
    public static final String stdName = "secp192r1";

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

    public BaseModel getPublicKeyString(PublicKey publicKey) {

        return new BaseModel(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }

    public PublicKey generatePublicKeyFromString(String publicKeyStringEncoded) throws Exception {

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyStringEncoded));
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        return publicKey;
    }
}