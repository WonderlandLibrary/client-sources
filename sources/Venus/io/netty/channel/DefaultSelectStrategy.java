/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.SelectStrategy;
import io.netty.util.IntSupplier;

final class DefaultSelectStrategy
implements SelectStrategy {
    static final SelectStrategy INSTANCE = new DefaultSelectStrategy();

    private DefaultSelectStrategy() {
    }

    @Override
    public int calculateStrategy(IntSupplier intSupplier, boolean bl) throws Exception {
        return bl ? intSupplier.get() : -1;
    }
}

