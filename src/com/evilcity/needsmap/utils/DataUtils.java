package com.evilcity.needsmap.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DataUtils {
    private static MessageDigest sha256;
    public static byte[] hash(String password)  {
        if (sha256 == null) {
            try {
                sha256 = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        byte[] passBytes = password.getBytes();
        byte[] passHash = sha256.digest(passBytes);
        return passHash;
    }
    public static String stringHash(String password) {
        return Base64.getUrlEncoder().encodeToString(hash(password));
    }
}
