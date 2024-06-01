package best.actinium.util.io;

import net.minecraft.client.Minecraft;
import org.lwjglx.Sys;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Nyghtfull
 * other than the getNewHashedHWID function which was made by litiane
 * this is made only for fun and its p easy to crack if i release this
 * and the code is pretty bad
 * @since 1/7/2024
 */
public class BackendUtil {
    private static boolean idk = false;
    final static String BACKEND_URL = "https://raw.githubusercontent.com/Nyghtfullfr/Uhh-Down-in-Ohio/main/data.txt";
    public final static String WEBHOOK_URL = "https://discord.com/api/webhooks/1199739005076521020/6cP6g5IvzJtzd5Uh6RzLslRaRSekGqSD_kuDcC4cmbrjckUxuo-NLubVAZuDNuaPwnnd";
    public final static String WEBHOOK_IRC_URL = "https://discord.com/api/webhooks/1210576284674166804/LrBVR1LEwpj6ABB8JxsTs2l4JThj8n5lL5Cg8iGAC0hK3Zdn6NRuJpDuMIkwU2aKmz3U";

    public static String readFromUrl(String url) throws IOException {
        StringBuilder content = new StringBuilder();
        try {
            URL urlObj = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                content.append(inputLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
        }

        return content.toString();
    }

    public static void crashSend() {
        if(!idk) {
            try {
                BackendUtil.sendMessage(WEBHOOK_URL,"Could Not Find HWID in Backend , Sending Info" +
                        " ```System Name: " + System.getProperty("user.name") +
                        " ,OS Name: " + System.getProperty("os.name") + " ,IPv4: " + getIPv4() + " ,IPv6: "
                        + getIPv6() + "```");
                idk = true;
            } catch (Exception e) {
                crash();
            }
        }

        System.exit((int) (Minecraft.getSystemTime() % 1000L));
    }

    public static void crash()  {
        System.exit((int) (Minecraft.getSystemTime() % 1000L));
    }

    public static String getIPv4() {
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Inet6Address getIPv6Addresses(InetAddress[] addresses) {
        try {
            for (InetAddress addr : addresses) {
                if (addr instanceof Inet6Address) {
                    return (Inet6Address) addr;
                }
            }
        } catch (NullPointerException e) {
          //  System.exit(1);
        }
        return null;
    }

    public static String getIPv6() {
        try {
            return getIPv6Addresses(InetAddress.getAllByName("www.google.at")).toString().
                    replace("www.google.at/", "");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(String url1, String message) {
        try {
            URL url = new URL(url1);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = "{\"content\":\"" + message + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            System.out.println(connection.getResponseCode() + "Printed");

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBackendData() throws IOException {
        return readFromUrl(BACKEND_URL);
    }

    public static String getBackendDataContaining(String searchString) throws IOException {
        String[] lines = readFromUrl(BACKEND_URL).split("\\n");

        for (String line : lines) {
            if (line.contains(searchString)) {
                return line;
            }
        }

        return "";
    }

    public static boolean isListed() throws IOException {
        return getBackendData().contains(getNewHashedHWID());
    }

    public static String getUserName() throws IOException {
        return getBackendDataContaining(getNewHashedHWID()).replace(getNewHashedHWID() + ":","");
    }

    public static String getNewHashedHWID() {
        try {
            String hwid = null;

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("SHA-256");
                String hwidString = System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + Runtime.getRuntime().availableProcessors() + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("NUMBER_OF_PROCESSORS");
                byte[] hashBytes = md.digest(hwidString.getBytes(StandardCharsets.UTF_8));
                hwid = Arrays.toString(hashBytes);
            } catch (Exception var9) {
            }

            md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(hwid != null ? hwid.getBytes() : new byte[0]);
            StringBuilder hexStringBuilder = new StringBuilder();
            byte[] var4 = hashedBytes;
            int var5 = hashedBytes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte b = var4[var6];
                String hex = String.format("%02x", b);
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException var10) {
            return "######################";
        }
    }
}