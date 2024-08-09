/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.Native;
import io.netty.util.internal.PlatformDependent;

final class KQueueEventArray {
    private static final int KQUEUE_EVENT_SIZE = Native.sizeofKEvent();
    private static final int KQUEUE_IDENT_OFFSET = Native.offsetofKEventIdent();
    private static final int KQUEUE_FILTER_OFFSET = Native.offsetofKEventFilter();
    private static final int KQUEUE_FFLAGS_OFFSET = Native.offsetofKEventFFlags();
    private static final int KQUEUE_FLAGS_OFFSET = Native.offsetofKEventFlags();
    private static final int KQUEUE_DATA_OFFSET = Native.offsetofKeventData();
    private long memoryAddress;
    private int size;
    private int capacity;

    KQueueEventArray(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("capacity must be >= 1 but was " + n);
        }
        this.memoryAddress = PlatformDependent.allocateMemory(n * KQUEUE_EVENT_SIZE);
        this.capacity = n;
    }

    long memoryAddress() {
        return this.memoryAddress;
    }

    int capacity() {
        return this.capacity;
    }

    int size() {
        return this.size;
    }

    void clear() {
        this.size = 0;
    }

    void evSet(AbstractKQueueChannel abstractKQueueChannel, short s, short s2, int n) {
        this.checkSize();
        KQueueEventArray.evSet(this.getKEventOffset(this.size++), abstractKQueueChannel, abstractKQueueChannel.socket.intValue(), s, s2, n);
    }

    private void checkSize() {
        if (this.size == this.capacity) {
            this.realloc(false);
        }
    }

    void realloc(boolean bl) {
        int n = this.capacity <= 65536 ? this.capacity << 1 : this.capacity + this.capacity >> 1;
        long l = PlatformDependent.reallocateMemory(this.memoryAddress, n * KQUEUE_EVENT_SIZE);
        if (l != 0L) {
            this.memoryAddress = l;
            this.capacity = n;
            return;
        }
        if (bl) {
            throw new OutOfMemoryError("unable to allocate " + n + " new bytes! Existing capacity is: " + this.capacity);
        }
    }

    void free() {
        PlatformDependent.freeMemory(this.memoryAddress);
        this.capacity = 0;
        this.size = 0;
        this.memoryAddress = 0;
    }

    long getKEventOffset(int n) {
        return this.memoryAddress + (long)(n * KQUEUE_EVENT_SIZE);
    }

    short flags(int n) {
        return PlatformDependent.getShort(this.getKEventOffset(n) + (long)KQUEUE_FLAGS_OFFSET);
    }

    short filter(int n) {
        return PlatformDependent.getShort(this.getKEventOffset(n) + (long)KQUEUE_FILTER_OFFSET);
    }

    short fflags(int n) {
        return PlatformDependent.getShort(this.getKEventOffset(n) + (long)KQUEUE_FFLAGS_OFFSET);
    }

    int fd(int n) {
        return PlatformDependent.getInt(this.getKEventOffset(n) + (long)KQUEUE_IDENT_OFFSET);
    }

    long data(int n) {
        return PlatformDependent.getLong(this.getKEventOffset(n) + (long)KQUEUE_DATA_OFFSET);
    }

    AbstractKQueueChannel channel(int n) {
        return KQueueEventArray.getChannel(this.getKEventOffset(n));
    }

    private static native void evSet(long var0, AbstractKQueueChannel var2, int var3, short var4, short var5, int var6);

    private static native AbstractKQueueChannel getChannel(long var0);

    static native void deleteGlobalRefs(long var0, long var2);
}

