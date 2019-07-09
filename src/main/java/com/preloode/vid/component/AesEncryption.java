package com.preloode.vid.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.spec.KeySpec;
import java.util.Base64;


@Component
public class AesEncryption {


    @Autowired
    private Log log;

    private String key;

    private String salt;


    public String getKey() {

        this.setKey("MRPreloode@1989");

        return this.key;

    }


    public void setKey(String key) {

        this.key = key;

    }


    public String getSalt() {

        this.setSalt("MRPreloode@1989");

        return this.salt;

    }


    public void setSalt(String salt) {

        this.salt = salt;

    }


    public String decrypt(HttpServletRequest request, String string) {

        String result = "";

        try {

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(this.getKey().toCharArray(), this.getSalt().getBytes(), 65536, 256);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            byte[] iv = {0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0};
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            result = new String(cipher.doFinal(Base64.getDecoder().decode(string)));

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public String encrypt(HttpServletRequest request, String string) {

        String result = "";

        try {

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(this.getKey().toCharArray(), this.getSalt().getBytes(), 65536, 256);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            byte[] iv = {0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0};
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            result = Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


}
