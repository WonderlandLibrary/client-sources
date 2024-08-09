/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import java.util.Arrays;

public class NibbleArray {
    private final byte[] handle;

    public NibbleArray(int n) {
        if (n == 0 || n % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = new byte[n / 2];
    }

    public NibbleArray(byte[] byArray) {
        if (byArray.length == 0 || byArray.length % 2 != 0) {
            throw new IllegalArgumentException("Length of nibble array must be a positive number dividable by 2!");
        }
        this.handle = byArray;
    }

    public byte get(int n, int n2, int n3) {
        return this.get(ChunkSection.index(n, n2, n3));
    }

    public byte get(int n) {
        byte by = this.handle[n / 2];
        if (n % 2 == 0) {
            return (byte)(by & 0xF);
        }
        return (byte)(by >> 4 & 0xF);
    }

    public void set(int n, int n2, int n3, int n4) {
        this.set(ChunkSection.index(n, n2, n3), n4);
    }

    public void set(int n, int n2) {
        this.handle[n /= 2] = n % 2 == 0 ? (byte)(this.handle[n] & 0xF0 | n2 & 0xF) : (byte)(this.handle[n] & 0xF | (n2 & 0xF) << 4);
    }

    public int size() {
        return this.handle.length * 2;
    }

    public int actualSize() {
        return this.handle.length;
    }

    public void fill(byte by) {
        by = (byte)(by & 0xF);
        Arrays.fill(this.handle, (byte)(by << 4 | by));
    }

    public byte[] getHandle() {
        return this.handle;
    }

    public void setHandle(byte[] byArray) {
        if (byArray.length != this.handle.length) {
            throw new IllegalArgumentException("Length of handle must equal to size of nibble array!");
        }
        System.arraycopy(byArray, 0, this.handle, 0, byArray.length);
    }
}

