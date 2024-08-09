/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.epoll.Native;
import io.netty.util.internal.PlatformDependent;

final class EpollEventArray {
    private static final int EPOLL_EVENT_SIZE = Native.sizeofEpollEvent();
    private static final int EPOLL_DATA_OFFSET = Native.offsetofEpollData();
    private long memoryAddress;
    private int length;

    EpollEventArray(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("length must be >= 1 but was " + n);
        }
        this.length = n;
        this.memoryAddress = EpollEventArray.allocate(n);
    }

    private static long allocate(int n) {
        return PlatformDependent.allocateMemory(n * EPOLL_EVENT_SIZE);
    }

    long memoryAddress() {
        return this.memoryAddress;
    }

    int length() {
        return this.length;
    }

    void increase() {
        this.length <<= 1;
        this.free();
        this.memoryAddress = EpollEventArray.allocate(this.length);
    }

    void free() {
        PlatformDependent.freeMemory(this.memoryAddress);
    }

    int events(int n) {
        return PlatformDependent.getInt(this.memoryAddress + (long)(n * EPOLL_EVENT_SIZE));
    }

    int fd(int n) {
        return PlatformDependent.getInt(this.memoryAddress + (long)(n * EPOLL_EVENT_SIZE) + (long)EPOLL_DATA_OFFSET);
    }
}

