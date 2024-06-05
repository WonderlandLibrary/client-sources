/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;

public class ProtocolPathKeyImpl
implements ProtocolPathKey {
    private final int clientProtocolVersion;
    private final int serverProtocolVersion;

    public ProtocolPathKeyImpl(int clientProtocolVersion, int serverProtocolVersion) {
        this.clientProtocolVersion = clientProtocolVersion;
        this.serverProtocolVersion = serverProtocolVersion;
    }

    @Override
    public int clientProtocolVersion() {
        return this.clientProtocolVersion;
    }

    @Override
    public int serverProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) return false;
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ProtocolPathKeyImpl that = (ProtocolPathKeyImpl)o;
        if (this.clientProtocolVersion != that.clientProtocolVersion) {
            return false;
        }
        if (this.serverProtocolVersion != that.serverProtocolVersion) return false;
        return true;
    }

    public int hashCode() {
        int result = this.clientProtocolVersion;
        return 31 * result + this.serverProtocolVersion;
    }
}

