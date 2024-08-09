/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.util.AbstractList;
import net.minecraft.nbt.INBT;

public abstract class CollectionNBT<T extends INBT>
extends AbstractList<T>
implements INBT {
    @Override
    public abstract T set(int var1, T var2);

    @Override
    public abstract void add(int var1, T var2);

    @Override
    public abstract T remove(int var1);

    public abstract boolean setNBTByIndex(int var1, INBT var2);

    public abstract boolean addNBTByIndex(int var1, INBT var2);

    public abstract byte getTagType();

    @Override
    public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public void add(int n, Object object) {
        this.add(n, (T)((INBT)object));
    }

    @Override
    public Object set(int n, Object object) {
        return this.set(n, (T)((INBT)object));
    }
}

