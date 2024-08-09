/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloatSet
extends AbstractFloatCollection
implements Cloneable,
FloatSet {
    protected AbstractFloatSet() {
    }

    @Override
    public abstract FloatIterator iterator();

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof Set)) {
            return true;
        }
        Set set = (Set)object;
        if (set.size() != this.size()) {
            return true;
        }
        return this.containsAll(set);
    }

    @Override
    public int hashCode() {
        int n = 0;
        int n2 = this.size();
        FloatIterator floatIterator = this.iterator();
        while (n2-- != 0) {
            float f = floatIterator.nextFloat();
            n += HashCommon.float2int(f);
        }
        return n;
    }

    @Override
    public boolean remove(float f) {
        return super.rem(f);
    }

    @Override
    @Deprecated
    public boolean rem(float f) {
        return this.remove(f);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

