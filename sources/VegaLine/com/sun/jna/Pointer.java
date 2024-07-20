/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Callback;
import com.sun.jna.CallbackReference;
import com.sun.jna.FromNativeContext;
import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.NativeMapped;
import com.sun.jna.NativeMappedConverter;
import com.sun.jna.Platform;
import com.sun.jna.Structure;
import com.sun.jna.ToNativeContext;
import com.sun.jna.WString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class Pointer {
    public static final int SIZE = Native.POINTER_SIZE;
    public static final Pointer NULL;
    protected long peer;

    public static final Pointer createConstant(long peer) {
        return new Opaque(peer);
    }

    public static final Pointer createConstant(int peer) {
        return new Opaque((long)peer & 0xFFFFFFFFFFFFFFFFL);
    }

    Pointer() {
    }

    public Pointer(long peer) {
        this.peer = peer;
    }

    public Pointer share(long offset) {
        return this.share(offset, 0L);
    }

    public Pointer share(long offset, long sz) {
        if (offset == 0L) {
            return this;
        }
        return new Pointer(this.peer + offset);
    }

    public void clear(long size) {
        this.setMemory(0L, size, (byte)0);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        return o instanceof Pointer && ((Pointer)o).peer == this.peer;
    }

    public int hashCode() {
        return (int)((this.peer >>> 32) + (this.peer & 0xFFFFFFFFFFFFFFFFL));
    }

    public long indexOf(long offset, byte value) {
        return Native.indexOf(this, this.peer, offset, value);
    }

    public void read(long offset, byte[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, short[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, char[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, int[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, long[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, float[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, double[] buf, int index, int length) {
        Native.read(this, this.peer, offset, buf, index, length);
    }

    public void read(long offset, Pointer[] buf, int index, int length) {
        for (int i = 0; i < length; ++i) {
            Pointer p = this.getPointer(offset + (long)(i * SIZE));
            Pointer oldp = buf[i + index];
            if (oldp != null && p != null && p.peer == oldp.peer) continue;
            buf[i + index] = p;
        }
    }

    public void write(long offset, byte[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, short[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, char[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, int[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, long[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, float[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long offset, double[] buf, int index, int length) {
        Native.write(this, this.peer, offset, buf, index, length);
    }

    public void write(long bOff, Pointer[] buf, int index, int length) {
        for (int i = 0; i < length; ++i) {
            this.setPointer(bOff + (long)(i * SIZE), buf[index + i]);
        }
    }

    Object getValue(long offset, Class<?> type2, Object currentValue) {
        Object result = null;
        if (Structure.class.isAssignableFrom(type2)) {
            Structure s = (Structure)currentValue;
            if (Structure.ByReference.class.isAssignableFrom(type2)) {
                s = Structure.updateStructureByReference(type2, s, this.getPointer(offset));
            } else {
                s.useMemory(this, (int)offset, true);
                s.read();
            }
            result = s;
        } else if (type2 == Boolean.TYPE || type2 == Boolean.class) {
            result = Function.valueOf(this.getInt(offset) != 0);
        } else if (type2 == Byte.TYPE || type2 == Byte.class) {
            result = this.getByte(offset);
        } else if (type2 == Short.TYPE || type2 == Short.class) {
            result = this.getShort(offset);
        } else if (type2 == Character.TYPE || type2 == Character.class) {
            result = Character.valueOf(this.getChar(offset));
        } else if (type2 == Integer.TYPE || type2 == Integer.class) {
            result = this.getInt(offset);
        } else if (type2 == Long.TYPE || type2 == Long.class) {
            result = this.getLong(offset);
        } else if (type2 == Float.TYPE || type2 == Float.class) {
            result = Float.valueOf(this.getFloat(offset));
        } else if (type2 == Double.TYPE || type2 == Double.class) {
            result = this.getDouble(offset);
        } else if (Pointer.class.isAssignableFrom(type2)) {
            Pointer p = this.getPointer(offset);
            if (p != null) {
                Pointer oldp;
                Pointer pointer = oldp = currentValue instanceof Pointer ? (Pointer)currentValue : null;
                result = oldp == null || p.peer != oldp.peer ? p : oldp;
            }
        } else if (type2 == String.class) {
            Pointer p = this.getPointer(offset);
            result = p != null ? p.getString(0L) : null;
        } else if (type2 == WString.class) {
            Pointer p = this.getPointer(offset);
            result = p != null ? new WString(p.getWideString(0L)) : null;
        } else if (Callback.class.isAssignableFrom(type2)) {
            Pointer fp = this.getPointer(offset);
            if (fp == null) {
                result = null;
            } else {
                Callback cb = (Callback)currentValue;
                Pointer oldfp = CallbackReference.getFunctionPointer(cb);
                if (!fp.equals(oldfp)) {
                    cb = CallbackReference.getCallback(type2, fp);
                }
                result = cb;
            }
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(type2)) {
            Pointer bp = this.getPointer(offset);
            if (bp == null) {
                result = null;
            } else {
                Pointer oldbp;
                Pointer pointer = oldbp = currentValue == null ? null : Native.getDirectBufferPointer((Buffer)currentValue);
                if (oldbp == null || !oldbp.equals(bp)) {
                    throw new IllegalStateException("Can't autogenerate a direct buffer on memory read");
                }
                result = currentValue;
            }
        } else if (NativeMapped.class.isAssignableFrom(type2)) {
            NativeMapped nm = (NativeMapped)currentValue;
            if (nm != null) {
                Object value = this.getValue(offset, nm.nativeType(), null);
                result = nm.fromNative(value, new FromNativeContext(type2));
                if (nm.equals(result)) {
                    result = nm;
                }
            } else {
                NativeMappedConverter tc = NativeMappedConverter.getInstance(type2);
                Object value = this.getValue(offset, tc.nativeType(), null);
                result = tc.fromNative(value, new FromNativeContext(type2));
            }
        } else if (type2.isArray()) {
            result = currentValue;
            if (result == null) {
                throw new IllegalStateException("Need an initialized array");
            }
            this.readArray(offset, result, type2.getComponentType());
        } else {
            throw new IllegalArgumentException("Reading \"" + type2 + "\" from memory is not supported");
        }
        return result;
    }

    private void readArray(long offset, Object o, Class<?> cls) {
        int length = 0;
        length = Array.getLength(o);
        Object result = o;
        if (cls == Byte.TYPE) {
            this.read(offset, (byte[])result, 0, length);
        } else if (cls == Short.TYPE) {
            this.read(offset, (short[])result, 0, length);
        } else if (cls == Character.TYPE) {
            this.read(offset, (char[])result, 0, length);
        } else if (cls == Integer.TYPE) {
            this.read(offset, (int[])result, 0, length);
        } else if (cls == Long.TYPE) {
            this.read(offset, (long[])result, 0, length);
        } else if (cls == Float.TYPE) {
            this.read(offset, (float[])result, 0, length);
        } else if (cls == Double.TYPE) {
            this.read(offset, (double[])result, 0, length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            this.read(offset, (Pointer[])result, 0, length);
        } else if (Structure.class.isAssignableFrom(cls)) {
            Structure[] sarray = (Structure[])result;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                Pointer[] parray = this.getPointerArray(offset, sarray.length);
                for (int i = 0; i < sarray.length; ++i) {
                    sarray[i] = Structure.updateStructureByReference(cls, sarray[i], parray[i]);
                }
            } else {
                Structure first = sarray[0];
                if (first == null) {
                    first = Structure.newInstance(cls, this.share(offset));
                    first.conditionalAutoRead();
                    sarray[0] = first;
                } else {
                    first.useMemory(this, (int)offset, true);
                    first.read();
                }
                Structure[] tmp = first.toArray(sarray.length);
                for (int i = 1; i < sarray.length; ++i) {
                    if (sarray[i] == null) {
                        sarray[i] = tmp[i];
                        continue;
                    }
                    sarray[i].useMemory(this, (int)(offset + (long)(i * sarray[i].size())), true);
                    sarray[i].read();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(cls)) {
            NativeMapped[] array = (NativeMapped[])result;
            NativeMappedConverter tc = NativeMappedConverter.getInstance(cls);
            int size = Native.getNativeSize(result.getClass(), result) / array.length;
            for (int i = 0; i < array.length; ++i) {
                Object value = this.getValue(offset + (long)(size * i), tc.nativeType(), array[i]);
                array[i] = (NativeMapped)tc.fromNative(value, new FromNativeContext(cls));
            }
        } else {
            throw new IllegalArgumentException("Reading array of " + cls + " from memory not supported");
        }
    }

    public byte getByte(long offset) {
        return Native.getByte(this, this.peer, offset);
    }

    public char getChar(long offset) {
        return Native.getChar(this, this.peer, offset);
    }

    public short getShort(long offset) {
        return Native.getShort(this, this.peer, offset);
    }

    public int getInt(long offset) {
        return Native.getInt(this, this.peer, offset);
    }

    public long getLong(long offset) {
        return Native.getLong(this, this.peer, offset);
    }

    public NativeLong getNativeLong(long offset) {
        return new NativeLong(NativeLong.SIZE == 8 ? this.getLong(offset) : (long)this.getInt(offset));
    }

    public float getFloat(long offset) {
        return Native.getFloat(this, this.peer, offset);
    }

    public double getDouble(long offset) {
        return Native.getDouble(this, this.peer, offset);
    }

    public Pointer getPointer(long offset) {
        return Native.getPointer(this.peer + offset);
    }

    public ByteBuffer getByteBuffer(long offset, long length) {
        return Native.getDirectByteBuffer(this, this.peer, offset, length).order(ByteOrder.nativeOrder());
    }

    @Deprecated
    public String getString(long offset, boolean wide) {
        return wide ? this.getWideString(offset) : this.getString(offset);
    }

    public String getWideString(long offset) {
        return Native.getWideString(this, this.peer, offset);
    }

    public String getString(long offset) {
        return this.getString(offset, Native.getDefaultStringEncoding());
    }

    public String getString(long offset, String encoding) {
        return Native.getString(this, offset, encoding);
    }

    public byte[] getByteArray(long offset, int arraySize) {
        byte[] buf = new byte[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public char[] getCharArray(long offset, int arraySize) {
        char[] buf = new char[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public short[] getShortArray(long offset, int arraySize) {
        short[] buf = new short[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public int[] getIntArray(long offset, int arraySize) {
        int[] buf = new int[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public long[] getLongArray(long offset, int arraySize) {
        long[] buf = new long[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public float[] getFloatArray(long offset, int arraySize) {
        float[] buf = new float[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public double[] getDoubleArray(long offset, int arraySize) {
        double[] buf = new double[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public Pointer[] getPointerArray(long offset) {
        ArrayList<Pointer> array = new ArrayList<Pointer>();
        int addOffset = 0;
        Pointer p = this.getPointer(offset);
        while (p != null) {
            array.add(p);
            p = this.getPointer(offset + (long)(addOffset += SIZE));
        }
        return array.toArray(new Pointer[array.size()]);
    }

    public Pointer[] getPointerArray(long offset, int arraySize) {
        Pointer[] buf = new Pointer[arraySize];
        this.read(offset, buf, 0, arraySize);
        return buf;
    }

    public String[] getStringArray(long offset) {
        return this.getStringArray(offset, -1, Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long offset, String encoding) {
        return this.getStringArray(offset, -1, encoding);
    }

    public String[] getStringArray(long offset, int length) {
        return this.getStringArray(offset, length, Native.getDefaultStringEncoding());
    }

    @Deprecated
    public String[] getStringArray(long offset, boolean wide) {
        return this.getStringArray(offset, -1, wide);
    }

    public String[] getWideStringArray(long offset) {
        return this.getWideStringArray(offset, -1);
    }

    public String[] getWideStringArray(long offset, int length) {
        return this.getStringArray(offset, length, "--WIDE-STRING--");
    }

    @Deprecated
    public String[] getStringArray(long offset, int length, boolean wide) {
        return this.getStringArray(offset, length, wide ? "--WIDE-STRING--" : Native.getDefaultStringEncoding());
    }

    public String[] getStringArray(long offset, int length, String encoding) {
        ArrayList<String> strings = new ArrayList<String>();
        int addOffset = 0;
        if (length != -1) {
            Pointer p = this.getPointer(offset + (long)addOffset);
            int count = 0;
            while (count++ < length) {
                String s = p == null ? null : ("--WIDE-STRING--".equals(encoding) ? p.getWideString(0L) : p.getString(0L, encoding));
                strings.add(s);
                if (count >= length) continue;
                p = this.getPointer(offset + (long)(addOffset += SIZE));
            }
        } else {
            Pointer p;
            while ((p = this.getPointer(offset + (long)addOffset)) != null) {
                String s = p == null ? null : ("--WIDE-STRING--".equals(encoding) ? p.getWideString(0L) : p.getString(0L, encoding));
                strings.add(s);
                addOffset += SIZE;
            }
        }
        return strings.toArray(new String[strings.size()]);
    }

    void setValue(long offset, Object value, Class<?> type2) {
        if (type2 == Boolean.TYPE || type2 == Boolean.class) {
            this.setInt(offset, Boolean.TRUE.equals(value) ? -1 : 0);
        } else if (type2 == Byte.TYPE || type2 == Byte.class) {
            this.setByte(offset, value == null ? (byte)0 : (Byte)value);
        } else if (type2 == Short.TYPE || type2 == Short.class) {
            this.setShort(offset, value == null ? (short)0 : (Short)value);
        } else if (type2 == Character.TYPE || type2 == Character.class) {
            this.setChar(offset, value == null ? (char)'\u0000' : ((Character)value).charValue());
        } else if (type2 == Integer.TYPE || type2 == Integer.class) {
            this.setInt(offset, value == null ? 0 : (Integer)value);
        } else if (type2 == Long.TYPE || type2 == Long.class) {
            this.setLong(offset, value == null ? 0L : (Long)value);
        } else if (type2 == Float.TYPE || type2 == Float.class) {
            this.setFloat(offset, value == null ? 0.0f : ((Float)value).floatValue());
        } else if (type2 == Double.TYPE || type2 == Double.class) {
            this.setDouble(offset, value == null ? 0.0 : (Double)value);
        } else if (type2 == Pointer.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (type2 == String.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (type2 == WString.class) {
            this.setPointer(offset, (Pointer)value);
        } else if (Structure.class.isAssignableFrom(type2)) {
            Structure s = (Structure)value;
            if (Structure.ByReference.class.isAssignableFrom(type2)) {
                this.setPointer(offset, s == null ? null : s.getPointer());
                if (s != null) {
                    s.autoWrite();
                }
            } else {
                s.useMemory(this, (int)offset, true);
                s.write();
            }
        } else if (Callback.class.isAssignableFrom(type2)) {
            this.setPointer(offset, CallbackReference.getFunctionPointer((Callback)value));
        } else if (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(type2)) {
            Pointer p = value == null ? null : Native.getDirectBufferPointer((Buffer)value);
            this.setPointer(offset, p);
        } else if (NativeMapped.class.isAssignableFrom(type2)) {
            NativeMappedConverter tc = NativeMappedConverter.getInstance(type2);
            Class<?> nativeType = tc.nativeType();
            this.setValue(offset, tc.toNative(value, new ToNativeContext()), nativeType);
        } else if (type2.isArray()) {
            this.writeArray(offset, value, type2.getComponentType());
        } else {
            throw new IllegalArgumentException("Writing " + type2 + " to memory is not supported");
        }
    }

    private void writeArray(long offset, Object value, Class<?> cls) {
        if (cls == Byte.TYPE) {
            byte[] buf = (byte[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Short.TYPE) {
            short[] buf = (short[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Character.TYPE) {
            char[] buf = (char[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Integer.TYPE) {
            int[] buf = (int[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Long.TYPE) {
            long[] buf = (long[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Float.TYPE) {
            float[] buf = (float[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (cls == Double.TYPE) {
            double[] buf = (double[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (Pointer.class.isAssignableFrom(cls)) {
            Pointer[] buf = (Pointer[])value;
            this.write(offset, buf, 0, buf.length);
        } else if (Structure.class.isAssignableFrom(cls)) {
            Structure[] sbuf = (Structure[])value;
            if (Structure.ByReference.class.isAssignableFrom(cls)) {
                Pointer[] buf = new Pointer[sbuf.length];
                for (int i = 0; i < sbuf.length; ++i) {
                    if (sbuf[i] == null) {
                        buf[i] = null;
                        continue;
                    }
                    buf[i] = sbuf[i].getPointer();
                    sbuf[i].write();
                }
                this.write(offset, buf, 0, buf.length);
            } else {
                Structure first = sbuf[0];
                if (first == null) {
                    sbuf[0] = first = Structure.newInstance(cls, this.share(offset));
                } else {
                    first.useMemory(this, (int)offset, true);
                }
                first.write();
                Structure[] tmp = first.toArray(sbuf.length);
                for (int i = 1; i < sbuf.length; ++i) {
                    if (sbuf[i] == null) {
                        sbuf[i] = tmp[i];
                    } else {
                        sbuf[i].useMemory(this, (int)(offset + (long)(i * sbuf[i].size())), true);
                    }
                    sbuf[i].write();
                }
            }
        } else if (NativeMapped.class.isAssignableFrom(cls)) {
            NativeMapped[] buf = (NativeMapped[])value;
            NativeMappedConverter tc = NativeMappedConverter.getInstance(cls);
            Class<?> nativeType = tc.nativeType();
            int size = Native.getNativeSize(value.getClass(), value) / buf.length;
            for (int i = 0; i < buf.length; ++i) {
                Object element = tc.toNative(buf[i], new ToNativeContext());
                this.setValue(offset + (long)(i * size), element, nativeType);
            }
        } else {
            throw new IllegalArgumentException("Writing array of " + cls + " to memory not supported");
        }
    }

    public void setMemory(long offset, long length, byte value) {
        Native.setMemory(this, this.peer, offset, length, value);
    }

    public void setByte(long offset, byte value) {
        Native.setByte(this, this.peer, offset, value);
    }

    public void setShort(long offset, short value) {
        Native.setShort(this, this.peer, offset, value);
    }

    public void setChar(long offset, char value) {
        Native.setChar(this, this.peer, offset, value);
    }

    public void setInt(long offset, int value) {
        Native.setInt(this, this.peer, offset, value);
    }

    public void setLong(long offset, long value) {
        Native.setLong(this, this.peer, offset, value);
    }

    public void setNativeLong(long offset, NativeLong value) {
        if (NativeLong.SIZE == 8) {
            this.setLong(offset, value.longValue());
        } else {
            this.setInt(offset, value.intValue());
        }
    }

    public void setFloat(long offset, float value) {
        Native.setFloat(this, this.peer, offset, value);
    }

    public void setDouble(long offset, double value) {
        Native.setDouble(this, this.peer, offset, value);
    }

    public void setPointer(long offset, Pointer value) {
        Native.setPointer(this, this.peer, offset, value != null ? value.peer : 0L);
    }

    @Deprecated
    public void setString(long offset, String value, boolean wide) {
        if (wide) {
            this.setWideString(offset, value);
        } else {
            this.setString(offset, value);
        }
    }

    public void setWideString(long offset, String value) {
        Native.setWideString(this, this.peer, offset, value);
    }

    public void setString(long offset, WString value) {
        this.setWideString(offset, value == null ? null : value.toString());
    }

    public void setString(long offset, String value) {
        this.setString(offset, value, Native.getDefaultStringEncoding());
    }

    public void setString(long offset, String value, String encoding) {
        byte[] data = Native.getBytes(value, encoding);
        this.write(offset, data, 0, data.length);
        this.setByte(offset + (long)data.length, (byte)0);
    }

    public String dump(long offset, int size) {
        int BYTES_PER_ROW = 4;
        String TITLE = "memory dump";
        StringWriter sw = new StringWriter("memory dump".length() + 2 + size * 2 + size / 4 * 4);
        PrintWriter out = new PrintWriter(sw);
        out.println("memory dump");
        for (int i = 0; i < size; ++i) {
            byte b = this.getByte(offset + (long)i);
            if (i % 4 == 0) {
                out.print("[");
            }
            if (b >= 0 && b < 16) {
                out.print("0");
            }
            out.print(Integer.toHexString(b & 0xFF));
            if (i % 4 != 3 || i >= size - 1) continue;
            out.println("]");
        }
        if (sw.getBuffer().charAt(sw.getBuffer().length() - 2) != ']') {
            out.println("]");
        }
        return sw.toString();
    }

    public String toString() {
        return "native@0x" + Long.toHexString(this.peer);
    }

    public static long nativeValue(Pointer p) {
        return p == null ? 0L : p.peer;
    }

    public static void nativeValue(Pointer p, long value) {
        p.peer = value;
    }

    static {
        if (SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        NULL = null;
    }

    /*
     * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
     */
    private static class Opaque
    extends Pointer {
        private final String MSG = "This pointer is opaque: " + this;

        private Opaque(long peer) {
            super(peer);
        }

        @Override
        public Pointer share(long offset, long size) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void clear(long size) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public long indexOf(long offset, byte value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, byte[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, char[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, short[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, int[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, long[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, float[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, double[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void read(long bOff, Pointer[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, byte[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, char[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, short[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, int[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, long[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, float[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, double[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void write(long bOff, Pointer[] buf, int index, int length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public ByteBuffer getByteBuffer(long offset, long length) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public byte getByte(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public char getChar(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public short getShort(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public int getInt(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public long getLong(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public float getFloat(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public double getDouble(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public Pointer getPointer(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String getString(long bOff, String encoding) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String getWideString(long bOff) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setByte(long bOff, byte value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setChar(long bOff, char value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setShort(long bOff, short value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setInt(long bOff, int value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setLong(long bOff, long value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setFloat(long bOff, float value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setDouble(long bOff, double value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setPointer(long offset, Pointer value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setString(long offset, String value, String encoding) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setWideString(long offset, String value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public void setMemory(long offset, long size, byte value) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String dump(long offset, int size) {
            throw new UnsupportedOperationException(this.MSG);
        }

        @Override
        public String toString() {
            return "const@0x" + Long.toHexString(this.peer);
        }
    }
}

