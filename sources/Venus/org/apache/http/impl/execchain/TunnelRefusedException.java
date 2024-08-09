/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

public class TunnelRefusedException
extends HttpException {
    private static final long serialVersionUID = -8646722842745617323L;
    private final HttpResponse response;

    public TunnelRefusedException(String string, HttpResponse httpResponse) {
        super(string);
        this.response = httpResponse;
    }

    public HttpResponse getResponse() {
        return this.response;
    }
}

