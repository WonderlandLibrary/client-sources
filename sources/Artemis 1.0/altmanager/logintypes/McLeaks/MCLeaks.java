package artemis.altmanager.logintypes.McLeaks;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MCLeaks {

    private static Session2 session;

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final Gson gson = new Gson();

    public static boolean isAltActive() {
        return session != null;
    }

    public static Session2 getSession() {
        return session;
    }

    public static void refresh(final Session2 session) {
        MCLeaks.session = session;
    }

    public static void remove() {
        MCLeaks.session = null;
    }

    public static void redeem(final String token, final Callback<Object> callback) {
        EXECUTOR_SERVICE.execute(() -> {
            final URLConnection connection = preparePostRequest("{\"token\":\"" + token + "\"}");

            if(connection == null) {
                callback.done("An error occured! [R1]");
                return;
            }

            final Object o = getResult(connection);
            if(o instanceof String) {
                callback.done(o);
                return;
            }

            final JsonObject jsonObject = (JsonObject) o;

            if(jsonObject == null)
                return;

            if(!jsonObject.has("mcname") || !jsonObject.has("session")) {
                callback.done("An error occured! [R2]");
                return;
            }

            callback.done(new RedeemResponse(jsonObject.get("mcname").getAsString(), jsonObject.get("session").getAsString()));
        });
    }

    private static URLConnection preparePostRequest(final String body) {
        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://auth.mcleaks.net/v1/redeem").openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
            connection.setDoOutput(true);

            final DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
            dataOutputStream.close();

            return connection;
        }catch(final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getResult(final URLConnection urlConnection) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            final StringBuilder stringBuilder = new StringBuilder();

            String line;
            while((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line);

            bufferedReader.close();
            final JsonElement jsonElement = gson.fromJson(stringBuilder.toString(), JsonElement.class);

            if(!jsonElement.isJsonObject() || !jsonElement.getAsJsonObject().has("success"))
                return "An error occured! [G1]";

            if(!jsonElement.getAsJsonObject().get("success").getAsBoolean())
                return jsonElement.getAsJsonObject().has("errorMessage") ? jsonElement.getAsJsonObject().get("errorMessage").getAsString() : "An error occured! [G4]";

            if(!jsonElement.getAsJsonObject().has("result"))
                return "An error occured! [G3]";

            return jsonElement.getAsJsonObject().get("result").isJsonObject() ? jsonElement.getAsJsonObject().get("result").getAsJsonObject() : null;
        }catch(final Exception e) {
            e.printStackTrace();
            return "An error occured! [G2]";
        }
    }
}