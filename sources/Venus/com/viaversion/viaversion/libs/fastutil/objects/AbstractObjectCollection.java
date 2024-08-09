/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import java.util.AbstractCollection;
import java.util.Iterator;

public abstract class AbstractObjectCollection<K>
extends AbstractCollection<K>
implements ObjectCollection<K> {
    protected AbstractObjectCollection() {
    }

    @Override
    public abstract ObjectIterator<K> iterator();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator2 = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            Object e = iterator2.next();
            if (this == e) {
                stringBuilder.append("(this collection)");
                continue;
            }
            stringBuilder.append(String.valueOf(e));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

