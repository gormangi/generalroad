package com.generalroad.shop.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CreateUuid {

    public static String createShortUuid() {
        String uuidString = UUID.randomUUID().toString();
        byte[] uuidStringBytes = uuidString.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(uuidStringBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<5;i++) {
            sb.append(String.format("%02x", hashBytes[i]));
        }
        return sb.toString();
    }
}
