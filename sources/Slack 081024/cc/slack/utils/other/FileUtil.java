package cc.slack.utils.other;

import cc.slack.utils.client.IMinecraft;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.Util;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil implements IMinecraft {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject readJsonFromFile(String path) {
        try {
            return GSON.fromJson(new FileReader(path), JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject readJsonFromResourceLocation(ResourceLocation resourceLocation) {
        try (InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return GSON.fromJson(stringBuilder.toString(), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJsonToFile(JsonObject json, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            GSON.toJson(json, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void showURL(String url) {
        try {
            if (Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void showFolder(String path) {
        File folder =  new File(Minecraft.getMinecraft().mcDataDir, path);

        // Check if Desktop is supported on the current platform
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                // Check if the folder exists and is a directory
                if (folder.exists() && folder.isDirectory()) {
                    desktop.open(folder);
                    System.out.println("Folder opened successfully.");
                } else {
                    System.out.println("The specified folder does not exist or is not a directory.");
                }
            } catch (IOException e) {
                System.out.println("Failed to open the folder due to an I/O error.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported on the current platform.");
        }
    }

    public static String fetchHwid() {
        try {
            String hwid = "";
            switch (Util.getOSType()) {
                default:
                case WINDOWS:
                    String computerName = System.getenv("COMPUTERNAME");
                    String userName = System.getProperty("user.name");
                    String processorIdentifier = System.getenv("PROCESSOR_IDENTIFIER");
                    String processorLevel = System.getenv("PROCESSOR_LEVEL");

                    // Concatenate the variables
                    hwid = computerName + userName + processorIdentifier + processorLevel;
                    break;
                case OSX:
                    String useName = System.getProperty("user.name");
                    String osName = System.getProperty("os.name");
                    String osVersion = System.getProperty("os.version");
                    String serialNumber = getSerialNumber();

                    // Concatenate the variables
                    hwid = useName + osName + osVersion + serialNumber;
                    break;
                case LINUX:
                    Process cess = Runtime.getRuntime().exec("sudo dmidecode -s system-uuid");
                    cess.getOutputStream().close();
                    BufferedReader der = new BufferedReader(new InputStreamReader(cess.getInputStream()));

                    // Read the HWID
                    hwid = der.readLine().trim();
                    break;
            }
            if (hwid == "") return "f";
            return generateMD5(hwid) + "f";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "f";
    }

    private static String getSerialNumber() {
        String serialNumber = "";
        try {
            // Execute the system_profiler command to get the hardware serial number
            Process process = Runtime.getRuntime().exec("system_profiler SPHardwareDataType | awk '/Serial/ { print $4; }'");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            serialNumber = reader.readLine().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serialNumber;
    }

    private static String generateMD5(String input) throws NoSuchAlgorithmException {
        // Create an MD5 message digest
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

        // Convert byte array into signum representation
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        // Return the hexadecimal string
        return hexString.toString();
    }

}
