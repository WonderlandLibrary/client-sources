/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.IObjectIntIterable;

public class ObjectIntIdentityMap
implements IObjectIntIterable {
    private final IdentityHashMap identityMap = new IdentityHashMap(512);
    private final List objectList = Lists.newArrayList();
    private static final String __OBFID = "CL_00001203";

    public void put(Object key, int value) {
        this.identityMap.put(key, value);
        while (true) {
            if (this.objectList.size() > value) {
                this.objectList.set(value, key);
                return;
            }
            this.objectList.add(null);
        }
    }

    public int get(Object key) {
        Integer integer = (Integer)this.identityMap.get(key);
        if (integer == null) {
            return -1;
        }
        int n = integer;
        return n;
    }

    public final Object getByValue(int value) {
        if (value < 0) return null;
        if (value >= this.objectList.size()) return null;
        Object v0 = this.objectList.get(value);
        return v0;
    }

    @Override
    public Iterator iterator() {
        return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
    }

    public List getObjectList() {
        return this.objectList;
    }
}

