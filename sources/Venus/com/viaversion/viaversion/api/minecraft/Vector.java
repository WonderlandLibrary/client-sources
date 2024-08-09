/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public class Vector {
    private int blockX;
    private int blockY;
    private int blockZ;

    public Vector(int n, int n2, int n3) {
        this.blockX = n;
        this.blockY = n2;
        this.blockZ = n3;
    }

    public int blockX() {
        return this.blockX;
    }

    public int blockY() {
        return this.blockY;
    }

    public int blockZ() {
        return this.blockZ;
    }

    @Deprecated
    public int getBlockX() {
        return this.blockX;
    }

    @Deprecated
    public int getBlockY() {
        return this.blockY;
    }

    @Deprecated
    public int getBlockZ() {
        return this.blockZ;
    }

    @Deprecated
    public void setBlockX(int n) {
        this.blockX = n;
    }

    @Deprecated
    public void setBlockY(int n) {
        this.blockY = n;
    }

    @Deprecated
    public void setBlockZ(int n) {
        this.blockZ = n;
    }
}

