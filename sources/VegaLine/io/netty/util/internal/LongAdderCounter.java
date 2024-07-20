/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.LongCounter;
import java.util.concurrent.atomic.LongAdder;

final class LongAdderCounter
extends LongAdder
implements LongCounter {
    LongAdderCounter() {
    }

    @Override
    public long value() {
        return this.longValue();
    }
}

