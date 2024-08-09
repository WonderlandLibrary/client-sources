/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import java.net.ConnectException;

public class ConnectTimeoutException
extends ConnectException {
    private static final long serialVersionUID = 2317065249988317463L;

    public ConnectTimeoutException(String string) {
        super(string);
    }

    public ConnectTimeoutException() {
    }
}

