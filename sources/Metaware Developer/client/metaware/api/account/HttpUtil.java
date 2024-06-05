package client.metaware.api.account;

import client.metaware.Metaware;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class HttpUtil {

    private static final String USER_AGENT = "WhizClient";

    public static class AllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    public static CompletableFuture<HttpResponse> asyncHttpsConnection(String url) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        Metaware.INSTANCE.getExecutorService().submit(() -> {
            toComplete.complete(httpsConnection(url));
        });
        return toComplete;
    }

    public static CompletableFuture<HttpResponse> asyncHttpConnection(String url) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        Metaware.INSTANCE.getExecutorService().submit(() -> {
            toComplete.complete(httpConnection(url));
        });
        return toComplete;
    }

    public static HttpResponse httpsConnection(String url) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setHostnameVerifier(new AllHostnameVerifier());
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.connect();
            return new HttpResponse(inputStreamToString(connection.getInputStream()), connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse httpConnection(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.connect();
            return new HttpResponse(inputStreamToString(connection.getInputStream()), connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static class HttpResponse {
        private final String content;
        private final int response;

        public HttpResponse(String content, int response) {
            this.content = content;
            this.response = response;
        }

        public String content() {
            return content;
        }

        public int response() {
            return response;
        }
    }
    
    public static CompletableFuture<String> getUUIDString(String name) {
        final CompletableFuture<HttpUtil.HttpResponse> httpsResponse = HttpUtil.asyncHttpsConnection("https://api.mojang.com/users/profiles/minecraft/" + name);
        final CompletableFuture<String> toComplete = new CompletableFuture<>();
        httpsResponse.whenCompleteAsync((response, throwable) -> {
            if(response.content().equals("Invalid UUID")) toComplete.complete(null);
            JsonParser parser = new JsonParser();
            JsonObject uuidObject = parser.parse(response.content()).getAsJsonObject();
            toComplete.complete(uuidObject.get("id").getAsString());
        });
        return toComplete;
    }
    
    

}
