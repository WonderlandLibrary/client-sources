/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SpreadBuilder {
    private final ArrayList<Object> list;

    public SpreadBuilder(int n) {
        this.list = new ArrayList(n);
    }

    public void addSpread(Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof Object[]) {
            Object[] objectArray = (Object[])object;
            if (objectArray.length > 0) {
                this.list.ensureCapacity(this.list.size() + objectArray.length);
                Collections.addAll(this.list, objectArray);
            }
        } else if (object instanceof Collection) {
            this.list.addAll((Collection)object);
        } else if (object instanceof Iterable) {
            for (Object t : (Iterable)object) {
                this.list.add(t);
            }
        } else if (object instanceof Iterator) {
            Iterator iterator2 = (Iterator)object;
            while (iterator2.hasNext()) {
                this.list.add(iterator2.next());
            }
        } else {
            throw new UnsupportedOperationException("Don't know how to spread " + object.getClass());
        }
    }

    public int size() {
        return this.list.size();
    }

    public void add(Object object) {
        this.list.add(object);
    }

    public Object[] toArray(Object[] objectArray) {
        return this.list.toArray(objectArray);
    }
}

