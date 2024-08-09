/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

public interface RangeValueIterator {
    public boolean next(Element var1);

    public void reset();

    public static class Element {
        public int start;
        public int limit;
        public int value;
    }
}

