/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.TextUtils;

public class HttpResponseException
extends ClientProtocolException {
    private static final long serialVersionUID = -7186627969477257933L;
    private final int statusCode;
    private final String reasonPhrase;

    public HttpResponseException(int n, String string) {
        super(String.format("status code: %d" + (TextUtils.isBlank(string) ? "" : ", reason phrase: %s"), n, string));
        this.statusCode = n;
        this.reasonPhrase = string;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}

