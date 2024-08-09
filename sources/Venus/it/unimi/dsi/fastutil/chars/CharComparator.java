/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import java.util.Comparator;

@FunctionalInterface
public interface CharComparator
extends Comparator<Character> {
    @Override
    public int compare(char var1, char var2);

    @Override
    @Deprecated
    default public int compare(Character c, Character c2) {
        return this.compare(c.charValue(), c2.charValue());
    }

    @Override
    @Deprecated
    default public int compare(Object object, Object object2) {
        return this.compare((Character)object, (Character)object2);
    }
}

