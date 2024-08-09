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
public interface Reference2ByteFunction<K>
extends Function<K, Byte>,
ToIntFunction<K> {
    @Override
    default public int applyAsInt(K k) {
        return this.getByte(k);
    }

    @Override
    default public byte put(K k, byte by) {
        throw new UnsupportedOperationException();
    }

    public byte getByte(Object var1);

    default public byte removeByte(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Byte put(K k, Byte by) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        byte by2 = this.put(k2, (byte)by);
        return bl ? Byte.valueOf(by2) : null;
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        Object object2 = object;
        byte by = this.getByte(object2);
        return by != this.defaultReturnValue() || this.containsKey(object2) ? Byte.valueOf(by) : null;
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Byte.valueOf(this.removeByte(object2)) : null;
    }

    default public void defaultReturnValue(byte by) {
        throw new UnsupportedOperationException();
    }

    default public byte defaultReturnValue() {
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
        return this.put((K)object, (Byte)object2);
    }
}

