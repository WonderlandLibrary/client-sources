/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt.tag.limiter;

import com.viaversion.viaversion.libs.opennbt.tag.limiter.NoopTagLimiter;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiterImpl;

public interface TagLimiter {
    public static TagLimiter create(int maxBytes, int maxLevels) {
        return new TagLimiterImpl(maxBytes, maxLevels);
    }

    public static TagLimiter noop() {
        return NoopTagLimiter.INSTANCE;
    }

    public void countBytes(int var1);

    public void checkLevel(int var1);

    default public void countByte() {
        this.countBytes(1);
    }

    default public void countShort() {
        this.countBytes(2);
    }

    default public void countInt() {
        this.countBytes(4);
    }

    default public void countFloat() {
        this.countBytes(4);
    }

    default public void countLong() {
        this.countBytes(8);
    }

    default public void countDouble() {
        this.countBytes(8);
    }

    public int maxBytes();

    public int maxLevels();

    public int bytes();
}

