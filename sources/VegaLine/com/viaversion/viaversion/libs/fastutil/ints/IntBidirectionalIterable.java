/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterable;

public interface IntBidirectionalIterable
extends IntIterable {
    @Override
    public IntBidirectionalIterator iterator();
}

