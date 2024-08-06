package club.strifeclient.util.networking;

import club.strifeclient.util.system.FileUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    /*
        Thanks lennox for HTTP <3
     */

    private static final String USER_AGENT = "Strife Client";

    public static HttpResponse httpsConnection(String url, Map<String, String> headers) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setHostnameVerifier(new AllHostnameVerifier());
            connection.setRequestProperty("User-Agent", USER_AGENT);
            if (headers != null)
                headers.forEach(connection::setRequestProperty);
            connection.connect();
            return new HttpResponse(FileUtil.inputStreamToString(connection.getInputStream()), connection.getResponseCode());
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
            return new HttpResponse(FileUtil.inputStreamToString(connection.getInputStream()), connection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class HttpResponse {
        private final String content;
        private final int response;
        public HttpResponse(String content, int response) {
            this.content = content;
            this.response = response;
        }
        public String getContent() {
            return content;
        }
        public int getResponse() {
            return response;
        }
    }
}