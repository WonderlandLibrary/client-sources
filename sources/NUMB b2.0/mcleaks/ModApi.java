package mcleaks;

import java.util.concurrent.*;
import javax.net.ssl.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;

public class ModApi
{
    public static final ExecutorService EXECUTOR_SERVICE;
    private static final String API_URL = "http://auth.mcleaks.net/v1/";
    private static final Gson gson;
    
    static {
        EXECUTOR_SERVICE = new ForkJoinPool();
        gson = new Gson();
    }
    
    public static void redeem(final String token, final Callback<Object> callback) {
        final String url = "http://auth.mcleaks.net/v1/redeem";
        ModApi.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                final URLConnection connection = preparePostRequest("http://auth.mcleaks.net/v1/redeem", "{\"token\":\"" + token + "\"}");
                if (connection == null) {
                    callback.done("An error occured! [R1]");
                    return;
                }
                final Object o = getResult(connection);
                if (o instanceof String) {
                    callback.done(o);
                    return;
                }
                final JsonObject jsonObject = (JsonObject)o;
                if (!jsonObject.has("mcname") || !jsonObject.has("session")) {
                    callback.done("An error occured! [R2]");
                    return;
                }
                final RedeemResponse response = new RedeemResponse();
                response.setMcName(jsonObject.get("mcname").getAsString());
                response.setSession(jsonObject.get("session").getAsString());
                callback.done(response);
            }
        });
    }
    
    private static URLConnection preparePostRequest(final String url, final String body) {
        try {
            HttpURLConnection con;
            if (url.toLowerCase().startsWith("https://")) {
                con = (HttpsURLConnection)new URL(url).openConnection();
            }
            else {
                con = (HttpURLConnection)new URL(url).openConnection();
            }
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(body.getBytes("UTF-8"));
            wr.flush();
            wr.close();
            return con;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Object getResult(final URLConnection urlConnection) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            final JsonElement jsonElement = (JsonElement)ModApi.gson.fromJson(result.toString(), (Class)JsonElement.class);
            System.out.println(result.toString());
            if (!jsonElement.isJsonObject() || !jsonElement.getAsJsonObject().has("success")) {
                return "An error occured! [G1]";
            }
            if (!jsonElement.getAsJsonObject().get("success").getAsBoolean()) {
                return jsonElement.getAsJsonObject().has("errorMessage") ? jsonElement.getAsJsonObject().get("errorMessage").getAsString() : "An error occured! [G4]";
            }
            if (!jsonElement.getAsJsonObject().has("result")) {
                return "An error occured! [G3]";
            }
            return jsonElement.getAsJsonObject().get("result").isJsonObject() ? jsonElement.getAsJsonObject().get("result").getAsJsonObject() : null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "An error occured! [G2]";
        }
    }
}
