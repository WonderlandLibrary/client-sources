/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;

public class ProtocolPathKeyImpl
implements ProtocolPathKey {
    private final int clientProtocolVersion;
    private final int serverProtocolVersion;

    public ProtocolPathKeyImpl(int n, int n2) {
        this.clientProtocolVersion = n;
        this.serverProtocolVersion = n2;
    }

    @Override
    public int clientProtocolVersion() {
        return this.clientProtocolVersion;
    }

    @Override
    public int serverProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ProtocolPathKeyImpl protocolPathKeyImpl = (ProtocolPathKeyImpl)object;
        if (this.clientProtocolVersion != protocolPathKeyImpl.clientProtocolVersion) {
            return true;
        }
        return this.serverProtocolVersion == protocolPathKeyImpl.serverProtocolVersion;
    }

    public int hashCode() {
        int n = this.clientProtocolVersion;
        n = 31 * n + this.serverProtocolVersion;
        return n;
    }
}

