/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import us.myles.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterable;

public interface ObjectBidirectionalIterable<K>
extends ObjectIterable<K> {
    @Override
    public ObjectBidirectionalIterator<K> iterator();
}

