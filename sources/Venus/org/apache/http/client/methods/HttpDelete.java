/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpDelete
extends HttpRequestBase {
    public static final String METHOD_NAME = "DELETE";

    public HttpDelete() {
    }

    public HttpDelete(URI uRI) {
        this.setURI(uRI);
    }

    public HttpDelete(String string) {
        this.setURI(URI.create(string));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

