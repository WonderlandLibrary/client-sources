/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public class BlockedProtocolVersionsImpl
implements BlockedProtocolVersions {
    private final IntSet singleBlockedVersions;
    private final int blocksBelow;
    private final int blocksAbove;

    public BlockedProtocolVersionsImpl(IntSet intSet, int n, int n2) {
        this.singleBlockedVersions = intSet;
        this.blocksBelow = n;
        this.blocksAbove = n2;
    }

    @Override
    public boolean contains(int n) {
        return this.blocksBelow != -1 && n < this.blocksBelow || this.blocksAbove != -1 && n > this.blocksAbove || this.singleBlockedVersions.contains(n);
    }

    @Override
    public int blocksBelow() {
        return this.blocksBelow;
    }

    @Override
    public int blocksAbove() {
        return this.blocksAbove;
    }

    @Override
    public IntSet singleBlockedVersions() {
        return this.singleBlockedVersions;
    }
}

