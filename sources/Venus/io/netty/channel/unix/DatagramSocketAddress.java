/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import java.net.InetSocketAddress;

public final class DatagramSocketAddress
extends InetSocketAddress {
    private static final long serialVersionUID = 3094819287843178401L;
    private final int receivedAmount;
    private final DatagramSocketAddress localAddress;

    DatagramSocketAddress(String string, int n, int n2, DatagramSocketAddress datagramSocketAddress) {
        super(string, n);
        this.receivedAmount = n2;
        this.localAddress = datagramSocketAddress;
    }

    public DatagramSocketAddress localAddress() {
        return this.localAddress;
    }

    public int receivedAmount() {
        return this.receivedAmount;
    }
}

