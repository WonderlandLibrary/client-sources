/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.model;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import via.ViaLoadingBase;

public class ComparableProtocolVersion
extends ProtocolVersion {
    private final int index;

    public ComparableProtocolVersion(int n, String string, int n2) {
        super(n, string);
        this.index = n2;
    }

    public boolean isOlderThan(ProtocolVersion protocolVersion) {
        return this.getIndex() > ViaLoadingBase.fromProtocolVersion(protocolVersion).getIndex();
    }

    public boolean isOlderThanOrEqualTo(ProtocolVersion protocolVersion) {
        return this.getIndex() >= ViaLoadingBase.fromProtocolVersion(protocolVersion).getIndex();
    }

    public boolean isNewerThan(ProtocolVersion protocolVersion) {
        return this.getIndex() < ViaLoadingBase.fromProtocolVersion(protocolVersion).getIndex();
    }

    public boolean isNewerThanOrEqualTo(ProtocolVersion protocolVersion) {
        return this.getIndex() <= ViaLoadingBase.fromProtocolVersion(protocolVersion).getIndex();
    }

    public boolean isEqualTo(ProtocolVersion protocolVersion) {
        return this.getIndex() == ViaLoadingBase.fromProtocolVersion(protocolVersion).getIndex();
    }

    public int getIndex() {
        return this.index;
    }
}

