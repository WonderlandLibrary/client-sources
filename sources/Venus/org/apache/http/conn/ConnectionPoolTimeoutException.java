/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import org.apache.http.conn.ConnectTimeoutException;

public class ConnectionPoolTimeoutException
extends ConnectTimeoutException {
    private static final long serialVersionUID = -7898874842020245128L;

    public ConnectionPoolTimeoutException() {
    }

    public ConnectionPoolTimeoutException(String string) {
        super(string);
    }
}

