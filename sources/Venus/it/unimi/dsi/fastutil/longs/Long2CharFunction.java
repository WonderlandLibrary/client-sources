/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Function;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Long2CharFunction
extends Function<Long, Character>,
LongToIntFunction {
    @Override
    default public int applyAsInt(long l) {
        return this.get(l);
    }

    @Override
    default public char put(long l, char c) {
        throw new UnsupportedOperationException();
    }

    public char get(long var1);

    default public char remove(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(Long l, Character c) {
        long l2 = l;
        boolean bl = this.containsKey(l2);
        char c2 = this.put(l2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        char c = this.get(l);
        return c != this.defaultReturnValue() || this.containsKey(l) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        if (object == null) {
            return null;
        }
        long l = (Long)object;
        return this.containsKey(l) ? Character.valueOf(this.remove(l)) : null;
    }

    default public boolean containsKey(long l) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey((Long)object);
    }

    default public void defaultReturnValue(char c) {
        throw new UnsupportedOperationException();
    }

    default public char defaultReturnValue() {
        return '\u0001';
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Long)object, (Character)object2);
    }
}

