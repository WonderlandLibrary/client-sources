/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;

public class ServerProtocolVersionRange
implements ServerProtocolVersion {
    private final int lowestSupportedVersion;
    private final int highestSupportedVersion;
    private final IntSortedSet supportedVersions;

    public ServerProtocolVersionRange(int n, int n2, IntSortedSet intSortedSet) {
        this.lowestSupportedVersion = n;
        this.highestSupportedVersion = n2;
        this.supportedVersions = intSortedSet;
    }

    @Override
    public int lowestSupportedVersion() {
        return this.lowestSupportedVersion;
    }

    @Override
    public int highestSupportedVersion() {
        return this.highestSupportedVersion;
    }

    @Override
    public IntSortedSet supportedVersions() {
        return this.supportedVersions;
    }
}

