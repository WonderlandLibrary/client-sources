/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import optifine.HttpListener;
import optifine.HttpPipelineConnection;
import optifine.HttpPipelineRequest;
import optifine.HttpRequest;
import optifine.HttpResponse;

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

    public static void addRequest(String urlStr, HttpListener listener) throws IOException {
        HttpPipeline.addRequest(urlStr, listener, Proxy.NO_PROXY);
    }

    public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
        HttpRequest hr2 = HttpPipeline.makeRequest(urlStr, proxy);
        HttpPipelineRequest hpr = new HttpPipelineRequest(hr2, listener);
        HttpPipeline.addRequest(hpr);
    }

    public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
        URL url = new URL(urlStr);
        if (!url.getProtocol().equals("http")) {
            throw new IOException("Only protocol http is supported: " + url);
        }
        String file = url.getFile();
        String host = url.getHost();
        int port = url.getPort();
        if (port <= 0) {
            port = 80;
        }
        String method = "GET";
        String http = "HTTP/1.1";
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        headers.put(HEADER_USER_AGENT, "Java/" + System.getProperty("java.version"));
        headers.put(HEADER_HOST, host);
        headers.put(HEADER_ACCEPT, "text/html, image/gif, image/png");
        headers.put(HEADER_CONNECTION, HEADER_VALUE_KEEP_ALIVE);
        byte[] body = new byte[]{};
        HttpRequest req = new HttpRequest(host, port, proxy, method, file, http, headers, body);
        return req;
    }

    public static void addRequest(HttpPipelineRequest pr2) {
        HttpRequest hr2 = pr2.getHttpRequest();
        HttpPipelineConnection conn = HttpPipeline.getConnection(hr2.getHost(), hr2.getPort(), hr2.getProxy());
        while (!conn.addRequest(pr2)) {
            HttpPipeline.removeConnection(hr2.getHost(), hr2.getPort(), hr2.getProxy(), conn);
            conn = HttpPipeline.getConnection(hr2.getHost(), hr2.getPort(), hr2.getProxy());
        }
    }

    private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
        String key = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
        if (conn == null) {
            conn = new HttpPipelineConnection(host, port, proxy);
            mapConnections.put(key, conn);
        }
        return conn;
    }

    private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
        String key = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection conn = (HttpPipelineConnection)mapConnections.get(key);
        if (conn == hpc) {
            mapConnections.remove(key);
        }
    }

    private static String makeConnectionKey(String host, int port, Proxy proxy) {
        String hostPort = String.valueOf(host) + ":" + port + "-" + proxy;
        return hostPort;
    }

    public static byte[] get(String urlStr) throws IOException {
        return HttpPipeline.get(urlStr, Proxy.NO_PROXY);
    }

    public static byte[] get(String urlStr, Proxy proxy) throws IOException {
        HttpRequest req = HttpPipeline.makeRequest(urlStr, proxy);
        HttpResponse resp = HttpPipeline.executeRequest(req);
        if (resp.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + resp.getStatus());
        }
        return resp.getBody();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HttpResponse executeRequest(HttpRequest req) throws IOException {
        final HashMap map = new HashMap();
        String KEY_RESPONSE = "Response";
        String KEY_EXCEPTION = "Exception";
        HttpListener l2 = new HttpListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void finished(HttpRequest req, HttpResponse resp) {
                HashMap var3 = map;
                HashMap hashMap = map;
                synchronized (hashMap) {
                    map.put("Response", resp);
                    map.notifyAll();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void failed(HttpRequest req, Exception e2) {
                HashMap var3 = map;
                HashMap hashMap = map;
                synchronized (hashMap) {
                    map.put("Exception", e2);
                    map.notifyAll();
                }
            }
        };
        HashMap hashMap = map;
        synchronized (hashMap) {
            HttpPipelineRequest hpr = new HttpPipelineRequest(req, l2);
            HttpPipeline.addRequest(hpr);
            try {
                map.wait();
            }
            catch (InterruptedException var10) {
                throw new InterruptedIOException("Interrupted");
            }
            Exception e2 = (Exception)map.get("Exception");
            if (e2 != null) {
                if (e2 instanceof IOException) {
                    throw (IOException)e2;
                }
                if (e2 instanceof RuntimeException) {
                    throw (RuntimeException)e2;
                }
                throw new RuntimeException(e2.getMessage(), e2);
            }
            HttpResponse resp = (HttpResponse)map.get("Response");
            if (resp == null) {
                throw new IOException("Response is null");
            }
            return resp;
        }
    }

    public static boolean hasActiveRequests() {
        HttpPipelineConnection conn;
        Collection conns = mapConnections.values();
        Iterator it2 = conns.iterator();
        do {
            if (it2.hasNext()) continue;
            return false;
        } while (!(conn = (HttpPipelineConnection)it2.next()).hasActiveRequests());
        return true;
    }

}

