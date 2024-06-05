/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.compiler;

import java.util.HashMap;

public final class KeywordTable
extends HashMap<String, Integer> {
    private static final long serialVersionUID = 1L;

    public int lookup(String name) {
        if (!this.containsKey(name)) return -1;
        int n = (Integer)this.get(name);
        return n;
    }

    public void append(String name, int t) {
        this.put(name, t);
    }
}

