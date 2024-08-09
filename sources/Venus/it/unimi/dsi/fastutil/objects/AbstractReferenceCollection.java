/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.AbstractCollection;
import java.util.Iterator;

public abstract class AbstractReferenceCollection<K>
extends AbstractCollection<K>
implements ReferenceCollection<K> {
    protected AbstractReferenceCollection() {
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

