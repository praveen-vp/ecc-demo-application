package com.msf.ecc.eccdemo.services.imp;

import com.msf.ecc.eccdemo.services.AESEncryptionService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class AESEncryptionServiceImp implements AESEncryptionService {

    private static final String characterEncoding = "UTF-8";
    private static final String cipherTransformation = "AES/ECB/PKCS5Padding";
    private static final String aesEncryptionAlgorithm = "AES";
    private static final String messageDigest = "SHA-256";

    private SecretKeySpec secretKey;
    private byte[] key;

    public String encrypt(String strToEncrypt, String secret) {

        try {

            setKey(secret);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(characterEncoding)));

        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
            throw new RuntimeException("ERROR while Encrypting");
        }
    }

    public String decrypt(String strToDecrypt, String secret) {

        try {

            setKey(secret);
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR while Encrypting");
        }
    }


    public void setKey(String myKey) {

        try {

            key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance(messageDigest);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, aesEncryptionAlgorithm);

            System.out.println(sha.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}