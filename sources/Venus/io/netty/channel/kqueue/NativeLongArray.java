/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.unix.Limits;
import io.netty.util.internal.PlatformDependent;

final class NativeLongArray {
    private long memoryAddress;
    private int capacity;
    private int size;

    NativeLongArray(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("capacity must be >= 1 but was " + n);
        }
        this.memoryAddress = PlatformDependent.allocateMemory(n * Limits.SIZEOF_JLONG);
        this.capacity = n;
    }

    void add(long l) {
        this.checkSize();
        PlatformDependent.putLong(this.memoryOffset(this.size++), l);
    }

    void clear() {
        this.size = 0;
    }

    boolean isEmpty() {
        return this.size == 0;
    }

    void free() {
        PlatformDependent.freeMemory(this.memoryAddress);
        this.memoryAddress = 0L;
    }

    long memoryAddress() {
        return this.memoryAddress;
    }

    long memoryAddressEnd() {
        return this.memoryOffset(this.size);
    }

    private long memoryOffset(int n) {
        return this.memoryAddress + (long)(n * Limits.SIZEOF_JLONG);
    }

    private void checkSize() {
        if (this.size == this.capacity) {
            this.realloc();
        }
    }

    private void realloc() {
        int n = this.capacity <= 65536 ? this.capacity << 1 : this.capacity + this.capacity >> 1;
        long l = PlatformDependent.reallocateMemory(this.memoryAddress, n * Limits.SIZEOF_JLONG);
        if (l == 0L) {
            throw new OutOfMemoryError("unable to allocate " + n + " new bytes! Existing capacity is: " + this.capacity);
        }
        this.memoryAddress = l;
        this.capacity = n;
    }

    public String toString() {
        return "memoryAddress: " + this.memoryAddress + " capacity: " + this.capacity + " size: " + this.size;
    }
}

