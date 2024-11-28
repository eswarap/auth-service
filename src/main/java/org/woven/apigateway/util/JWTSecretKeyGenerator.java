package org.woven.apigateway.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class JWTSecretKeyGenerator {
    public static void main(String[] args) {
        String secretKeyBase64 = JWTSecretKeyGenerator.generateSecretKey("AES", 256);
        log.info("JWT Secret Key: {} " ,secretKeyBase64);
    }
    public static String generateSecretKey(final String algorithm, final int keySize) {
        String secretKeyBase64 = null;
        try {
            // Generate a secret key using AES algorithm with 256 bits of key size
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(keySize);
            // Generate the secret key
            byte[] secretKey = keyGenerator.generateKey().getEncoded();
            // Convert the secret key to Base64
            secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey);
        } catch (NoSuchAlgorithmException e) {
            log.error("no algorithm found", e);
        }
        return secretKeyBase64;
    }
}
