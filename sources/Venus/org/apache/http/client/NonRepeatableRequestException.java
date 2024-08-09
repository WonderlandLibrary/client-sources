/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.ProtocolException;

public class NonRepeatableRequestException
extends ProtocolException {
    private static final long serialVersionUID = 82685265288806048L;

    public NonRepeatableRequestException() {
    }

    public NonRepeatableRequestException(String string) {
        super(string);
    }

    public NonRepeatableRequestException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

