/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.ToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Reference2LongFunction<K>
extends Function<K, Long>,
ToLongFunction<K> {
    @Override
    default public long applyAsLong(K k) {
        return this.getLong(k);
    }

    @Override
    default public long put(K k, long l) {
        throw new UnsupportedOperationException();
    }

    public long getLong(Object var1);

    default public long removeLong(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Long put(K k, Long l) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        long l2 = this.put(k2, (long)l);
        return bl ? Long.valueOf(l2) : null;
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        Object object2 = object;
        long l = this.getLong(object2);
        return l != this.defaultReturnValue() || this.containsKey(object2) ? Long.valueOf(l) : null;
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Long.valueOf(this.removeLong(object2)) : null;
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
        return this.put((K)object, (Long)object2);
    }
}

