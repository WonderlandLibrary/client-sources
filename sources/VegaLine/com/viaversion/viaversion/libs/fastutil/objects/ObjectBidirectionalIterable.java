/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterable;

public interface ObjectBidirectionalIterable<K>
extends ObjectIterable<K> {
    @Override
    public ObjectBidirectionalIterator<K> iterator();
}

