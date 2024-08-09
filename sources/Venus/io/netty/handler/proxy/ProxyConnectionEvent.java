/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

public final class ProxyConnectionEvent {
    private final String protocol;
    private final String authScheme;
    private final SocketAddress proxyAddress;
    private final SocketAddress destinationAddress;
    private String strVal;

    public ProxyConnectionEvent(String string, String string2, SocketAddress socketAddress, SocketAddress socketAddress2) {
        if (string == null) {
            throw new NullPointerException("protocol");
        }
        if (string2 == null) {
            throw new NullPointerException("authScheme");
        }
        if (socketAddress == null) {
            throw new NullPointerException("proxyAddress");
        }
        if (socketAddress2 == null) {
            throw new NullPointerException("destinationAddress");
        }
        this.protocol = string;
        this.authScheme = string2;
        this.proxyAddress = socketAddress;
        this.destinationAddress = socketAddress2;
    }

    public String protocol() {
        return this.protocol;
    }

    public String authScheme() {
        return this.authScheme;
    }

    public <T extends SocketAddress> T proxyAddress() {
        return (T)this.proxyAddress;
    }

    public <T extends SocketAddress> T destinationAddress() {
        return (T)this.destinationAddress;
    }

    public String toString() {
        if (this.strVal != null) {
            return this.strVal;
        }
        StringBuilder stringBuilder = new StringBuilder(128).append(StringUtil.simpleClassName(this)).append('(').append(this.protocol).append(", ").append(this.authScheme).append(", ").append(this.proxyAddress).append(" => ").append(this.destinationAddress).append(')');
        this.strVal = stringBuilder.toString();
        return this.strVal;
    }
}

