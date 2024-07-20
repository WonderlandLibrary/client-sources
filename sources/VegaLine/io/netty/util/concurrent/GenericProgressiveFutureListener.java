/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ProgressiveFuture;

public interface GenericProgressiveFutureListener<F extends ProgressiveFuture<?>>
extends GenericFutureListener<F> {
    public void operationProgressed(F var1, long var2, long var4) throws Exception;
}

