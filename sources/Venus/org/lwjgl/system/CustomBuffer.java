/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;
import javax.annotation.Nullable;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

public abstract class CustomBuffer<SELF extends CustomBuffer<SELF>>
extends Pointer.Default {
    @Nullable
    protected ByteBuffer container;
    protected int mark;
    protected int position;
    protected int limit;
    protected int capacity;

    protected CustomBuffer(long l, @Nullable ByteBuffer byteBuffer, int n, int n2, int n3, int n4) {
        super(l);
        this.container = byteBuffer;
        this.mark = n;
        this.position = n2;
        this.limit = n3;
        this.capacity = n4;
    }

    public abstract int sizeof();

    public long address0() {
        return this.address;
    }

    @Override
    public long address() {
        return this.address + Integer.toUnsignedLong(this.position) * (long)this.sizeof();
    }

    public long address(int n) {
        return this.address + Integer.toUnsignedLong(n) * (long)this.sizeof();
    }

    public void free() {
        MemoryUtil.nmemFree(this.address);
    }

    public int capacity() {
        return this.capacity;
    }

    public int position() {
        return this.position;
    }

    public SELF position(int n) {
        if (n < 0 || this.limit < n) {
            throw new IllegalArgumentException();
        }
        this.position = n;
        if (n < this.mark) {
            this.mark = -1;
        }
        return this.self();
    }

    public int limit() {
        return this.limit;
    }

    public SELF limit(int n) {
        if (n < 0 || this.capacity < n) {
            throw new IllegalArgumentException();
        }
        this.limit = n;
        if (n < this.position) {
            this.position = n;
        }
        if (n < this.mark) {
            this.mark = -1;
        }
        return this.self();
    }

    public SELF mark() {
        this.mark = this.position;
        return this.self();
    }

    public SELF reset() {
        int n = this.mark;
        if (n < 0) {
            throw new InvalidMarkException();
        }
        this.position = n;
        return this.self();
    }

    public SELF clear() {
        this.position = 0;
        this.limit = this.capacity;
        this.mark = -1;
        return this.self();
    }

    public SELF flip() {
        this.limit = this.position;
        this.position = 0;
        this.mark = -1;
        return this.self();
    }

    public SELF rewind() {
        this.position = 0;
        this.mark = -1;
        return this.self();
    }

    public int remaining() {
        return this.limit - this.position;
    }

    public boolean hasRemaining() {
        return this.position < this.limit;
    }

    public SELF slice() {
        CustomBuffer customBuffer;
        try {
            customBuffer = (CustomBuffer)UNSAFE.allocateInstance(this.getClass());
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)customBuffer, ADDRESS, this.address + Integer.toUnsignedLong(this.position) * (long)this.sizeof());
        UNSAFE.putInt((Object)customBuffer, BUFFER_MARK, -1);
        UNSAFE.putInt((Object)customBuffer, BUFFER_LIMIT, this.remaining());
        UNSAFE.putInt((Object)customBuffer, BUFFER_CAPACITY, this.remaining());
        UNSAFE.putObject((Object)customBuffer, BUFFER_CONTAINER, (Object)this.container);
        return (SELF)customBuffer;
    }

    public SELF slice(int n, int n2) {
        CustomBuffer customBuffer;
        int n3 = this.position + n;
        if (n < 0 || this.limit < n) {
            throw new IllegalArgumentException();
        }
        if (n2 < 0 || this.capacity - n3 < n2) {
            throw new IllegalArgumentException();
        }
        try {
            customBuffer = (CustomBuffer)UNSAFE.allocateInstance(this.getClass());
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)customBuffer, ADDRESS, this.address + Integer.toUnsignedLong(n3) * (long)this.sizeof());
        UNSAFE.putInt((Object)customBuffer, BUFFER_MARK, -1);
        UNSAFE.putInt((Object)customBuffer, BUFFER_LIMIT, n2);
        UNSAFE.putInt((Object)customBuffer, BUFFER_CAPACITY, n2);
        UNSAFE.putObject((Object)customBuffer, BUFFER_CONTAINER, (Object)this.container);
        return (SELF)customBuffer;
    }

    public SELF duplicate() {
        CustomBuffer customBuffer;
        try {
            customBuffer = (CustomBuffer)UNSAFE.allocateInstance(this.getClass());
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)customBuffer, ADDRESS, this.address);
        UNSAFE.putInt((Object)customBuffer, BUFFER_MARK, this.mark);
        UNSAFE.putInt((Object)customBuffer, BUFFER_POSITION, this.position);
        UNSAFE.putInt((Object)customBuffer, BUFFER_LIMIT, this.limit);
        UNSAFE.putInt((Object)customBuffer, BUFFER_CAPACITY, this.capacity);
        UNSAFE.putObject((Object)customBuffer, BUFFER_CONTAINER, (Object)this.container);
        return (SELF)customBuffer;
    }

    public SELF put(SELF SELF) {
        if (SELF == this) {
            throw new IllegalArgumentException();
        }
        int n = ((CustomBuffer)SELF).remaining();
        if (this.remaining() < n) {
            throw new BufferOverflowException();
        }
        MemoryUtil.memCopy(((CustomBuffer)SELF).address(), this.address(), Integer.toUnsignedLong(n) * (long)this.sizeof());
        this.position += n;
        return this.self();
    }

    public SELF compact() {
        MemoryUtil.memCopy(this.address(), this.address, Integer.toUnsignedLong(this.remaining()) * (long)this.sizeof());
        this.position(this.remaining());
        this.limit(this.capacity());
        this.mark = -1;
        return this.self();
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[pos=" + this.position() + " lim=" + this.limit() + " cap=" + this.capacity() + "]";
    }

    protected abstract SELF self();

    protected final int nextGetIndex() {
        if (this.position < this.limit) {
            return this.position++;
        }
        throw new BufferUnderflowException();
    }

    protected final int nextPutIndex() {
        if (this.position < this.limit) {
            return this.position++;
        }
        throw new BufferOverflowException();
    }
}

