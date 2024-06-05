package client.metaware.impl.module.render;

import client.metaware.Metaware;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HttpUtil {

    private static final String USER_AGENT = "Strife Client";

    public static CompletableFuture<HttpResponse> asyncHttpsConnection(String url) {
        return asyncHttpConnection(url);
    }

    public static CompletableFuture<HttpResponse> asyncHttpsConnection(String url, Map<String, String> headers) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        Metaware.INSTANCE.getExecutorService().submit(() -> {
            toComplete.complete(httpsConnection(url, headers));
        });
        return toComplete;
    }

    public static CompletableFuture<HttpResponse> asyncHttpConnection(String url) {
        return asyncHttpConnection(url, null);
    }

    public static CompletableFuture<HttpResponse> asyncHttpConnection(String url, Map<String, String> headers) {
        CompletableFuture<HttpResponse> toComplete = new CompletableFuture<>();
        Metaware.INSTANCE.getExecutorService().submit(() -> {
            toComplete.complete(httpConnection(url, headers));
        });
        return toComplete;
    }

    public static HttpResponse httpsConnection(String url, Map<String, String> headers) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setHostnameVerifier(new client.metaware.api.account.HttpUtil.AllHostnameVerifier());
            connection.setRequestProperty("User-Agent", USER_AGENT);
            if (headers != null)
                headers.forEach(connection::setRequestProperty);
            connection.connect();
            return new HttpResponse(inputStreamToString(connection.getInputStream()), connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HttpResponse httpConnection(String url, Map<String, String> headers) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", USER_AGENT);
            if (headers != null)
                headers.forEach(connection::setRequestProperty);
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