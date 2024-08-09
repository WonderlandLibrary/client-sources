/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CheckIntrinsics;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PointerBuffer
extends CustomBuffer<PointerBuffer>
implements Comparable<PointerBuffer> {
    protected PointerBuffer(long l, @Nullable ByteBuffer byteBuffer, int n, int n2, int n3, int n4) {
        super(l, byteBuffer, n, n2, n3, n4);
    }

    public static PointerBuffer allocateDirect(int n) {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(BufferUtils.getAllocationSize(n, POINTER_SHIFT));
        return PointerBuffer.wrap(PointerBuffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static PointerBuffer create(long l, int n) {
        return PointerBuffer.wrap(PointerBuffer.class, l, n);
    }

    public static PointerBuffer create(ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining() >> POINTER_SHIFT;
        return PointerBuffer.wrap(PointerBuffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    @Override
    protected PointerBuffer self() {
        return this;
    }

    @Override
    public int sizeof() {
        return POINTER_SIZE;
    }

    public long get() {
        return MemoryUtil.memGetAddress(this.address + Integer.toUnsignedLong(this.nextGetIndex()) * (long)POINTER_SIZE);
    }

    public static long get(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < POINTER_SIZE) {
            throw new BufferUnderflowException();
        }
        try {
            long l = MemoryUtil.memGetAddress(MemoryUtil.memAddress(byteBuffer));
            return l;
        } finally {
            byteBuffer.position(byteBuffer.position() + POINTER_SIZE);
        }
    }

    @Override
    public PointerBuffer put(long l) {
        MemoryUtil.memPutAddress(this.address + Integer.toUnsignedLong(this.nextPutIndex()) * (long)POINTER_SIZE, l);
        return this;
    }

    public static void put(ByteBuffer byteBuffer, long l) {
        if (byteBuffer.remaining() < POINTER_SIZE) {
            throw new BufferOverflowException();
        }
        try {
            MemoryUtil.memPutAddress(MemoryUtil.memAddress(byteBuffer), l);
        } finally {
            byteBuffer.position(byteBuffer.position() + POINTER_SIZE);
        }
    }

    public long get(int n) {
        return MemoryUtil.memGetAddress(this.address + Checks.check(n, this.limit) * (long)POINTER_SIZE);
    }

    public static long get(ByteBuffer byteBuffer, int n) {
        CheckIntrinsics.checkFromIndexSize(n, POINTER_SIZE, byteBuffer.limit());
        return MemoryUtil.memGetAddress(MemoryUtil.memAddress0(byteBuffer) + (long)n);
    }

    public PointerBuffer put(int n, long l) {
        MemoryUtil.memPutAddress(this.address + Checks.check(n, this.limit) * (long)POINTER_SIZE, l);
        return this;
    }

    public static void put(ByteBuffer byteBuffer, int n, long l) {
        CheckIntrinsics.checkFromIndexSize(n, POINTER_SIZE, byteBuffer.limit());
        MemoryUtil.memPutAddress(MemoryUtil.memAddress0(byteBuffer) + (long)n, l);
    }

    @Override
    public PointerBuffer put(Pointer pointer) {
        this.put(pointer.address());
        return this;
    }

    public PointerBuffer put(int n, Pointer pointer) {
        this.put(n, pointer.address());
        return this;
    }

    @Override
    public PointerBuffer put(ByteBuffer byteBuffer) {
        this.put(MemoryUtil.memAddress(byteBuffer));
        return this;
    }

    @Override
    public PointerBuffer put(ShortBuffer shortBuffer) {
        this.put(MemoryUtil.memAddress(shortBuffer));
        return this;
    }

    @Override
    public PointerBuffer put(IntBuffer intBuffer) {
        this.put(MemoryUtil.memAddress(intBuffer));
        return this;
    }

    @Override
    public PointerBuffer put(LongBuffer longBuffer) {
        this.put(MemoryUtil.memAddress(longBuffer));
        return this;
    }

    @Override
    public PointerBuffer put(FloatBuffer floatBuffer) {
        this.put(MemoryUtil.memAddress(floatBuffer));
        return this;
    }

    @Override
    public PointerBuffer put(DoubleBuffer doubleBuffer) {
        this.put(MemoryUtil.memAddress(doubleBuffer));
        return this;
    }

    public PointerBuffer putAddressOf(CustomBuffer<?> customBuffer) {
        this.put(MemoryUtil.memAddress(customBuffer));
        return this;
    }

    public PointerBuffer put(int n, ByteBuffer byteBuffer) {
        this.put(n, MemoryUtil.memAddress(byteBuffer));
        return this;
    }

    public PointerBuffer put(int n, ShortBuffer shortBuffer) {
        this.put(n, MemoryUtil.memAddress(shortBuffer));
        return this;
    }

    public PointerBuffer put(int n, IntBuffer intBuffer) {
        this.put(n, MemoryUtil.memAddress(intBuffer));
        return this;
    }

    public PointerBuffer put(int n, LongBuffer longBuffer) {
        this.put(n, MemoryUtil.memAddress(longBuffer));
        return this;
    }

    public PointerBuffer put(int n, FloatBuffer floatBuffer) {
        this.put(n, MemoryUtil.memAddress(floatBuffer));
        return this;
    }

    public PointerBuffer put(int n, DoubleBuffer doubleBuffer) {
        this.put(n, MemoryUtil.memAddress(doubleBuffer));
        return this;
    }

    public PointerBuffer putAddressOf(int n, CustomBuffer<?> customBuffer) {
        this.put(n, MemoryUtil.memAddress(customBuffer));
        return this;
    }

    public ByteBuffer getByteBuffer(int n) {
        return MemoryUtil.memByteBuffer(this.get(), n);
    }

    public ShortBuffer getShortBuffer(int n) {
        return MemoryUtil.memShortBuffer(this.get(), n);
    }

    public IntBuffer getIntBuffer(int n) {
        return MemoryUtil.memIntBuffer(this.get(), n);
    }

    public LongBuffer getLongBuffer(int n) {
        return MemoryUtil.memLongBuffer(this.get(), n);
    }

    public FloatBuffer getFloatBuffer(int n) {
        return MemoryUtil.memFloatBuffer(this.get(), n);
    }

    public DoubleBuffer getDoubleBuffer(int n) {
        return MemoryUtil.memDoubleBuffer(this.get(), n);
    }

    public PointerBuffer getPointerBuffer(int n) {
        return MemoryUtil.memPointerBuffer(this.get(), n);
    }

    public String getStringASCII() {
        return MemoryUtil.memASCII(this.get());
    }

    public String getStringUTF8() {
        return MemoryUtil.memUTF8(this.get());
    }

    public String getStringUTF16() {
        return MemoryUtil.memUTF16(this.get());
    }

    public ByteBuffer getByteBuffer(int n, int n2) {
        return MemoryUtil.memByteBuffer(this.get(n), n2);
    }

    public ShortBuffer getShortBuffer(int n, int n2) {
        return MemoryUtil.memShortBuffer(this.get(n), n2);
    }

    public IntBuffer getIntBuffer(int n, int n2) {
        return MemoryUtil.memIntBuffer(this.get(n), n2);
    }

    public LongBuffer getLongBuffer(int n, int n2) {
        return MemoryUtil.memLongBuffer(this.get(n), n2);
    }

    public FloatBuffer getFloatBuffer(int n, int n2) {
        return MemoryUtil.memFloatBuffer(this.get(n), n2);
    }

    public DoubleBuffer getDoubleBuffer(int n, int n2) {
        return MemoryUtil.memDoubleBuffer(this.get(n), n2);
    }

    public PointerBuffer getPointerBuffer(int n, int n2) {
        return MemoryUtil.memPointerBuffer(this.get(n), n2);
    }

    public String getStringASCII(int n) {
        return MemoryUtil.memASCII(this.get(n));
    }

    public String getStringUTF8(int n) {
        return MemoryUtil.memUTF8(this.get(n));
    }

    public String getStringUTF16(int n) {
        return MemoryUtil.memUTF16(this.get(n));
    }

    public PointerBuffer get(long[] lArray) {
        return this.get(lArray, 0, lArray.length);
    }

    public PointerBuffer get(long[] lArray, int n, int n2) {
        if (BITS64) {
            MemoryUtil.memLongBuffer(this.address(), this.remaining()).get(lArray, n, n2);
            this.position(this.position() + n2);
        } else {
            this.get32(lArray, n, n2);
        }
        return this;
    }

    private void get32(long[] lArray, int n, int n2) {
        CheckIntrinsics.checkFromIndexSize(n, n2, lArray.length);
        if (this.remaining() < n2) {
            throw new BufferUnderflowException();
        }
        int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            lArray[i] = this.get();
        }
    }

    @Override
    public PointerBuffer put(long[] lArray) {
        return this.put(lArray, 0, lArray.length);
    }

    public PointerBuffer put(long[] lArray, int n, int n2) {
        if (BITS64) {
            MemoryUtil.memLongBuffer(this.address(), this.remaining()).put(lArray, n, n2);
            this.position(this.position() + n2);
        } else {
            this.put32(lArray, n, n2);
        }
        return this;
    }

    private void put32(long[] lArray, int n, int n2) {
        CheckIntrinsics.checkFromIndexSize(n, n2, lArray.length);
        if (this.remaining() < n2) {
            throw new BufferOverflowException();
        }
        int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            this.put(lArray[i]);
        }
    }

    @Override
    public int hashCode() {
        int n = 1;
        int n2 = this.position();
        for (int i = this.limit() - 1; i >= n2; --i) {
            n = 31 * n + (int)this.get(i);
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PointerBuffer)) {
            return true;
        }
        PointerBuffer pointerBuffer = (PointerBuffer)object;
        if (this.remaining() != pointerBuffer.remaining()) {
            return true;
        }
        int n = this.position();
        int n2 = this.limit() - 1;
        int n3 = pointerBuffer.limit() - 1;
        while (n2 >= n) {
            long l;
            long l2 = this.get(n2);
            if (l2 != (l = pointerBuffer.get(n3))) {
                return true;
            }
            --n2;
            --n3;
        }
        return false;
    }

    @Override
    public int compareTo(PointerBuffer pointerBuffer) {
        int n = this.position() + Math.min(this.remaining(), pointerBuffer.remaining());
        int n2 = this.position();
        int n3 = pointerBuffer.position();
        while (n2 < n) {
            long l;
            long l2 = this.get(n2);
            if (l2 != (l = pointerBuffer.get(n3))) {
                if (l2 < l) {
                    return 1;
                }
                return 0;
            }
            ++n2;
            ++n3;
        }
        return this.remaining() - pointerBuffer.remaining();
    }

    @Override
    protected CustomBuffer self() {
        return this.self();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((PointerBuffer)object);
    }
}

