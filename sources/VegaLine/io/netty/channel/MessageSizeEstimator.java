/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

public interface MessageSizeEstimator {
    public Handle newHandle();

    public static interface Handle {
        public int size(Object var1);
    }
}

