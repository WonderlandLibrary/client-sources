/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import net.minecraft.client.util.ISearchTree;

public interface IMutableSearchTree<T>
extends ISearchTree<T> {
    public void func_217872_a(T var1);

    public void clear();

    public void recalculate();
}

