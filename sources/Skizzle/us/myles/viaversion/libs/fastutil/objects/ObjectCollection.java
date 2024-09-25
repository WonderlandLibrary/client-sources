/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.util.Collection;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterable;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterator;

public interface ObjectCollection<K>
extends Collection<K>,
ObjectIterable<K> {
    @Override
    public ObjectIterator<K> iterator();
}

