/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntConsumer;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;

public abstract class AbstractIntSpliterator
implements IntSpliterator {
    protected AbstractIntSpliterator() {
    }

    @Override
    public final boolean tryAdvance(IntConsumer intConsumer) {
        return this.tryAdvance((java.util.function.IntConsumer)intConsumer);
    }

    @Override
    public final void forEachRemaining(IntConsumer intConsumer) {
        this.forEachRemaining((java.util.function.IntConsumer)intConsumer);
    }
}

