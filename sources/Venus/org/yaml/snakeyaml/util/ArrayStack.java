/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.util;

import java.util.ArrayList;

public class ArrayStack<T> {
    private final ArrayList<T> stack;

    public ArrayStack(int n) {
        this.stack = new ArrayList(n);
    }

    public void push(T t) {
        this.stack.add(t);
    }

    public T pop() {
        return this.stack.remove(this.stack.size() - 1);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public void clear() {
        this.stack.clear();
    }
}

