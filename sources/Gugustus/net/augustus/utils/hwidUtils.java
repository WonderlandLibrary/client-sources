package net.augustus.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class hwidUtils {
	
	public static String getLocalHWID() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String hwidString = System.getProperty("os.name") +
                    System.getProperty("os.arch") +
                    System.getProperty("os.version") +
                    Runtime.getRuntime().availableProcessors() +
                    System.getenv("PROCESSOR_IDENTIFIER") +
                    System.getenv("PROCESSOR_ARCHITECTURE") +
                    System.getenv("PROCESSOR_ARCHITEW6432") +
                    System.getenv("NUMBER_OF_PROCESSORS");

            byte[] hashBytes = digest.digest(hwidString.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            return "######################";
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexBuilder.append('0');
            }
            hexBuilder.append(hex);
        }
        return hexBuilder.toString();
    }

}
