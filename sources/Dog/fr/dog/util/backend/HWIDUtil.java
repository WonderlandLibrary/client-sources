package fr.dog.util.backend;

import dev.liticane.transpiler.Native;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Native
public class HWIDUtil {
    public static String getHWID() {
        String osName = System.getProperty("os.name");
        String prettyName = "Unknown";

        if (osName.toLowerCase().contains("win"))
            prettyName = "Windows";
        else if (osName.toLowerCase().contains("mac") || osName.toLowerCase().contains("darwin"))
            prettyName = "MacOS";
        else if (osName.toLowerCase().contains("nix") || osName.toLowerCase().contains("nux"))
            prettyName = "Linux";

        String rawData = prettyName + System.getProperty("os.arch") + System.getProperty("os.version") +
                Runtime.getRuntime().availableProcessors() + System.getenv("NUMBER_OF_PROCESSORS") +
                System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") +
                System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("USERNAME");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));

            StringBuilder output = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1)
                    output.append('0');

                output.append(hex);
            }

            return output.toString();
        } catch (Throwable throwable) {
            return "?";
        }
    }
}