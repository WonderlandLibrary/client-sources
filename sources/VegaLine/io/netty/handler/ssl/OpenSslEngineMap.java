/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;

interface OpenSslEngineMap {
    public ReferenceCountedOpenSslEngine remove(long var1);

    public void add(ReferenceCountedOpenSslEngine var1);

    public ReferenceCountedOpenSslEngine get(long var1);
}

