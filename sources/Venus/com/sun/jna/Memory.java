/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WeakMemoryHolder;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

public class Memory
extends Pointer {
    private static final Map<Memory, Reference<Memory>> allocatedMemory = Collections.synchronizedMap(new WeakHashMap());
    private static final WeakMemoryHolder buffers = new WeakMemoryHolder();
    protected long size;

    public static void purge() {
        buffers.clean();
    }

    public static void disposeAll() {
        LinkedList<Memory> linkedList = new LinkedList<Memory>(allocatedMemory.keySet());
        for (Memory memory : linkedList) {
            memory.dispose();
        }
    }

    public Memory(long l) {
        this.size = l;
        if (l <= 0L) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        this.peer = Memory.malloc(l);
        if (this.peer == 0L) {
            throw new OutOfMemoryError("Cannot allocate " + l + " bytes");
        }
        allocatedMemory.put(this, new WeakReference<Memory>(this));
    }

    protected Memory() {
    }

    @Override
    public Pointer share(long l) {
        return this.share(l, this.size() - l);
    }

    @Override
    public Pointer share(long l, long l2) {
        this.boundsCheck(l, l2);
        return new SharedMemory(this, l, l2);
    }

    public Memory align(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Byte boundary must be positive: " + n);
        }
        for (int i = 0; i < 32; ++i) {
            if (n != 1 << i) continue;
            long l = (long)n - 1L ^ 0xFFFFFFFFFFFFFFFFL;
            if ((this.peer & l) != this.peer) {
                long l2 = this.peer + (long)n - 1L & l;
                long l3 = this.peer + this.size - l2;
                if (l3 <= 0L) {
                    throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                }
                return (Memory)this.share(l2 - this.peer, l3);
            }
            return this;
        }
        throw new IllegalArgumentException("Byte boundary must be a power of two");
    }

    protected void finalize() {
        this.dispose();
    }

    protected synchronized void dispose() {
        try {
            Memory.free(this.peer);
        } finally {
            allocatedMemory.remove(this);
            this.peer = 0L;
        }
    }

    public void clear() {
        this.clear(this.size);
    }

    public boolean valid() {
        return this.peer != 0L;
    }

    public long size() {
        return this.size;
    }

    protected void boundsCheck(long l, long l2) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Invalid offset: " + l);
        }
        if (l + l2 > this.size) {
            String string = "Bounds exceeds available space : size=" + this.size + ", offset=" + (l + l2);
            throw new IndexOutOfBoundsException(string);
        }
    }

    @Override
    public void read(long l, byte[] byArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 1L);
        super.read(l, byArray, n, n2);
    }

    @Override
    public void read(long l, short[] sArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 2L);
        super.read(l, sArray, n, n2);
    }

    @Override
    public void read(long l, char[] cArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 2L);
        super.read(l, cArray, n, n2);
    }

    @Override
    public void read(long l, int[] nArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 4L);
        super.read(l, nArray, n, n2);
    }

    @Override
    public void read(long l, long[] lArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 8L);
        super.read(l, lArray, n, n2);
    }

    @Override
    public void read(long l, float[] fArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 4L);
        super.read(l, fArray, n, n2);
    }

    @Override
    public void read(long l, double[] dArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 8L);
        super.read(l, dArray, n, n2);
    }

    @Override
    public void write(long l, byte[] byArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 1L);
        super.write(l, byArray, n, n2);
    }

    @Override
    public void write(long l, short[] sArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 2L);
        super.write(l, sArray, n, n2);
    }

    @Override
    public void write(long l, char[] cArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 2L);
        super.write(l, cArray, n, n2);
    }

    @Override
    public void write(long l, int[] nArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 4L);
        super.write(l, nArray, n, n2);
    }

    @Override
    public void write(long l, long[] lArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 8L);
        super.write(l, lArray, n, n2);
    }

    @Override
    public void write(long l, float[] fArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 4L);
        super.write(l, fArray, n, n2);
    }

    @Override
    public void write(long l, double[] dArray, int n, int n2) {
        this.boundsCheck(l, (long)n2 * 8L);
        super.write(l, dArray, n, n2);
    }

    @Override
    public byte getByte(long l) {
        this.boundsCheck(l, 1L);
        return super.getByte(l);
    }

    @Override
    public char getChar(long l) {
        this.boundsCheck(l, 1L);
        return super.getChar(l);
    }

    @Override
    public short getShort(long l) {
        this.boundsCheck(l, 2L);
        return super.getShort(l);
    }

    @Override
    public int getInt(long l) {
        this.boundsCheck(l, 4L);
        return super.getInt(l);
    }

    @Override
    public long getLong(long l) {
        this.boundsCheck(l, 8L);
        return super.getLong(l);
    }

    @Override
    public float getFloat(long l) {
        this.boundsCheck(l, 4L);
        return super.getFloat(l);
    }

    @Override
    public double getDouble(long l) {
        this.boundsCheck(l, 8L);
        return super.getDouble(l);
    }

    @Override
    public Pointer getPointer(long l) {
        this.boundsCheck(l, Pointer.SIZE);
        return super.getPointer(l);
    }

    @Override
    public ByteBuffer getByteBuffer(long l, long l2) {
        this.boundsCheck(l, l2);
        ByteBuffer byteBuffer = super.getByteBuffer(l, l2);
        buffers.put(byteBuffer, this);
        return byteBuffer;
    }

    @Override
    public String getString(long l, String string) {
        this.boundsCheck(l, 0L);
        return super.getString(l, string);
    }

    @Override
    public String getWideString(long l) {
        this.boundsCheck(l, 0L);
        return super.getWideString(l);
    }

    @Override
    public void setByte(long l, byte by) {
        this.boundsCheck(l, 1L);
        super.setByte(l, by);
    }

    @Override
    public void setChar(long l, char c) {
        this.boundsCheck(l, Native.WCHAR_SIZE);
        super.setChar(l, c);
    }

    @Override
    public void setShort(long l, short s) {
        this.boundsCheck(l, 2L);
        super.setShort(l, s);
    }

    @Override
    public void setInt(long l, int n) {
        this.boundsCheck(l, 4L);
        super.setInt(l, n);
    }

    @Override
    public void setLong(long l, long l2) {
        this.boundsCheck(l, 8L);
        super.setLong(l, l2);
    }

    @Override
    public void setFloat(long l, float f) {
        this.boundsCheck(l, 4L);
        super.setFloat(l, f);
    }

    @Override
    public void setDouble(long l, double d) {
        this.boundsCheck(l, 8L);
        super.setDouble(l, d);
    }

    @Override
    public void setPointer(long l, Pointer pointer) {
        this.boundsCheck(l, Pointer.SIZE);
        super.setPointer(l, pointer);
    }

    @Override
    public void setString(long l, String string, String string2) {
        this.boundsCheck(l, (long)Native.getBytes(string, string2).length + 1L);
        super.setString(l, string, string2);
    }

    @Override
    public void setWideString(long l, String string) {
        this.boundsCheck(l, ((long)string.length() + 1L) * (long)Native.WCHAR_SIZE);
        super.setWideString(l, string);
    }

    @Override
    public String toString() {
        return "allocated@0x" + Long.toHexString(this.peer) + " (" + this.size + " bytes)";
    }

    protected static void free(long l) {
        if (l != 0L) {
            Native.free(l);
        }
    }

    protected static long malloc(long l) {
        return Native.malloc(l);
    }

    public String dump() {
        return this.dump(0L, (int)this.size());
    }

    private class SharedMemory
    extends Memory {
        final Memory this$0;

        public SharedMemory(Memory memory, long l, long l2) {
            this.this$0 = memory;
            this.size = l2;
            this.peer = memory.peer + l;
        }

        @Override
        protected void dispose() {
            this.peer = 0L;
        }

        @Override
        protected void boundsCheck(long l, long l2) {
            this.this$0.boundsCheck(this.peer - this.this$0.peer + l, l2);
        }

        @Override
        public String toString() {
            return super.toString() + " (shared from " + this.this$0.toString() + ")";
        }
    }
}

