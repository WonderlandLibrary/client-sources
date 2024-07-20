/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Platform;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public abstract class Ticker {
    private static final Ticker SYSTEM_TICKER = new Ticker(){

        @Override
        public long read() {
            return Platform.systemNanoTime();
        }
    };

    protected Ticker() {
    }

    @CanIgnoreReturnValue
    public abstract long read();

    public static Ticker systemTicker() {
        return SYSTEM_TICKER;
    }
}

