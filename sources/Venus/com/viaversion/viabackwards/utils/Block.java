/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.utils;

public class Block {
    private final int id;
    private final short data;

    public Block(int n, int n2) {
        this.id = n;
        this.data = (short)n2;
    }

    public Block(int n) {
        this.id = n;
        this.data = 0;
    }

    public int getId() {
        return this.id;
    }

    public int getData() {
        return this.data;
    }

    public Block withData(int n) {
        return new Block(this.id, n);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Block block = (Block)object;
        if (this.id != block.id) {
            return true;
        }
        return this.data == block.data;
    }

    public int hashCode() {
        int n = this.id;
        n = 31 * n + this.data;
        return n;
    }
}

