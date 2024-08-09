package dev.excellent.impl.util.other;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class HWID {
    private final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public String getHWID() {
        return bytesToHex(generateHWID());
    }

    public byte[] generateHWID() {
        try {
            MessageDigest hash = MessageDigest.getInstance("SHA-256");

            String hwid = System.getProperty("os.name")
                    + System.getProperty("os.arch")
                    + System.getProperty("os.version")
                    + Runtime.getRuntime().availableProcessors()
                    + System.getenv("PROCESSOR_IDENTIFIER")
                    + System.getenv("PROCESSOR_ARCHITECTURE")
                    + System.getenv("NUMBER_OF_PROCESSORS");
            return hash.digest(hwid.getBytes());
        } catch (NoSuchAlgorithmException exception) {
            throw new Error("Algorithm not found.", exception);
        }

    }

    public String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[value >>> 4];
            hexChars[i * 2 + 1] = hexArray[value & 0x0F];
        }
        return new String(hexChars);
    }

    public byte[] hexStringToByteArray(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}