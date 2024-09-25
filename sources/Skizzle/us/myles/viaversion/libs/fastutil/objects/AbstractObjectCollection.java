/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.fastutil.objects;

import java.util.AbstractCollection;
import java.util.Iterator;
import us.myles.viaversion.libs.fastutil.objects.ObjectCollection;
import us.myles.viaversion.libs.fastutil.objects.ObjectIterator;

public abstract class AbstractObjectCollection<K>
extends AbstractCollection<K>
implements ObjectCollection<K> {
    protected AbstractObjectCollection() {
    }

    @Override
    public abstract ObjectIterator<K> iterator();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            Object k = i.next();
            if (this == k) {
                s.append("(this collection)");
                continue;
            }
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

