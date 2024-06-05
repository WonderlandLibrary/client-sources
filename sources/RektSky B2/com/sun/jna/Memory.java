package com.sun.jna;

import java.lang.ref.*;
import java.nio.*;
import java.util.*;

public class Memory extends Pointer
{
    private static final Map<Memory, Reference<Memory>> allocatedMemory;
    private static final WeakMemoryHolder buffers;
    protected long size;
    
    public static void purge() {
        Memory.buffers.clean();
    }
    
    public static void disposeAll() {
        final Collection<Memory> refs = new LinkedList<Memory>(Memory.allocatedMemory.keySet());
        for (final Memory r : refs) {
            r.dispose();
        }
    }
    
    public Memory(final long size) {
        this.size = size;
        if (size <= 0L) {
            throw new IllegalArgumentException("Allocation size must be greater than zero");
        }
        this.peer = malloc(size);
        if (this.peer == 0L) {
            throw new OutOfMemoryError("Cannot allocate " + size + " bytes");
        }
        Memory.allocatedMemory.put(this, new WeakReference<Memory>(this));
    }
    
    protected Memory() {
    }
    
    @Override
    public Pointer share(final long offset) {
        return this.share(offset, this.size() - offset);
    }
    
    @Override
    public Pointer share(final long offset, final long sz) {
        this.boundsCheck(offset, sz);
        return new SharedMemory(offset, sz);
    }
    
    public Memory align(final int byteBoundary) {
        if (byteBoundary <= 0) {
            throw new IllegalArgumentException("Byte boundary must be positive: " + byteBoundary);
        }
        int i = 0;
        while (i < 32) {
            if (byteBoundary == 1 << i) {
                final long mask = ~(byteBoundary - 1L);
                if ((this.peer & mask) == this.peer) {
                    return this;
                }
                final long newPeer = this.peer + byteBoundary - 1L & mask;
                final long newSize = this.peer + this.size - newPeer;
                if (newSize <= 0L) {
                    throw new IllegalArgumentException("Insufficient memory to align to the requested boundary");
                }
                return (Memory)this.share(newPeer - this.peer, newSize);
            }
            else {
                ++i;
            }
        }
        throw new IllegalArgumentException("Byte boundary must be a power of two");
    }
    
    @Override
    protected void finalize() {
        this.dispose();
    }
    
