package org.jsoup.helper;

import org.jsoup.*;
import org.jsoup.parser.*;
import java.nio.charset.*;
import java.util.*;
import java.net.*;

public static class Request extends HttpConnection.Base<Connection.Request> implements Connection.Request
{
    private Proxy proxy;
    private int timeoutMilliseconds;
    private int maxBodySizeBytes;
    private boolean followRedirects;
    private Collection<Connection.KeyVal> data;
    private String body;
    private boolean ignoreHttpErrors;
    private boolean ignoreContentType;
    private Parser parser;
    private boolean parserDefined;
    private boolean validateTSLCertificates;
    private String postDataCharset;
    
    private Request() {
        this.body = null;
        this.ignoreHttpErrors = false;
        this.ignoreContentType = false;
        this.parserDefined = false;
        this.validateTSLCertificates = true;
        this.postDataCharset = "UTF-8";
        this.timeoutMilliseconds = 30000;
        this.maxBodySizeBytes = 1048576;
        this.followRedirects = true;
        this.data = new ArrayList<Connection.KeyVal>();
        this.method = Method.GET;
        this.headers.put("Accept-Encoding", "gzip");
        this.headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
        this.parser = Parser.htmlParser();
    }
    
    public Proxy proxy() {
        return this.proxy;
    }
    
    public Request proxy(final Proxy proxy) {
        this.proxy = proxy;
        return this;
    }
    
    public Request proxy(final String host, final int port) {
        this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
        return this;
    }
    
    public int timeout() {
        return this.timeoutMilliseconds;
    }
    
    public Request timeout(final int millis) {
        Validate.isTrue(millis >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
        this.timeoutMilliseconds = millis;
        return this;
    }
    
    public int maxBodySize() {
        return this.maxBodySizeBytes;
    }
    
    public Connection.Request maxBodySize(final int bytes) {
        Validate.isTrue(bytes >= 0, "maxSize must be 0 (unlimited) or larger");
        this.maxBodySizeBytes = bytes;
        return this;
    }
    
    public boolean followRedirects() {
        return this.followRedirects;
    }
    
    public Connection.Request followRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }
    
    public boolean ignoreHttpErrors() {
        return this.ignoreHttpErrors;
    }
    
    public boolean validateTLSCertificates() {
        return this.validateTSLCertificates;
    }
    
    public void validateTLSCertificates(final boolean value) {
        this.validateTSLCertificates = value;
    }
    
    public Connection.Request ignoreHttpErrors(final boolean ignoreHttpErrors) {
        this.ignoreHttpErrors = ignoreHttpErrors;
        return this;
    }
    
    public boolean ignoreContentType() {
        return this.ignoreContentType;
    }
    
    public Connection.Request ignoreContentType(final boolean ignoreContentType) {
        this.ignoreContentType = ignoreContentType;
        return this;
    }
    
    public Request data(final Connection.KeyVal keyval) {
        Validate.notNull(keyval, "Key val must not be null");
        this.data.add(keyval);
        return this;
    }
    
    public Collection<Connection.KeyVal> data() {
        return this.data;
    }
    
    public Connection.Request requestBody(final String body) {
        this.body = body;
        return this;
    }
    
    public String requestBody() {
        return this.body;
    }
    
    public Request parser(final Parser parser) {
        this.parser = parser;
        this.parserDefined = true;
        return this;
    }
    
    public Parser parser() {
        return this.parser;
    }
    
    public Connection.Request postDataCharset(final String charset) {
        Validate.notNull(charset, "Charset must not be null");
        if (!Charset.isSupported(charset)) {
            throw new IllegalCharsetNameException(charset);
        }
        this.postDataCharset = charset;
        return this;
    }
    
    public String postDataCharset() {
        return this.postDataCharset;
    }
}
