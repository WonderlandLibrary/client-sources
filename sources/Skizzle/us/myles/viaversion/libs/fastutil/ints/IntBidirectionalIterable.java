/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.ints;

import us.myles.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import us.myles.viaversion.libs.fastutil.ints.IntIterable;

public interface IntBidirectionalIterable
extends IntIterable {
    @Override
    public IntBidirectionalIterator iterator();
}

