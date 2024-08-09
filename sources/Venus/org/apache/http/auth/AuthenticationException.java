/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import org.apache.http.ProtocolException;

public class AuthenticationException
extends ProtocolException {
    private static final long serialVersionUID = -6794031905674764776L;

    public AuthenticationException() {
    }

    public AuthenticationException(String string) {
        super(string);
    }

    public AuthenticationException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

