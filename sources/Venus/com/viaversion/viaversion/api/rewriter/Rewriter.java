/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;

public interface Rewriter<T extends Protocol> {
    public void register();

    public T protocol();

    default public void onMappingDataLoaded() {
    }
}

