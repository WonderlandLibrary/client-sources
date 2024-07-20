/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

public interface ProtocolPathKey {
    public int clientProtocolVersion();

    public int serverProtocolVersion();

    @Deprecated
    default public int getClientProtocolVersion() {
        return this.clientProtocolVersion();
    }

    @Deprecated
    default public int getServerProtocolVersion() {
        return this.serverProtocolVersion();
    }
}

