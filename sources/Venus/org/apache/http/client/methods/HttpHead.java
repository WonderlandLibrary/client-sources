/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpHead
extends HttpRequestBase {
    public static final String METHOD_NAME = "HEAD";

    public HttpHead() {
    }

    public HttpHead(URI uRI) {
        this.setURI(uRI);
    }

    public HttpHead(String string) {
        this.setURI(URI.create(string));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

