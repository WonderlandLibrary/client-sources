package me.dinozoid.strife.util.network;

import me.dinozoid.strife.StrifeClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class HttpUtil {

    private static final String USER_AGENT = "Strife Client";

    public static CompletableFuture<HttpResponse> asyncHttpsConnection(String url) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        StrifeClient.INSTANCE.executorService().submit(() -> {
            toComplete.complete(httpsConnection(url));
        });
        return toComplete;
    }

    public static CompletableFuture<HttpResponse> asyncHttpConnection(String url) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        StrifeClient.INSTANCE.executorService().submit(() -> {
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

}
