/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;

public interface ProtocolPathEntry {
    public int outputProtocolVersion();

    public Protocol<?, ?, ?, ?> protocol();

    @Deprecated
    default public int getOutputProtocolVersion() {
        return this.outputProtocolVersion();
    }

    @Deprecated
    default public Protocol getProtocol() {
        return this.protocol();
    }
}

