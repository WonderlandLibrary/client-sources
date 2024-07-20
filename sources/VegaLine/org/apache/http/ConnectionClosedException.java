/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.IOException;

public class ConnectionClosedException
extends IOException {
    private static final long serialVersionUID = 617550366255636674L;

    public ConnectionClosedException(String message) {
        super(message);
    }
}

