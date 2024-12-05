package com.eric.onlineresourcemanagementsys.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class EncryptionUtil {
    public static byte[] encryptPassword(String password, SecretKey key) {
        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            System.out.println("Encrypted Password: " + Arrays.toString(encrypted)); // Debug log
            return encrypted;
        } catch (Exception e) {
            System.out.println("Error encrypting password: " + e.getMessage());
            return null;
        }
    }

    public static boolean verifyPassword(byte[] encryptedPassword, String inputPassword, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encryptedPassword);
            System.out.println("Decrypted Password: " + new String(decrypted)); // Debug log
            return Arrays.equals(decrypted, inputPassword.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            System.out.println("Error decrypting password: " + e.getMessage());
            return false;
        }

    }
}
