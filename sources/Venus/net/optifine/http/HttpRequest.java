/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    private String host = null;
    private int port = 0;
    private Proxy proxy = Proxy.NO_PROXY;
    private String method = null;
    private String file = null;
    private String http = null;
    private Map<String, String> headers = new LinkedHashMap<String, String>();
    private byte[] body = null;
    private int redirects = 0;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_POST = "POST";
    public static final String HTTP_1_0 = "HTTP/1.0";
    public static final String HTTP_1_1 = "HTTP/1.1";

    public HttpRequest(String string, int n, Proxy proxy, String string2, String string3, String string4, Map<String, String> map, byte[] byArray) {
        this.host = string;
        this.port = n;
        this.proxy = proxy;
        this.method = string2;
        this.file = string3;
        this.http = string4;
        this.headers = map;
        this.body = byArray;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getMethod() {
        return this.method;
    }

    public String getFile() {
        return this.file;
    }

    public String getHttp() {
        return this.http;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public byte[] getBody() {
        return this.body;
    }

    public int getRedirects() {
        return this.redirects;
    }

    public void setRedirects(int n) {
        this.redirects = n;
    }

    public Proxy getProxy() {
        return this.proxy;
    }
}

