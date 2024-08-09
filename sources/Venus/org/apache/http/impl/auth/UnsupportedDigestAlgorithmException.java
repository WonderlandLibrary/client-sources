/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

public class UnsupportedDigestAlgorithmException
extends RuntimeException {
    private static final long serialVersionUID = 319558534317118022L;

    public UnsupportedDigestAlgorithmException() {
    }

    public UnsupportedDigestAlgorithmException(String string) {
        super(string);
    }

    public UnsupportedDigestAlgorithmException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

