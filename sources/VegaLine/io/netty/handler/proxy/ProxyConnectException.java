/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import java.net.ConnectException;

public class ProxyConnectException
extends ConnectException {
    private static final long serialVersionUID = 5211364632246265538L;

    public ProxyConnectException() {
    }

    public ProxyConnectException(String msg) {
        super(msg);
    }

    public ProxyConnectException(Throwable cause) {
        this.initCause(cause);
    }

    public ProxyConnectException(String msg, Throwable cause) {
        super(msg);
        this.initCause(cause);
    }
}

