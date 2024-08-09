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
public interface Reference2CharFunction<K>
extends Function<K, Character>,
ToIntFunction<K> {
    @Override
    default public int applyAsInt(K k) {
        return this.getChar(k);
    }

    @Override
    default public char put(K k, char c) {
        throw new UnsupportedOperationException();
    }

    public char getChar(Object var1);

    default public char removeChar(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public Character put(K k, Character c) {
        K k2 = k;
        boolean bl = this.containsKey(k2);
        char c2 = this.put(k2, c.charValue());
        return bl ? Character.valueOf(c2) : null;
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        Object object2 = object;
        char c = this.getChar(object2);
        return c != this.defaultReturnValue() || this.containsKey(object2) ? Character.valueOf(c) : null;
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        Object object2 = object;
        return this.containsKey(object2) ? Character.valueOf(this.removeChar(object2)) : null;
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
        return this.put((K)object, (Character)object2);
    }
}

