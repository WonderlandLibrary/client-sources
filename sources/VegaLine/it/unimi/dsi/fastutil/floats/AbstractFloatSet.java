/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import java.util.Set;

public abstract class AbstractFloatSet
extends AbstractFloatCollection
implements Cloneable,
FloatSet {
    protected AbstractFloatSet() {
    }

    @Override
    public abstract FloatIterator iterator();

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
        FloatIterator i = this.iterator();
        while (n-- != 0) {
            float k = i.nextFloat();
            h += HashCommon.float2int(k);
        }
        return h;
    }

    @Override
    public boolean remove(float k) {
        return this.rem(k);
    }
}

