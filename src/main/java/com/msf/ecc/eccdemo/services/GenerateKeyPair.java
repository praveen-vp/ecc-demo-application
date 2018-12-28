package com.msf.ecc.eccdemo.services;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class GenerateKeyPair {

    public static void main(String args[]) {

        try {

            GenerateKeyPair gkp = new GenerateKeyPair();
            KeyPair keyPair = gkp.generateKeyPair();

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

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "SunEC");
        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp192r1");
        kpg.initialize(ecsp);

        KeyPair kp = kpg.genKeyPair();
/*
        PrivateKey privateKey = kp.getPrivate();
        PublicKey publicKey = kp.getPublic();

        System.out.println(privateKey.toString());
        System.out.println(publicKey.toString());
*/
        return kp;
    }

}