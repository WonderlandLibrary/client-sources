/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Function;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@FunctionalInterface
public interface Object2BooleanFunction<K>
extends Function<K, Boolean>,
Predicate<K> {
    @Override
    default public boolean test(K k) {
        return this.getBoolean(k);
    }

    @Override
    default public boolean put(K k, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public boolean getBoolean(Object var1);

    default public boolean removeBoolean(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Boolean put(K k, Boolean bl) {
        K k2 = k;
        boolean bl2 = this.containsKey(k2);
        boolean bl3 = this.put(k2, (boolean)bl);
        return bl2 ? Boolean.valueOf(bl3) : null;
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        Object object2 = object;
        boolean bl = this.getBoolean(object2);
        return bl != this.defaultReturnValue() || this.containsKey(object2) ? Boolean.valueOf(bl) : null;
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Boolean.valueOf(this.removeBoolean(object2)) : null;
    }

    default public void defaultReturnValue(boolean bl) {
        throw new UnsupportedOperationException();
    }

    default public boolean defaultReturnValue() {
        return true;
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
        return this.put((K)object, (Boolean)object2);
    }
}

