// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.altloader;

import java.util.concurrent.Executors;
import com.google.gson.JsonElement;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import java.util.concurrent.ExecutorService;

public class Api
{
    public static final ExecutorService EXECUTOR_SERVICE;
    private static final String API_URL = "https://api.easymc.io/v1";
    private static final Gson gson;
    
    public static void redeem(final String token, final Callback<Object> callback) {
        Api.EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                final HttpsURLConnection connection = Api.preparePostRequest("https://api.easymc.io/v1/token/redeem", "{\"token\":\"" + token + "\",\"client\":\"mod-1.8.9\"}");
                if (connection == null) {
                    callback.done("Could not create Connection. Please try again later.");
                    return;
                }
                final Object o = Api.getResult(connection);
                if (o instanceof String) {
                    callback.done(o);
                    return;
                }
                final JsonObject jsonObject = (JsonObject)o;
                final RedeemResponse response = new RedeemResponse();
                response.session = jsonObject.get("session").getAsString();
                response.mcName = jsonObject.get("mcName").getAsString();
                response.uuid = jsonObject.get("uuid").getAsString();
                callback.done(response);
            }
        });
    }
    
    static HttpsURLConnection preparePostRequest(final String url, final String body) {
        try {
            final HttpsURLConnection con = (HttpsURLConnection)new URL(url).openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
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
    
    static Object getResult(final HttpsURLConnection connection) {
        try {
            final InputStreamReader inputStreamReader = (connection.getResponseCode() != 200) ? new InputStreamReader(connection.getErrorStream()) : new InputStreamReader(connection.getInputStream());
            final BufferedReader reader = new BufferedReader(inputStreamReader);
            final StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            final JsonElement jsonElement = Api.gson.fromJson(result.toString(), JsonElement.class);
            if (!jsonElement.isJsonObject()) {
                return "Could not parse response.";
            }
            if (jsonElement.getAsJsonObject().has("error")) {
                return jsonElement.getAsJsonObject().get("error").getAsString();
            }
            if (!jsonElement.getAsJsonObject().has("session") || !jsonElement.getAsJsonObject().has("uuid") || !jsonElement.getAsJsonObject().has("mcName")) {
                return "Response is invalid.";
            }
            return jsonElement.getAsJsonObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    static {
        EXECUTOR_SERVICE = Executors.newCachedThreadPool();
        gson = new Gson();
    }
}