    protected synchronized void dispose() {
        try {
            free(this.peer);
        }
        finally {
            Memory.allocatedMemory.remove(this);
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
    
    protected void boundsCheck(final long off, final long sz) {
        if (off < 0L) {
            throw new IndexOutOfBoundsException("Invalid offset: " + off);
        }
        if (off + sz > this.size) {
            final String msg = "Bounds exceeds available space : size=" + this.size + ", offset=" + (off + sz);
            throw new IndexOutOfBoundsException(msg);
        }
    }
    
    @Override
    public void read(final long bOff, final byte[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 1L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final short[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 2L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final char[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 2L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final int[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 4L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final long[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 8L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final float[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 4L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void read(final long bOff, final double[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 8L);
        super.read(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final byte[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 1L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final short[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 2L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final char[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 2L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final int[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 4L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final long[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 8L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final float[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 4L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public void write(final long bOff, final double[] buf, final int index, final int length) {
        this.boundsCheck(bOff, length * 8L);
        super.write(bOff, buf, index, length);
    }
    
    @Override
    public byte getByte(final long offset) {
        this.boundsCheck(offset, 1L);
        return super.getByte(offset);
    }
    
    @Override
    public char getChar(final long offset) {
        this.boundsCheck(offset, 1L);
        return super.getChar(offset);
    }
    
    @Override
    public short getShort(final long offset) {
        this.boundsCheck(offset, 2L);
        return super.getShort(offset);
    }
    
    @Override
    public int getInt(final long offset) {
        this.boundsCheck(offset, 4L);
        return super.getInt(offset);
    }
    
    @Override
    public long getLong(final long offset) {
        this.boundsCheck(offset, 8L);
        return super.getLong(offset);
    }
    
    @Override
    public float getFloat(final long offset) {
        this.boundsCheck(offset, 4L);
        return super.getFloat(offset);
    }
    
    @Override
    public double getDouble(final long offset) {
        this.boundsCheck(offset, 8L);
        return super.getDouble(offset);
    }
    
    @Override
    public Pointer getPointer(final long offset) {
        this.boundsCheck(offset, Pointer.SIZE);
        return super.getPointer(offset);
    }
    
    @Override
    public ByteBuffer getByteBuffer(final long offset, final long length) {
        this.boundsCheck(offset, length);
        final ByteBuffer b = super.getByteBuffer(offset, length);
        Memory.buffers.put(b, this);
        return b;
    }
    
    @Override
    public String getString(final long offset, final String encoding) {
        this.boundsCheck(offset, 0L);
        return super.getString(offset, encoding);
    }
    
    @Override
    public String getWideString(final long offset) {
        this.boundsCheck(offset, 0L);
        return super.getWideString(offset);
    }
    
    @Override
    public void setByte(final long offset, final byte value) {
        this.boundsCheck(offset, 1L);
        super.setByte(offset, value);
    }
    
    @Override
    public void setChar(final long offset, final char value) {
        this.boundsCheck(offset, Native.WCHAR_SIZE);
        super.setChar(offset, value);
    }
    
    @Override
    public void setShort(final long offset, final short value) {
        this.boundsCheck(offset, 2L);
        super.setShort(offset, value);
    }
    
    @Override
    public void setInt(final long offset, final int value) {
        this.boundsCheck(offset, 4L);
        super.setInt(offset, value);
    }
    
    @Override
    public void setLong(final long offset, final long value) {
        this.boundsCheck(offset, 8L);
        super.setLong(offset, value);
    }
    
    @Override
    public void setFloat(final long offset, final float value) {
        this.boundsCheck(offset, 4L);
        super.setFloat(offset, value);
    }
    
    @Override
    public void setDouble(final long offset, final double value) {
        this.boundsCheck(offset, 8L);
        super.setDouble(offset, value);
    }
    
    @Override
    public void setPointer(final long offset, final Pointer value) {
        this.boundsCheck(offset, Pointer.SIZE);
        super.setPointer(offset, value);
    }
    
    @Override
    public void setString(final long offset, final String value, final String encoding) {
        this.boundsCheck(offset, Native.getBytes(value, encoding).length + 1L);
        super.setString(offset, value, encoding);
    }
    
    @Override
    public void setWideString(final long offset, final String value) {
        this.boundsCheck(offset, (value.length() + 1L) * Native.WCHAR_SIZE);
        super.setWideString(offset, value);
    }
    
    @Override
    public String toString() {
        return "allocated@0x" + Long.toHexString(this.peer) + " (" + this.size + " bytes)";
    }
    
    protected static void free(final long p) {
        if (p != 0L) {
            Native.free(p);
        }
    }
    
    protected static long malloc(final long size) {
        return Native.malloc(size);
    }
    
    public String dump() {
        return this.dump(0L, (int)this.size());
    }
    
    static {
        allocatedMemory = Collections.synchronizedMap(new WeakHashMap<Memory, Reference<Memory>>());
        buffers = new WeakMemoryHolder();
    }
    
    private class SharedMemory extends Memory
    {
        public SharedMemory(final long offset, final long size) {
            this.size = size;
            this.peer = Memory.this.peer + offset;
        }
        
        @Override
        protected void dispose() {
            this.peer = 0L;
        }
        
        @Override
        protected void boundsCheck(final long off, final long sz) {
            Memory.this.boundsCheck(this.peer - Memory.this.peer + off, sz);
        }
        
        @Override
        public String toString() {
            return super.toString() + " (shared from " + Memory.this.toString() + ")";
        }
    }
}
