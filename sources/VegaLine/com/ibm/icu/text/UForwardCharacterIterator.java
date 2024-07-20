/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

public interface UForwardCharacterIterator {
    public static final int DONE = -1;

    public int next();

    public int nextCodePoint();
}

