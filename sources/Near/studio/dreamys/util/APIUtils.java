package studio.dreamys.util;

import studio.dreamys.near;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class APIUtils {
    public static void postLogin() {
        try {
            //setup connection
            HttpURLConnection c = (HttpURLConnection) new URL("http://localhost:80/login").openConnection();
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-type", "application/json");
            c.setDoOutput(true);

            //send req
            String jsonInputString = "{\"hwid\":\"" + APIUtils.getHWID() + "\"}";
            OutputStream os = c.getOutputStream();
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            //receive res
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) response.append(responseLine.trim());
            System.out.println(response.substring(response.indexOf(":") + 1));
            if (response.toString().contains("token")) near.token = response.substring(response.indexOf(":") + 1);
            else PlayerUtils.addMessage("Access denied", "error");
        } catch (Exception e) {
            PlayerUtils.addMessage(Arrays.toString(e.getStackTrace()), "error");
        }
    }

    public static String getHWID() {
        try {
            String toEncrypt = System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuilder hexString = new StringBuilder();
            byte[] byteData = md.digest();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            PlayerUtils.addMessage(Arrays.toString(e.getStackTrace()), "error");
            return "error";
        }
    }
}
