/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.message;

import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicRequestLine;
import org.apache.http.util.Args;

public class BasicHttpRequest
extends AbstractHttpMessage
implements HttpRequest {
    private final String method;
    private final String uri;
    private RequestLine requestline;

    public BasicHttpRequest(String string, String string2) {
        this.method = Args.notNull(string, "Method name");
        this.uri = Args.notNull(string2, "Request URI");
        this.requestline = null;
    }

    public BasicHttpRequest(String string, String string2, ProtocolVersion protocolVersion) {
        this(new BasicRequestLine(string, string2, protocolVersion));
    }

    public BasicHttpRequest(RequestLine requestLine) {
        this.requestline = Args.notNull(requestLine, "Request line");
        this.method = requestLine.getMethod();
        this.uri = requestLine.getUri();
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        return this.getRequestLine().getProtocolVersion();
    }

    @Override
    public RequestLine getRequestLine() {
        if (this.requestline == null) {
            this.requestline = new BasicRequestLine(this.method, this.uri, HttpVersion.HTTP_1_1);
        }
        return this.requestline;
    }

    public String toString() {
        return this.method + ' ' + this.uri + ' ' + this.headergroup;
    }
}

