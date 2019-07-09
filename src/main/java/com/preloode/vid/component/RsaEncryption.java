package com.preloode.vid.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Component
public class RsaEncryption {


    @Autowired
    private Log log;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private String privateKeyString;

    private String publicKeyString;


    public PrivateKey getPrivateKey() {

        return this.privateKey;

    }


    public void setPrivateKey(HttpServletRequest request, String privateKey) {

        try {

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = keyFactory.generatePrivate(keySpec);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

    }


    public PublicKey getPublicKey() {

        return this.publicKey;

    }


    public void setPublicKey(HttpServletRequest request, String publicKey) {

        try {

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(keySpec);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

    }


    public String getPrivateKeyString() {

        String privateKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI2Ex6JriAbY1NFM7YyjboPQrXiBvGa7uAyZxUOCl1CjGNhXU73Q0k1Gjw66ox3vV2z+n9ZYclnfgpS4xDGv3qmXvnmLSQ3oJ3T/ovZUNtlINV7Tg2wLQhiyfxqEfHnhEM8DqfGgqSRMGuv+TIJpRpYAWcGPh5tlLoloGAzqK1H5AgMBAAECgYBuGhvndZnDGfeZTyyR+qUE0Nnlo6ock3fB9fDPcU6gYgP2bRqt10WG8ZpjbzlxFMED/6YDcZSl74r8gjPCJH4qc7tPthjxMkEwXldv2ce8ITnCw0k6EKXnf0cgNOvMMxFAvYFJS3hlhHUsu1CgbBqpV3/j96Z8dpYiJfISyY40wQJBAMFTZyozK4C/WvYjcnmb+SjsYqxMxQGf98EvWQc3LV5PTfvKTRAZGEpogs7QdHaStj+B/1hepYzIK0rCZDI1ggUCQQC7ZcXLiwNN3nVq9m++nHPUOY+ooSBpK0ac2USNQcI0KcbYlnIcrKPucZi4SMNvlZTVD3OxUEMzQ1vnsP5a485lAkEAkl0jgOaGrA2zvx/tHjbA94On3MyuZmGHAGJpY7YZigo+fz+VAngNVOA1EIDve4ntor035d6aNbCiXhI4K28pRQJAJwrfGLcPqyuL3wsU4OuLnk2XxpQ7Qm4HuinyBFQM9/00Nm+xupOlW8pC/TH7tcW+Sl17xxdnyMhh33WaqKkJmQJAQ7ed8jDpJoRznj7tErcSApQFeoqcm8suIaWuA/nd1xGDJbjmqHoIboU4CK0ehYOWswzSGpbFERIqXhHU1BV7gA==";
        this.setPrivateKeyString(privateKeyString);

        return this.privateKeyString;

    }


    public void setPrivateKeyString(String privateKeyString) {

        this.privateKeyString = privateKeyString;

    }


    public String getPublicKeyString() {

        String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNhMeia4gG2NTRTO2Mo26D0K14gbxmu7gMmcVDgpdQoxjYV1O90NJNRo8OuqMd71ds/p/WWHJZ34KUuMQxr96pl755i0kN6Cd0/6L2VDbZSDVe04NsC0IYsn8ahHx54RDPA6nxoKkkTBrr/kyCaUaWAFnBj4ebZS6JaBgM6itR+QIDAQAB";
        this.setPublicKeyString(publicKeyString);

        return this.publicKeyString;

    }


    public void setPublicKeyString(String publicKeyString) {

        this.publicKeyString = publicKeyString;

    }


    public String decrypt(HttpServletRequest request, String string) {

        String result = "";

        this.setPrivateKey(request, this.getPrivateKeyString());

        try {

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.getPrivateKey());

            byte[] stringByte = Base64.getDecoder().decode(string.getBytes());

            result = new String(cipher.doFinal(stringByte));

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public String encrypt(HttpServletRequest request, String string) {

        String result = "";

        this.setPublicKey(request, this.getPublicKeyString());

        try {

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.getPublicKey());

            byte[] stringByte = cipher.doFinal(string.getBytes());
            result = Base64.getEncoder().encodeToString(stringByte);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


    public Map<String, Object> generateKeyPair(HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>() {

            {
                put("privateKey", "");
                put("publicKey", "");
                put("result", false);
            }

        };

        try {

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair pair = keyGen.generateKeyPair();
            result.put("privateKey", Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
            result.put("publicKey", Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
            result.put("result", true);

        } catch (Exception exception) {

            this.log.exception(request, exception);

        }

        return result;

    }


}
