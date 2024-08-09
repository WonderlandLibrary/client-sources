/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.AbstractExecutionAwareRequest;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.params.HttpProtocolParams;

public abstract class HttpRequestBase
extends AbstractExecutionAwareRequest
implements HttpUriRequest,
Configurable {
    private ProtocolVersion version;
    private URI uri;
    private RequestConfig config;

    @Override
    public abstract String getMethod();

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.version != null ? this.version : HttpProtocolParams.getVersion(this.getParams());
    }

    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public RequestLine getRequestLine() {
        String string = this.getMethod();
        ProtocolVersion protocolVersion = this.getProtocolVersion();
        URI uRI = this.getURI();
        String string2 = null;
        if (uRI != null) {
            string2 = uRI.toASCIIString();
        }
        if (string2 == null || string2.isEmpty()) {
            string2 = "/";
        }
        return new BasicRequestLine(string, string2, protocolVersion);
    }

    @Override
    public RequestConfig getConfig() {
        return this.config;
    }

    public void setConfig(RequestConfig requestConfig) {
        this.config = requestConfig;
    }

    public void setURI(URI uRI) {
        this.uri = uRI;
    }

    public void started() {
    }

    public void releaseConnection() {
        this.reset();
    }

    public String toString() {
        return this.getMethod() + " " + this.getURI() + " " + this.getProtocolVersion();
    }
}

