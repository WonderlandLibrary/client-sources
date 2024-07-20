/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.model;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.intave.vialoadingbase.ViaLoadingBase;
import dev.intave.vialoadingbase.model.ComparableProtocolVersion;

public class ProtocolRange {
    private final ComparableProtocolVersion lowerBound;
    private final ComparableProtocolVersion upperBound;

    public ProtocolRange(ProtocolVersion lowerBound, ProtocolVersion upperBound) {
        this(ViaLoadingBase.fromProtocolVersion(lowerBound), ViaLoadingBase.fromProtocolVersion(upperBound));
    }

    public ProtocolRange(ComparableProtocolVersion lowerBound, ComparableProtocolVersion upperBound) {
        if (lowerBound == null && upperBound == null) {
            throw new RuntimeException("Invalid protocol range");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static ProtocolRange andNewer(ProtocolVersion version) {
        return new ProtocolRange(null, version);
    }

    public static ProtocolRange singleton(ProtocolVersion version) {
        return new ProtocolRange(version, version);
    }

    public static ProtocolRange andOlder(ProtocolVersion version) {
        return new ProtocolRange(version, null);
    }

    public boolean contains(ComparableProtocolVersion protocolVersion) {
        if (this.lowerBound != null && protocolVersion.getIndex() < this.lowerBound.getIndex()) {
            return false;
        }
        return this.upperBound == null || protocolVersion.getIndex() <= this.upperBound.getIndex();
    }

    public String toString() {
        if (this.lowerBound == null) {
            return this.upperBound.getName() + "+";
        }
        if (this.upperBound == null) {
            return this.lowerBound.getName() + "-";
        }
        if (this.lowerBound == this.upperBound) {
            return this.lowerBound.getName();
        }
        return this.lowerBound.getName() + " - " + this.upperBound.getName();
    }
}

