/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.SafeMath;
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Char2LongFunction
extends Function<Character, Long>,
IntToLongFunction {
    @Override
    @Deprecated
    default public long applyAsLong(int n) {
        return this.get(SafeMath.safeIntToChar(n));
    }

    @Override
    default public long put(char c, long l) {
        throw new UnsupportedOperationException();
    }

    public long get(char var1);

    default public long remove(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(Character c, Long l) {
        char c2 = c.charValue();
        boolean bl = this.containsKey(c2);
        long l2 = this.put(c2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        long l = this.get(c);
        return l != this.defaultReturnValue() || this.containsKey(c) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        if (object == null) {
            return null;
        }
        char c = ((Character)object).charValue();
        return this.containsKey(c) ? Long.valueOf(this.remove(c)) : null;
    }

    default public boolean containsKey(char c) {
        return false;
    }

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return object == null ? false : this.containsKey(((Character)object).charValue());
    }

    default public void defaultReturnValue(long l) {
        throw new UnsupportedOperationException();
    }

    default public long defaultReturnValue() {
        return 0L;
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
        return this.put((Character)object, (Long)object2);
    }
}

