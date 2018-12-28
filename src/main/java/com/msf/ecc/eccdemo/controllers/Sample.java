package com.msf.ecc.eccdemo.controllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class Sample {


    private static final String String = null;

    public static void main(String[] arg) {

        String pub = generateKeys("DSA", 1024);


        try {


            ServerSocket socketConnection = new ServerSocket(11111);

            System.out.println("Server Waiting");

            Socket pipe = socketConnection.accept();

            ObjectInputStream serverInputStream = new
                    ObjectInputStream(pipe.getInputStream());

            ObjectOutputStream serverOutputStream = new
                    ObjectOutputStream(pipe.getOutputStream());

            // Server Read Operations


            String Pub1 = (String) serverInputStream.readObject();


            // Server Write Operations

//            serverOutputStream.writeObject(ran1);
            serverOutputStream.writeObject(pub);


            serverInputStream.close();
            serverOutputStream.close();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String generateKeys(String keyAlgorithm, int numBits) {

        try {
            // Get the public/private key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyAlgorithm);
            keyGen.initialize(numBits);
            KeyPair keyPair = keyGen.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            System.out.println("\n" + "Generating key/value pair using " + privateKey.getAlgorithm() + " algorithm");

            // Get the bytes of the public and private keys
            byte[] privateKeyBytes = privateKey.getEncoded();
            byte[] publicKeyBytes = publicKey.getEncoded();

            // Get the formats of the encoded bytes
            String formatPrivate = privateKey.getFormat(); // PKCS#8
            String formatPublic = publicKey.getFormat(); // X.509

//            String pv = String.valueOf(privateKeyBytes);
//
//            System.out.println("Private Key : " + Base64.encode(pv));
//
//            String pb = String.valueOf(publicKeyBytes);
//            System.out.println("Public Key : " + Base64.encode(pb));
            //  return pb;

            // System.out.println("Private Key : " + Base64.encode(String.valueOf(privateKeyBytes)));
            // System.out.println("Public Key : " + Base64.encode(String.valueOf(publicKeyBytes)));

            // The bytes can be converted back to public and private key objects
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);

            // The original and new keys are the same
            // System.out.println("  Are both private keys equal? " + privateKey.equals(privateKey2));
            // System.out.println("  Are both public keys equal? " + publicKey.equals(publicKey2));

        } catch (InvalidKeySpecException specException) {
            System.out.println("Exception");
            System.out.println("Invalid Key Spec Exception");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception");
            System.out.println("No such algorithm: " + keyAlgorithm);
        }
        return null;
    }
}
