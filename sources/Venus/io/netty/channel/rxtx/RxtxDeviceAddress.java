/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.rxtx;

import java.net.SocketAddress;

@Deprecated
public class RxtxDeviceAddress
extends SocketAddress {
    private static final long serialVersionUID = -2907820090993709523L;
    private final String value;

    public RxtxDeviceAddress(String string) {
        this.value = string;
    }

    public String value() {
        return this.value;
    }
}

