/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import java.text.CharacterIterator;

abstract class DictionaryMatcher {
    DictionaryMatcher() {
    }

    public abstract int matches(CharacterIterator var1, int var2, int[] var3, int[] var4, int var5, int[] var6);

    public int matches(CharacterIterator characterIterator, int n, int[] nArray, int[] nArray2, int n2) {
        return this.matches(characterIterator, n, nArray, nArray2, n2, null);
    }

    public abstract int getType();
}

