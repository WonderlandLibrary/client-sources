package optifine;

import java.net.*;
import java.io.*;
import java.util.*;

public class HttpPipeline
{
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
    public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String HEADER_VALUE_CHUNKED = "chunked";
    private static Map mapConnections;
    
    public static void addRequest(final String urlStr, final HttpListener listener) throws IOException {
        addRequest(urlStr, listener, Proxy.NO_PROXY);
    }
    
    public static void addRequest(final String urlStr, final HttpListener listener, final Proxy proxy) throws IOException {
        final HttpRequest hr = makeRequest(urlStr, proxy);
        final HttpPipelineRequest hpr = new HttpPipelineRequest(hr, listener);
        addRequest(hpr);
    }
    
    public static HttpRequest makeRequest(final String urlStr, final Proxy proxy) throws IOException {
        final URL url = new URL(urlStr);
        if (!url.getProtocol().equals("http")) {
            throw new IOException("Only protocol http is supported: " + url);
        }
        final String file = url.getFile();
        final String host = url.getHost();
        int port = url.getPort();
        if (port <= 0) {
            port = 80;
        }
        final String method = "GET";
        final String http = "HTTP/1.1";
        final LinkedHashMap headers = new LinkedHashMap();
        headers.put("User-Agent", "Java/" + System.getProperty("java.version"));
        headers.put("Host", host);
        headers.put("Accept", "text/html, image/gif, image/png");
        headers.put("Connection", "keep-alive");
        final byte[] body = new byte[0];
        final HttpRequest req = new HttpRequest(host, port, proxy, method, file, http, headers, body);
        return req;
    }
    
    public static void addRequest(final HttpPipelineRequest pr) {
        final HttpRequest hr = pr.getHttpRequest();
        for (HttpPipelineConnection conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy()); !conn.addRequest(pr); conn = getConnection(hr.getHost(), hr.getPort(), hr.getProxy())) {
            removeConnection(hr.getHost(), hr.getPort(), hr.getProxy(), conn);
        }
    }
    
    private static synchronized HttpPipelineConnection getConnection(final String host, final int port, final Proxy proxy) {
        final String key = makeConnectionKey(host, port, proxy);
        HttpPipelineConnection conn = HttpPipeline.mapConnections.get(key);
        if (conn == null) {
            conn = new HttpPipelineConnection(host, port, proxy);
            HttpPipeline.mapConnections.put(key, conn);
        }
        return conn;
    }
    
    private static synchronized void removeConnection(final String host, final int port, final Proxy proxy, final HttpPipelineConnection hpc) {
        final String key = makeConnectionKey(host, port, proxy);
        final HttpPipelineConnection conn = HttpPipeline.mapConnections.get(key);
        if (conn == hpc) {
            HttpPipeline.mapConnections.remove(key);
        }
    }
    
    private static String makeConnectionKey(final String host, final int port, final Proxy proxy) {
        final String hostPort = host + ":" + port + "-" + proxy;
        return hostPort;
    }
    
    public static byte[] get(final String urlStr) throws IOException {
        return get(urlStr, Proxy.NO_PROXY);
    }
    
    public static byte[] get(final String urlStr, final Proxy proxy) throws IOException {
        final HttpRequest req = makeRequest(urlStr, proxy);
        final HttpResponse resp = executeRequest(req);
        if (resp.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + resp.getStatus());
        }
        return resp.getBody();
    }
    
    public static HttpResponse executeRequest(final HttpRequest req) throws IOException {
        final HashMap map = new HashMap();
        final String KEY_RESPONSE = "Response";
        final String KEY_EXCEPTION = "Exception";
        final HttpListener l = new HttpListener() {
            @Override
            public void finished(final HttpRequest req, final HttpResponse resp) {
                final Map var3 = map;
                synchronized (map) {
                    map.put("Response", resp);
                    map.notifyAll();
                }
            }
            
            @Override
            public void failed(final HttpRequest req, final Exception e) {
                final Map var3 = map;
                synchronized (map) {
                    map.put("Exception", e);
                    map.notifyAll();
                }
            }
        };
        synchronized (map) {
            final HttpPipelineRequest hpr = new HttpPipelineRequest(req, l);
            addRequest(hpr);
            try {
                map.wait();
            }
            catch (InterruptedException var10) {
                throw new InterruptedIOException("Interrupted");
            }
            final Exception e = map.get("Exception");
            if (e != null) {
                if (e instanceof IOException) {
                    throw (IOException)e;
                }
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                }
                throw new RuntimeException(e.getMessage(), e);
            }
            else {
                final HttpResponse resp = map.get("Response");
                if (resp == null) {
                    throw new IOException("Response is null");
                }
                return resp;
            }
        }
    }
    
    public static boolean hasActiveRequests() {
        final Collection conns = HttpPipeline.mapConnections.values();
        for (final HttpPipelineConnection conn : conns) {
            if (conn.hasActiveRequests()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        HttpPipeline.mapConnections = new HashMap();
    }
}
