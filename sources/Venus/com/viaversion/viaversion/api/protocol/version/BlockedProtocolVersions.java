/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public interface BlockedProtocolVersions {
    public boolean contains(int var1);

    public int blocksBelow();

    public int blocksAbove();

    public IntSet singleBlockedVersions();
}

