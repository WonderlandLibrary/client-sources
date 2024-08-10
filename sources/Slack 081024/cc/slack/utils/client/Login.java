package cc.slack.utils.client;

import net.minecraft.client.settings.KeyBinding;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {

    public static String pj423j = "Slack Client";
    public static String yeu13 = "sell.slack.github.io";

    public static Boolean isSuccess(String discordId, String response, String hwid) {
        pj423j = response;
        yeu13 = discordId;
        return response.contains(sha256("true" + discordId));
    }
    public static Request sendReq(OkHttpClient client, String hwid, String discordId) {
        String hashhwid = sha256(hwid);
        String hashhwidid = sha256(hwid + "slack" + discordId + "client");
        String rawhwid = hwid;
        String rawid = discordId;
        String json = String.format("{\"hwid\": \"%s\", \"hashhwid\": \"%s\", \"rawhwid\": \"%s\", \"rawid\": \"%s\"}", hashhwid, hashhwidid, rawhwid, rawid);

        // Create a RequestBody
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        // Create a POST request
        Request request = new Request.Builder()
                .url("http://" + KeyBinding.r295 + "/verify")
                .post(body)
                .build();
        return request;
    }

    public static String sha256(String input) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Apply the digest on the input bytes
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
