/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;

public interface ServerProtocolVersion {
    public int lowestSupportedVersion();

    public int highestSupportedVersion();

    public IntSortedSet supportedVersions();

    default public boolean isKnown() {
        if (this.lowestSupportedVersion() == -1) return false;
        if (this.highestSupportedVersion() == -1) return false;
        return true;
    }
}

