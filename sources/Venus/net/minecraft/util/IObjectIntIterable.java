/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;

public interface IObjectIntIterable<T>
extends Iterable<T> {
    public int getId(T var1);

    @Nullable
    public T getByValue(int var1);
}

