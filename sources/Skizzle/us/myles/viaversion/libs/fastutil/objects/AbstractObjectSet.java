/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.util.Iterator;
import java.util.Set;
import us.myles.viaversion.libs.fastutil.objects.AbstractObjectCollection;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterator;
import us.myles.viaversion.libs.fastutil.objects.ObjectSet;

public abstract class AbstractObjectSet<K>
extends AbstractObjectCollection<K>
implements Cloneable,
ObjectSet<K> {
    protected AbstractObjectSet() {
    }

    @Override
    public abstract ObjectIterator<K> iterator();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Set)) {
            return false;
        }
        Set s = (Set)o;
        if (s.size() != this.size()) {
            return false;
        }
        return this.containsAll(s);
    }

    @Override
    public int hashCode() {
        int h = 0;
        int n = this.size();
        Iterator i = this.iterator();
        while (n-- != 0) {
            Object k = i.next();
            h += k == null ? 0 : k.hashCode();
        }
        return h;
    }
}

