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
    public void countBytes(int n) {
    }

    @Override
    public void checkLevel(int n) {
    }

    @Override
    public int maxBytes() {
        return 0;
    }

    @Override
    public int maxLevels() {
        return 0;
    }

    @Override
    public int bytes() {
        return 1;
    }
}

