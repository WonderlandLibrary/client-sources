/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.execchain.ConnectionHolder;
import org.apache.http.impl.execchain.ResponseEntityProxy;
import org.apache.http.params.HttpParams;

class HttpResponseProxy
implements CloseableHttpResponse {
    private final HttpResponse original;
    private final ConnectionHolder connHolder;

    public HttpResponseProxy(HttpResponse httpResponse, ConnectionHolder connectionHolder) {
        this.original = httpResponse;
        this.connHolder = connectionHolder;
        ResponseEntityProxy.enchance(httpResponse, connectionHolder);
    }

    @Override
    public void close() throws IOException {
        if (this.connHolder != null) {
            this.connHolder.close();
        }
    }

    @Override
    public StatusLine getStatusLine() {
        return this.original.getStatusLine();
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        this.original.setStatusLine(statusLine);
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n) {
        this.original.setStatusLine(protocolVersion, n);
    }

    @Override
    public void setStatusLine(ProtocolVersion protocolVersion, int n, String string) {
        this.original.setStatusLine(protocolVersion, n, string);
    }

    @Override
    public void setStatusCode(int n) throws IllegalStateException {
        this.original.setStatusCode(n);
    }

    @Override
    public void setReasonPhrase(String string) throws IllegalStateException {
        this.original.setReasonPhrase(string);
    }

    @Override
    public HttpEntity getEntity() {
        return this.original.getEntity();
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.original.setEntity(httpEntity);
    }

    @Override
    public Locale getLocale() {
        return this.original.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        this.original.setLocale(locale);
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.original.getProtocolVersion();
    }

    @Override
    public boolean containsHeader(String string) {
        return this.original.containsHeader(string);
    }

    @Override
    public Header[] getHeaders(String string) {
        return this.original.getHeaders(string);
    }

    @Override
    public Header getFirstHeader(String string) {
        return this.original.getFirstHeader(string);
    }

    @Override
    public Header getLastHeader(String string) {
        return this.original.getLastHeader(string);
    }

    @Override
    public Header[] getAllHeaders() {
        return this.original.getAllHeaders();
    }

    @Override
    public void addHeader(Header header) {
        this.original.addHeader(header);
    }

    @Override
    public void addHeader(String string, String string2) {
        this.original.addHeader(string, string2);
    }

    @Override
    public void setHeader(Header header) {
        this.original.setHeader(header);
    }

    @Override
    public void setHeader(String string, String string2) {
        this.original.setHeader(string, string2);
    }

    @Override
    public void setHeaders(Header[] headerArray) {
        this.original.setHeaders(headerArray);
    }

    @Override
    public void removeHeader(Header header) {
        this.original.removeHeader(header);
    }

    @Override
    public void removeHeaders(String string) {
        this.original.removeHeaders(string);
    }

    @Override
    public HeaderIterator headerIterator() {
        return this.original.headerIterator();
    }

    @Override
    public HeaderIterator headerIterator(String string) {
        return this.original.headerIterator(string);
    }

    @Override
    public HttpParams getParams() {
        return this.original.getParams();
    }

    @Override
    public void setParams(HttpParams httpParams) {
        this.original.setParams(httpParams);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("HttpResponseProxy{");
        stringBuilder.append(this.original);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

