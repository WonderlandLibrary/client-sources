/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.version;

import com.google.common.base.Preconditions;

public class VersionRange {
    private final String baseVersion;
    private final int rangeFrom;
    private final int rangeTo;

    public VersionRange(String string, int n, int n2) {
        Preconditions.checkNotNull(string);
        Preconditions.checkArgument(n >= 0);
        Preconditions.checkArgument(n2 > n);
        this.baseVersion = string;
        this.rangeFrom = n;
        this.rangeTo = n2;
    }

    public String baseVersion() {
        return this.baseVersion;
    }

    public int rangeFrom() {
        return this.rangeFrom;
    }

    public int rangeTo() {
        return this.rangeTo;
    }

    @Deprecated
    public String getBaseVersion() {
        return this.baseVersion;
    }

    @Deprecated
    public int getRangeFrom() {
        return this.rangeFrom;
    }

    @Deprecated
    public int getRangeTo() {
        return this.rangeTo;
    }
}

