/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public interface AttributeMap {
    public <T> Attribute<T> attr(AttributeKey<T> var1);

    public <T> boolean hasAttr(AttributeKey<T> var1);
}

