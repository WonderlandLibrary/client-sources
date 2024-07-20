/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

public interface Stack<K> {
    public void push(K var1);

    public K pop();

    public boolean isEmpty();

    public K top();

    public K peek(int var1);
}

