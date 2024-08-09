/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpPatch
extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "PATCH";

    public HttpPatch() {
    }

    public HttpPatch(URI uRI) {
        this.setURI(uRI);
    }

    public HttpPatch(String string) {
        this.setURI(URI.create(string));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

