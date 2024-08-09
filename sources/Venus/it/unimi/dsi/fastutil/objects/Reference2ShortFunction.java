/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Reference2ShortFunction<K>
extends Function<K, Short>,
ToIntFunction<K> {
    @Override
    default public int applyAsInt(K k) {
        return this.getShort(k);
    }

    @Override
    default public short put(K k, short s) {
        throw new UnsupportedOperationException();
    }

    public short getShort(Object var1);

    default public short removeShort(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Short put(K k, Short s) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        short s2 = this.put(k2, (short)s);
        return bl ? Short.valueOf(s2) : null;
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        Object object2 = object;
        short s = this.getShort(object2);
        return s != this.defaultReturnValue() || this.containsKey(object2) ? Short.valueOf(s) : null;
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Short.valueOf(this.removeShort(object2)) : null;
    }

    default public void defaultReturnValue(short s) {
        throw new UnsupportedOperationException();
    }

    default public short defaultReturnValue() {
        return 1;
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
        return this.put((K)object, (Short)object2);
    }
}

