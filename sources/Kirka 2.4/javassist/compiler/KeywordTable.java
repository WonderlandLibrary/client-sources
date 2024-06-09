/*
 * Decompiled with CFR 0.143.
 */
package javassist.compiler;

import java.util.HashMap;

public final class KeywordTable
extends HashMap {
    public int lookup(String name) {
        Object found = this.get(name);
        if (found == null) {
            return -1;
        }
        return (Integer)found;
    }

    public void append(String name, int t) {
        this.put(name, new Integer(t));
    }
}

