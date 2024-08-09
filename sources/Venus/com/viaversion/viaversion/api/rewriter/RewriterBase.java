/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.rewriter.Rewriter;

public abstract class RewriterBase<T extends Protocol>
implements Rewriter<T> {
    protected final T protocol;

    protected RewriterBase(T t) {
        this.protocol = t;
    }

    @Override
    public final void register() {
        this.registerPackets();
        this.registerRewrites();
    }

    protected void registerPackets() {
    }

    protected void registerRewrites() {
    }

    @Override
    public T protocol() {
        return this.protocol;
    }
}

