/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.optifine.Config;
import net.optifine.http.HttpListener;
import net.optifine.http.HttpPipelineConnection;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public class HttpPipeline {
    private static Map mapConnections = new HashMap();
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
    public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String HEADER_VALUE_CHUNKED = "chunked";

    private HttpPipeline() {
    }

    public static void addRequest(String string, HttpListener httpListener) throws IOException {
        HttpPipeline.addRequest(string, httpListener, Proxy.NO_PROXY);
    }

    public static void addRequest(String string, HttpListener httpListener, Proxy proxy) throws IOException {
        HttpRequest httpRequest = HttpPipeline.makeRequest(string, proxy);
        HttpPipelineRequest httpPipelineRequest = new HttpPipelineRequest(httpRequest, httpListener);
        HttpPipeline.addRequest(httpPipelineRequest);
    }

    public static HttpRequest makeRequest(String string, Proxy proxy) throws IOException {
        URL uRL = new URL(string);
        if (!uRL.getProtocol().equals("http")) {
            throw new IOException("Only protocol http is supported: " + uRL);
        }
        String string2 = uRL.getFile();
        String string3 = uRL.getHost();
        int n = uRL.getPort();
        if (n <= 0) {
            n = 80;
        }
        String string4 = "GET";
        String string5 = "HTTP/1.1";
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put(HEADER_USER_AGENT, "Java/" + System.getProperty("java.version"));
        linkedHashMap.put(HEADER_HOST, string3);
        linkedHashMap.put(HEADER_ACCEPT, "text/html, image/gif, image/png");
        linkedHashMap.put(HEADER_CONNECTION, HEADER_VALUE_KEEP_ALIVE);
        byte[] byArray = new byte[]{};
        return new HttpRequest(string3, n, proxy, string4, string2, string5, linkedHashMap, byArray);
    }

    public static void addRequest(HttpPipelineRequest httpPipelineRequest) {
        HttpRequest httpRequest = httpPipelineRequest.getHttpRequest();
        HttpPipelineConnection httpPipelineConnection = HttpPipeline.getConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy());
        while (!httpPipelineConnection.addRequest(httpPipelineRequest)) {
            HttpPipeline.removeConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy(), httpPipelineConnection);
            httpPipelineConnection = HttpPipeline.getConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy());
        }
    }

    private static synchronized HttpPipelineConnection getConnection(String string, int n, Proxy proxy) {
        String string2 = HttpPipeline.makeConnectionKey(string, n, proxy);
        HttpPipelineConnection httpPipelineConnection = (HttpPipelineConnection)mapConnections.get(string2);
        if (httpPipelineConnection == null) {
            httpPipelineConnection = new HttpPipelineConnection(string, n, proxy);
            mapConnections.put(string2, httpPipelineConnection);
        }
        return httpPipelineConnection;
    }

    private static synchronized void removeConnection(String string, int n, Proxy proxy, HttpPipelineConnection httpPipelineConnection) {
        String string2 = HttpPipeline.makeConnectionKey(string, n, proxy);
        HttpPipelineConnection httpPipelineConnection2 = (HttpPipelineConnection)mapConnections.get(string2);
        if (httpPipelineConnection2 == httpPipelineConnection) {
            mapConnections.remove(string2);
        }
    }

    private static String makeConnectionKey(String string, int n, Proxy proxy) {
        return string + ":" + n + "-" + proxy;
    }

    public static byte[] get(String string) throws IOException {
        return HttpPipeline.get(string, Proxy.NO_PROXY);
    }

    public static byte[] get(String string, Proxy proxy) throws IOException {
        if (string.startsWith("file:")) {
            URL uRL = new URL(string);
            InputStream inputStream = uRL.openStream();
            return Config.readAll(inputStream);
        }
        HttpRequest httpRequest = HttpPipeline.makeRequest(string, proxy);
        HttpResponse httpResponse = HttpPipeline.executeRequest(httpRequest);
        if (httpResponse.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + httpResponse.getStatus());
        }
        return httpResponse.getBody();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HttpResponse executeRequest(HttpRequest httpRequest) throws IOException {
        HashMap hashMap = new HashMap();
        String string = "Response";
        String string2 = "Exception";
        HttpListener httpListener = new HttpListener(hashMap){
            final Map val$map;
            {
                this.val$map = map;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void finished(HttpRequest httpRequest, HttpResponse httpResponse) {
                Map map = this.val$map;
                synchronized (map) {
                    this.val$map.put("Response", httpResponse);
                    this.val$map.notifyAll();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void failed(HttpRequest httpRequest, Exception exception) {
                Map map = this.val$map;
                synchronized (map) {
                    this.val$map.put("Exception", exception);
                    this.val$map.notifyAll();
                }
            }
        };
        HashMap hashMap2 = hashMap;
        synchronized (hashMap2) {
            HttpPipelineRequest httpPipelineRequest = new HttpPipelineRequest(httpRequest, httpListener);
            HttpPipeline.addRequest(httpPipelineRequest);
            try {
                hashMap.wait();
            } catch (InterruptedException interruptedException) {
                throw new InterruptedIOException("Interrupted");
            }
            Exception exception = (Exception)hashMap.get("Exception");
            if (exception != null) {
                if (exception instanceof IOException) {
                    throw (IOException)exception;
                }
                if (exception instanceof RuntimeException) {
                    throw (RuntimeException)exception;
                }
                throw new RuntimeException(exception.getMessage(), exception);
            }
            HttpResponse httpResponse = (HttpResponse)hashMap.get("Response");
            if (httpResponse == null) {
                throw new IOException("Response is null");
            }
            return httpResponse;
        }
    }

    public static boolean hasActiveRequests() {
        for (HttpPipelineConnection httpPipelineConnection : mapConnections.values()) {
            if (!httpPipelineConnection.hasActiveRequests()) continue;
            return false;
        }
        return true;
    }
}

