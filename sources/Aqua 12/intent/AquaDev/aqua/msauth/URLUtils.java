// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.msauth;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import java.net.URL;
import java.net.HttpURLConnection;
import com.google.gson.Gson;

public class URLUtils
{
    private static final Gson gson;
    private static final String ACCEPTED_RESPONSE = "application/json";
    
    public static HttpURLConnection createURLConnection(final String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static JsonObject readJson(final HttpURLConnection connection) {
        return URLUtils.gson.fromJson(readResponse(connection), JsonObject.class);
    }
    
    public static JsonObject postJson(final String url, final Object request) {
        final HttpURLConnection connection = createURLConnection(url);
        connection.setDoOutput(true);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("Accept", "application/json");
        try {
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(URLUtils.gson.toJson(request).getBytes(StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return readJson(connection);
    }
    
    public static String readResponse(final HttpURLConnection connection) {
        final String redirection = connection.getHeaderField("Location");
        if (redirection != null) {
            return readResponse(createURLConnection(redirection));
        }
        final StringBuilder response = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader((connection.getResponseCode() >= 400) ? connection.getErrorStream() : connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }
    
    private static String readResponse(final BufferedReader br) {
        try {
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static JsonObject readJSONFromURL(final String url, final Map<String, String> headers) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            if (headers != null) {
                for (final String header : headers.keySet()) {
                    connection.addRequestProperty(header, headers.get(header));
                }
            }
            final InputStream is = (connection.getResponseCode() != 200) ? connection.getErrorStream() : connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return URLUtils.gson.fromJson(readResponse(rd), JsonObject.class);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        gson = new Gson();
    }
}
