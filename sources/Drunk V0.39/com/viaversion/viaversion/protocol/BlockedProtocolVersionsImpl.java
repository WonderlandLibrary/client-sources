/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public class BlockedProtocolVersionsImpl
implements BlockedProtocolVersions {
    private final IntSet singleBlockedVersions;
    private final int blocksBelow;
    private final int blocksAbove;

    public BlockedProtocolVersionsImpl(IntSet singleBlockedVersions, int blocksBelow, int blocksAbove) {
        this.singleBlockedVersions = singleBlockedVersions;
        this.blocksBelow = blocksBelow;
        this.blocksAbove = blocksAbove;
    }

    @Override
    public boolean contains(int protocolVersion) {
        if (this.blocksBelow != -1) {
            if (protocolVersion < this.blocksBelow) return true;
        }
        if (this.blocksAbove != -1) {
            if (protocolVersion > this.blocksAbove) return true;
        }
        if (this.singleBlockedVersions.contains(protocolVersion)) return true;
        return false;
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

