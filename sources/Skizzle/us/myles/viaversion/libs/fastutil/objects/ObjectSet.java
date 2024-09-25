/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.util.Set;
import us.myles.viaversion.libs.fastutil.objects.ObjectCollection;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterator;

public interface ObjectSet<K>
extends ObjectCollection<K>,
Set<K> {
    @Override
    public ObjectIterator<K> iterator();
}

