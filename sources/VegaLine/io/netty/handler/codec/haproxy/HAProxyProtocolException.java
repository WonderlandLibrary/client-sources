/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

import io.netty.handler.codec.DecoderException;

public class HAProxyProtocolException
extends DecoderException {
    private static final long serialVersionUID = 713710864325167351L;

    public HAProxyProtocolException() {
    }

    public HAProxyProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public HAProxyProtocolException(String message) {
        super(message);
    }

    public HAProxyProtocolException(Throwable cause) {
        super(cause);
    }
}

