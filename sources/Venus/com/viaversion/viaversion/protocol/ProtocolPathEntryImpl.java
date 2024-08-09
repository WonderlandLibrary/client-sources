/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;

public class ProtocolPathEntryImpl
implements ProtocolPathEntry {
    private final int outputProtocolVersion;
    private final Protocol<?, ?, ?, ?> protocol;

    public ProtocolPathEntryImpl(int n, Protocol<?, ?, ?, ?> protocol) {
        this.outputProtocolVersion = n;
        this.protocol = protocol;
    }

    @Override
    public int outputProtocolVersion() {
        return this.outputProtocolVersion;
    }

    @Override
    public Protocol<?, ?, ?, ?> protocol() {
        return this.protocol;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ProtocolPathEntryImpl protocolPathEntryImpl = (ProtocolPathEntryImpl)object;
        if (this.outputProtocolVersion != protocolPathEntryImpl.outputProtocolVersion) {
            return true;
        }
        return this.protocol.equals(protocolPathEntryImpl.protocol);
    }

    public int hashCode() {
        int n = this.outputProtocolVersion;
        n = 31 * n + this.protocol.hashCode();
        return n;
    }

    public String toString() {
        return "ProtocolPathEntryImpl{outputProtocolVersion=" + this.outputProtocolVersion + ", protocol=" + this.protocol + '}';
    }
}

