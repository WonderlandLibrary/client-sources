package com.alan.clients.util.vantage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HWIDUtil {
    static String hwid;

    static {
        hwid = bytesToHex(generateOldHWID()) + "dleotn6oc94kb" +
                getNewHWID();
    }

    public static String getHWID() {
        return hwid;
    }

    private static String getNewHWID() {
        String motherboardSerial = getMotherboardSerialNumber();
        String processorId = getProcessorId();

        if (OSUtil.getOS().equals(OperatingSystem.WINDOWS) && motherboardSerial.length() >= 5 && processorId.length() >= 5) {
            return new String(Base64.getEncoder().encode((motherboardSerial + "_"
                    + processorId).getBytes())).replaceAll("==", "");
        } else {
            return bytesToHex(generateOldHWID());
        }
    }

    private static String getMotherboardSerialNumber() {
        return executeWMIQuery(new String[]{"cmd.exe", "/c", "wmic baseboard get serialnumber"});
    }

    private static String getProcessorId() {
        return executeWMIQuery(new String[]{"cmd.exe", "/c", "wmic cpu get processorid"});
    }

    private static String executeWMIQuery(String[] command) {
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (headerSkipped && !line.trim().isEmpty()) {
                    result.append(line.trim());
                    break;
                }
                headerSkipped = true;
            }
            reader.close();
        } catch (Exception e) {
            return "unknown";
        }
        return result.toString();
    }

    private static byte[] generateOldHWID() {
        try {
            OperatingSystem os = OSUtil.getOS();
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            String o;

            if (os.equals(OperatingSystem.WINDOWS)) {
                o = "VANTAGE_" + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version")
                        + System.getProperty("user.name") + System.getProperty("user.home")
                        + System.getenv("NUMBER_OF_PROCESSORS");
            } else {
                o = "VANTAGE_" + os.getNiceName() + System.getProperty("os.arch")
                        + Runtime.getRuntime().availableProcessors() + System.getenv("PROCESSOR_IDENTIFIER")
                        + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432")
                        + System.getenv("NUMBER_OF_PROCESSORS") + System.getenv("COMPUTERNAME") + System.getenv("os")
                        + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE")
                        + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("HOME")
                        + System.getenv("HOSTNAME") + System.getenv("SHELL") + System.getenv("LOGNAME") + System.getenv("USERNAME");
            }

            return hash.digest(o.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new Error("HWIDException: ", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}