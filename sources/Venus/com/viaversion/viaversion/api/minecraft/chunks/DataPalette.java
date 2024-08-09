/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

public interface DataPalette {
    public int index(int var1, int var2, int var3);

    public int idAt(int var1);

    default public int idAt(int n, int n2, int n3) {
        return this.idAt(this.index(n, n2, n3));
    }

    public void setIdAt(int var1, int var2);

    default public void setIdAt(int n, int n2, int n3, int n4) {
        this.setIdAt(this.index(n, n2, n3), n4);
    }

    public int idByIndex(int var1);

    public void setIdByIndex(int var1, int var2);

    public int paletteIndexAt(int var1);

    public void setPaletteIndexAt(int var1, int var2);

    public void addId(int var1);

    public void replaceId(int var1, int var2);

    public int size();

    public void clear();
}

