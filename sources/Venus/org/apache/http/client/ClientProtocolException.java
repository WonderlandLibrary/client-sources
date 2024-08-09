/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import java.io.IOException;

public class ClientProtocolException
extends IOException {
    private static final long serialVersionUID = -5596590843227115865L;

    public ClientProtocolException() {
    }

    public ClientProtocolException(String string) {
        super(string);
    }

    public ClientProtocolException(Throwable throwable) {
        this.initCause(throwable);
    }

    public ClientProtocolException(String string, Throwable throwable) {
        super(string);
        this.initCause(throwable);
    }
}

