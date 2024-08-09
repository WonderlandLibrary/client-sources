/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.IOException;
import org.apache.http.HttpException;

public class ConnectionClosedException
extends IOException {
    private static final long serialVersionUID = 617550366255636674L;

    public ConnectionClosedException() {
        super("Connection is closed");
    }

    public ConnectionClosedException(String string) {
        super(HttpException.clean(string));
    }

    public ConnectionClosedException(String string, Object ... objectArray) {
        super(HttpException.clean(String.format(string, objectArray)));
    }
}

