/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.primitives.Longs;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class LittleEndianByteArray {
    private static final LittleEndianBytes byteArray;
    static final boolean $assertionsDisabled;

    static long load64(byte[] byArray, int n) {
        if (!$assertionsDisabled && byArray.length < n + 8) {
            throw new AssertionError();
        }
        return byteArray.getLongLittleEndian(byArray, n);
    }

    static long load64Safely(byte[] byArray, int n, int n2) {
        long l = 0L;
        int n3 = Math.min(n2, 8);
        for (int i = 0; i < n3; ++i) {
            l |= ((long)byArray[n + i] & 0xFFL) << i * 8;
        }
        return l;
    }

    static void store64(byte[] byArray, int n, long l) {
        if (!($assertionsDisabled || n >= 0 && n + 8 <= byArray.length)) {
            throw new AssertionError();
        }
        byteArray.putLongLittleEndian(byArray, n, l);
    }

    static int load32(byte[] byArray, int n) {
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 3] & 0xFF) << 24;
    }

    static boolean usingUnsafe() {
        return byteArray instanceof UnsafeByteArray;
    }

    private LittleEndianByteArray() {
    }

    static {
        $assertionsDisabled = !LittleEndianByteArray.class.desiredAssertionStatus();
        Enum enum_ = JavaLittleEndianBytes.INSTANCE;
        try {
            String string = System.getProperty("os.arch");
            if ("amd64".equals(string) || "aarch64".equals(string)) {
                enum_ = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? UnsafeByteArray.UNSAFE_LITTLE_ENDIAN : UnsafeByteArray.UNSAFE_BIG_ENDIAN;
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        byteArray = enum_;
    }

    private static enum JavaLittleEndianBytes implements LittleEndianBytes
    {
        INSTANCE{

            @Override
            public long getLongLittleEndian(byte[] byArray, int n) {
                return Longs.fromBytes(byArray[n + 7], byArray[n + 6], byArray[n + 5], byArray[n + 4], byArray[n + 3], byArray[n + 2], byArray[n + 1], byArray[n]);
            }

            @Override
            public void putLongLittleEndian(byte[] byArray, int n, long l) {
                long l2 = 255L;
                for (int i = 0; i < 8; ++i) {
                    byArray[n + i] = (byte)((l & l2) >> i * 8);
                    l2 <<= 8;
                }
            }
        };


        private JavaLittleEndianBytes() {
        }

        JavaLittleEndianBytes(1 var3_3) {
            this();
        }
    }

    private static enum UnsafeByteArray implements LittleEndianBytes
    {
        UNSAFE_LITTLE_ENDIAN{

            @Override
            public long getLongLittleEndian(byte[] byArray, int n) {
                return UnsafeByteArray.access$200().getLong((Object)byArray, (long)n + (long)UnsafeByteArray.access$100());
            }

            @Override
            public void putLongLittleEndian(byte[] byArray, int n, long l) {
                UnsafeByteArray.access$200().putLong((Object)byArray, (long)n + (long)UnsafeByteArray.access$100(), l);
            }
        }
        ,
        UNSAFE_BIG_ENDIAN{

            @Override
            public long getLongLittleEndian(byte[] byArray, int n) {
                long l = UnsafeByteArray.access$200().getLong((Object)byArray, (long)n + (long)UnsafeByteArray.access$100());
                return Long.reverseBytes(l);
            }

            @Override
            public void putLongLittleEndian(byte[] byArray, int n, long l) {
                long l2 = Long.reverseBytes(l);
                UnsafeByteArray.access$200().putLong((Object)byArray, (long)n + (long)UnsafeByteArray.access$100(), l2);
            }
        };

        private static final Unsafe theUnsafe;
        private static final int BYTE_ARRAY_BASE_OFFSET;

        private UnsafeByteArray() {
        }

        private static Unsafe getUnsafe() {
            try {
                return Unsafe.getUnsafe();
            } catch (SecurityException securityException) {
                try {
                    return AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>(){

                        @Override
                        public Unsafe run() throws Exception {
                            Class<Unsafe> clazz = Unsafe.class;
                            for (Field field : clazz.getDeclaredFields()) {
                                field.setAccessible(false);
                                Object object = field.get(null);
                                if (!clazz.isInstance(object)) continue;
                                return (Unsafe)clazz.cast(object);
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }

                        @Override
                        public Object run() throws Exception {
                            return this.run();
                        }
                    });
                } catch (PrivilegedActionException privilegedActionException) {
                    throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
                }
            }
        }

        UnsafeByteArray(1 var3_3) {
            this();
        }

        static int access$100() {
            return BYTE_ARRAY_BASE_OFFSET;
        }

        static Unsafe access$200() {
            return theUnsafe;
        }

        static {
            theUnsafe = UnsafeByteArray.getUnsafe();
            BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset(byte[].class);
            if (theUnsafe.arrayIndexScale(byte[].class) != 1) {
                throw new AssertionError();
            }
        }
    }

    private static interface LittleEndianBytes {
        public long getLongLittleEndian(byte[] var1, int var2);

        public void putLongLittleEndian(byte[] var1, int var2, long var3);
    }
}

