package xyz.mathax.mathaxclient.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.utils.misc.JsonDateDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.stream.Stream;

public class Http {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();

    public static class Request {
        private HttpRequest.Builder builder;

        private Method method;

        public Request(Method method, String url) {
            try {
                this.builder = HttpRequest.newBuilder().uri(new URI(url)).header("User-Agent", MatHax.NAME);
                this.method = method;
            } catch (URISyntaxException exception) {
                exception.printStackTrace();
            }
        }

        public Request bearer(String token) {
            builder.header("Authorization", "Bearer " + token);

            return this;
        }

        public Request bodyString(String string) {
            builder.header("Content-Type", "text/plain");
            builder.method(method.name(), HttpRequest.BodyPublishers.ofString(string));
            method = null;

            return this;
        }

        public Request bodyForm(String string) {
            builder.header("Content-Type", "application/x-www-form-urlencoded");
            builder.method(method.name(), HttpRequest.BodyPublishers.ofString(string));
            method = null;

            return this;
        }

        public Request bodyJson(String string) {
            builder.header("Content-Type", "application/json");
            builder.method(method.name(), HttpRequest.BodyPublishers.ofString(string));
            method = null;

            return this;
        }

        public Request bodyJson(Object object) {
            builder.header("Content-Type", "application/json");
            builder.method(method.name(), HttpRequest.BodyPublishers.ofString(GSON.toJson(object)));
            method = null;

            return this;
        }

        private <T> T _send(String accept, HttpResponse.BodyHandler<T> responseBodyHandler) {
            builder.header("Accept", accept);
            if (method != null) {
                builder.method(method.name(), HttpRequest.BodyPublishers.noBody());
            }

            try {
                var response = CLIENT.send(builder.build(), responseBodyHandler);
                return response.statusCode() == 200 ? response.body() : null;
            } catch (IOException | InterruptedException exception) {
                exception.printStackTrace();
                return null;
            }
        }

        public void send() {
            _send("*/*", HttpResponse.BodyHandlers.discarding());
        }

        public InputStream sendInputStream() {
            return _send("*/*", HttpResponse.BodyHandlers.ofInputStream());
        }

        public String sendString() {
            return _send("*/*", HttpResponse.BodyHandlers.ofString());
        }

        public Stream<String> sendLines() {
            return _send("*/*", HttpResponse.BodyHandlers.ofLines());
        }

        public <T> T sendJson(Type type) {
            InputStream inputStream = _send("application/json", HttpResponse.BodyHandlers.ofInputStream());
            return inputStream == null ? null : GSON.fromJson(new InputStreamReader(inputStream), type);
        }
    }

    public static Request get(String url) {
        return new Request(Method.GET, url);
    }

    public static Request post(String url) {
        return new Request(Method.POST, url);
    }

    private enum Method {
        GET,
        POST
    }
}
