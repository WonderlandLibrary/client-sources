/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import org.apache.http.ProtocolException;

public class MalformedCookieException
extends ProtocolException {
    private static final long serialVersionUID = -6695462944287282185L;

    public MalformedCookieException() {
    }

    public MalformedCookieException(String string) {
        super(string);
    }

    public MalformedCookieException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

