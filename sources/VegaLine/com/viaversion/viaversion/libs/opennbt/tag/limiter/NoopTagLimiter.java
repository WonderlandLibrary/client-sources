/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.limiter;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;

final class NoopTagLimiter
implements TagLimiter {
    static final TagLimiter INSTANCE = new NoopTagLimiter();

    NoopTagLimiter() {
    }

    @Override
    public void countBytes(int bytes) {
    }

    @Override
    public void checkLevel(int nestedLevel) {
    }

    @Override
    public int maxBytes() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int maxLevels() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int bytes() {
        return 0;
    }
}

