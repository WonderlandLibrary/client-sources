/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.embedded;

import java.net.SocketAddress;

final class EmbeddedSocketAddress
extends SocketAddress {
    private static final long serialVersionUID = 1400788804624980619L;

    EmbeddedSocketAddress() {
    }

    public String toString() {
        return "embedded";
    }
}

