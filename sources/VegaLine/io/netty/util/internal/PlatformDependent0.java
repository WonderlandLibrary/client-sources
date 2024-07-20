/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.Cleaner0;
import io.netty.util.internal.ConstantTimeUtils;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.misc.Unsafe;

final class PlatformDependent0 {
    private static final InternalLogger logger;
    private static final Unsafe UNSAFE;
    private static final long ADDRESS_FIELD_OFFSET;
    private static final long BYTE_ARRAY_BASE_OFFSET;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;
    static final int HASH_CODE_ASCII_SEED = -1028477387;
    static final int HASH_CODE_C1 = 461845907;
    static final int HASH_CODE_C2 = 461845907;
    private static final long UNSAFE_COPY_THRESHOLD = 0x100000L;
    private static final boolean UNALIGNED;

    static boolean isUnaligned() {
        return UNALIGNED;
    }

    static boolean hasUnsafe() {
        return UNSAFE != null;
    }

    static boolean unalignedAccess() {
        return UNALIGNED;
    }

    static void throwException(Throwable cause) {
        UNSAFE.throwException(ObjectUtil.checkNotNull(cause, "cause"));
    }

    static boolean hasDirectBufferNoCleanerConstructor() {
        return DIRECT_BUFFER_CONSTRUCTOR != null;
    }

    static ByteBuffer reallocateDirectNoCleaner(ByteBuffer buffer, int capacity) {
        return PlatformDependent0.newDirectBuffer(UNSAFE.reallocateMemory(PlatformDependent0.directBufferAddress(buffer), capacity), capacity);
    }

    static ByteBuffer allocateDirectNoCleaner(int capacity) {
        return PlatformDependent0.newDirectBuffer(UNSAFE.allocateMemory(capacity), capacity);
    }

    static ByteBuffer newDirectBuffer(long address, int capacity) {
        ObjectUtil.checkPositiveOrZero(address, "address");
        ObjectUtil.checkPositiveOrZero(capacity, "capacity");
        try {
            return (ByteBuffer)DIRECT_BUFFER_CONSTRUCTOR.newInstance(address, capacity);
        } catch (Throwable cause) {
            if (cause instanceof Error) {
                throw (Error)cause;
            }
            throw new Error(cause);
        }
    }

    static void freeDirectBuffer(ByteBuffer buffer) {
        Cleaner0.freeDirectBuffer(buffer);
    }

    static long directBufferAddress(ByteBuffer buffer) {
        return PlatformDependent0.getLong(buffer, ADDRESS_FIELD_OFFSET);
    }

    static long byteArrayBaseOffset() {
        return BYTE_ARRAY_BASE_OFFSET;
    }

    static Object getObject(Object object, long fieldOffset) {
        return UNSAFE.getObject(object, fieldOffset);
    }

    static int getInt(Object object, long fieldOffset) {
        return UNSAFE.getInt(object, fieldOffset);
    }

    private static long getLong(Object object, long fieldOffset) {
        return UNSAFE.getLong(object, fieldOffset);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static byte getByte(long address) {
        return UNSAFE.getByte(address);
    }

    static short getShort(long address) {
        return UNSAFE.getShort(address);
    }

    static int getInt(long address) {
        return UNSAFE.getInt(address);
    }

    static long getLong(long address) {
        return UNSAFE.getLong(address);
    }

    static byte getByte(byte[] data, int index) {
        return UNSAFE.getByte((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index);
    }

    static short getShort(byte[] data, int index) {
        return UNSAFE.getShort((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index);
    }

    static int getInt(byte[] data, int index) {
        return UNSAFE.getInt((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index);
    }

    static long getLong(byte[] data, int index) {
        return UNSAFE.getLong((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index);
    }

    static void putByte(long address, byte value) {
        UNSAFE.putByte(address, value);
    }

    static void putShort(long address, short value) {
        UNSAFE.putShort(address, value);
    }

    static void putInt(long address, int value) {
        UNSAFE.putInt(address, value);
    }

    static void putLong(long address, long value) {
        UNSAFE.putLong(address, value);
    }

    static void putByte(byte[] data, int index, byte value) {
        UNSAFE.putByte((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index, value);
    }

    static void putShort(byte[] data, int index, short value) {
        UNSAFE.putShort((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index, value);
    }

    static void putInt(byte[] data, int index, int value) {
        UNSAFE.putInt((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index, value);
    }

    static void putLong(byte[] data, int index, long value) {
        UNSAFE.putLong((Object)data, BYTE_ARRAY_BASE_OFFSET + (long)index, value);
    }

    static void copyMemory(long srcAddr, long dstAddr, long length) {
        while (length > 0L) {
            long size = Math.min(length, 0x100000L);
            UNSAFE.copyMemory(srcAddr, dstAddr, size);
            length -= size;
            srcAddr += size;
            dstAddr += size;
        }
    }

    static void copyMemory(Object src, long srcOffset, Object dst, long dstOffset, long length) {
        while (length > 0L) {
            long size = Math.min(length, 0x100000L);
            UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, size);
            length -= size;
            srcOffset += size;
            dstOffset += size;
        }
    }

    static void setMemory(long address, long bytes, byte value) {
        UNSAFE.setMemory(address, bytes, value);
    }

    static void setMemory(Object o, long offset, long bytes, byte value) {
        UNSAFE.setMemory(o, offset, bytes, value);
    }

    static boolean equals(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
        if (length == 0) {
            return true;
        }
        long baseOffset1 = BYTE_ARRAY_BASE_OFFSET + (long)startPos1;
        long baseOffset2 = BYTE_ARRAY_BASE_OFFSET + (long)startPos2;
        int remainingBytes = length & 7;
        long end = baseOffset1 + (long)remainingBytes;
        long i = baseOffset1 - 8L + (long)length;
        long j = baseOffset2 - 8L + (long)length;
        while (i >= end) {
            if (UNSAFE.getLong((Object)bytes1, i) != UNSAFE.getLong((Object)bytes2, j)) {
                return false;
            }
            i -= 8L;
            j -= 8L;
        }
        if (remainingBytes >= 4 && UNSAFE.getInt((Object)bytes1, baseOffset1 + (long)(remainingBytes -= 4)) != UNSAFE.getInt((Object)bytes2, baseOffset2 + (long)remainingBytes)) {
            return false;
        }
        if (remainingBytes >= 2) {
            return UNSAFE.getChar((Object)bytes1, baseOffset1) == UNSAFE.getChar((Object)bytes2, baseOffset2) && (remainingBytes == 2 || bytes1[startPos1 + 2] == bytes2[startPos2 + 2]);
        }
        return bytes1[startPos1] == bytes2[startPos2];
    }

    static int equalsConstantTime(byte[] bytes1, int startPos1, byte[] bytes2, int startPos2, int length) {
        long result = 0L;
        long baseOffset1 = BYTE_ARRAY_BASE_OFFSET + (long)startPos1;
        long baseOffset2 = BYTE_ARRAY_BASE_OFFSET + (long)startPos2;
        int remainingBytes = length & 7;
        long end = baseOffset1 + (long)remainingBytes;
        long i = baseOffset1 - 8L + (long)length;
        long j = baseOffset2 - 8L + (long)length;
        while (i >= end) {
            result |= UNSAFE.getLong((Object)bytes1, i) ^ UNSAFE.getLong((Object)bytes2, j);
            i -= 8L;
            j -= 8L;
        }
        switch (remainingBytes) {
            case 7: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getInt((Object)bytes1, baseOffset1 + 3L) ^ UNSAFE.getInt((Object)bytes2, baseOffset2 + 3L)) | (long)(UNSAFE.getChar((Object)bytes1, baseOffset1 + 1L) ^ UNSAFE.getChar((Object)bytes2, baseOffset2 + 1L)) | (long)(UNSAFE.getByte((Object)bytes1, baseOffset1) ^ UNSAFE.getByte((Object)bytes2, baseOffset2)), 0L);
            }
            case 6: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getInt((Object)bytes1, baseOffset1 + 2L) ^ UNSAFE.getInt((Object)bytes2, baseOffset2 + 2L)) | (long)(UNSAFE.getChar((Object)bytes1, baseOffset1) ^ UNSAFE.getChar((Object)bytes2, baseOffset2)), 0L);
            }
            case 5: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getInt((Object)bytes1, baseOffset1 + 1L) ^ UNSAFE.getInt((Object)bytes2, baseOffset2 + 1L)) | (long)(UNSAFE.getByte((Object)bytes1, baseOffset1) ^ UNSAFE.getByte((Object)bytes2, baseOffset2)), 0L);
            }
            case 4: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getInt((Object)bytes1, baseOffset1) ^ UNSAFE.getInt((Object)bytes2, baseOffset2)), 0L);
            }
            case 3: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getChar((Object)bytes1, baseOffset1 + 1L) ^ UNSAFE.getChar((Object)bytes2, baseOffset2 + 1L)) | (long)(UNSAFE.getByte((Object)bytes1, baseOffset1) ^ UNSAFE.getByte((Object)bytes2, baseOffset2)), 0L);
            }
            case 2: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getChar((Object)bytes1, baseOffset1) ^ UNSAFE.getChar((Object)bytes2, baseOffset2)), 0L);
            }
            case 1: {
                return ConstantTimeUtils.equalsConstantTime(result | (long)(UNSAFE.getByte((Object)bytes1, baseOffset1) ^ UNSAFE.getByte((Object)bytes2, baseOffset2)), 0L);
            }
        }
        return ConstantTimeUtils.equalsConstantTime(result, 0L);
    }

    static int hashCodeAscii(byte[] bytes, int startPos, int length) {
        int hash = -1028477387;
        long baseOffset = BYTE_ARRAY_BASE_OFFSET + (long)startPos;
        int remainingBytes = length & 7;
        long end = baseOffset + (long)remainingBytes;
        for (long i = baseOffset - 8L + (long)length; i >= end; i -= 8L) {
            hash = PlatformDependent0.hashCodeAsciiCompute(UNSAFE.getLong((Object)bytes, i), hash);
        }
        switch (remainingBytes) {
            case 7: {
                return ((hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)bytes, baseOffset))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)bytes, baseOffset + 1L))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)bytes, baseOffset + 3L));
            }
            case 6: {
                return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)bytes, baseOffset))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)bytes, baseOffset + 2L));
            }
            case 5: {
                return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)bytes, baseOffset))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)bytes, baseOffset + 1L));
            }
            case 4: {
                return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)bytes, baseOffset));
            }
            case 3: {
                return (hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)bytes, baseOffset))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)bytes, baseOffset + 1L));
            }
            case 2: {
                return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)bytes, baseOffset));
            }
            case 1: {
                return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)bytes, baseOffset));
            }
        }
        return hash;
    }

    static int hashCodeAsciiCompute(long value, int hash) {
        return hash * 461845907 + PlatformDependent0.hashCodeAsciiSanitize((int)value) * 461845907 + (int)((value & 0x1F1F1F1F00000000L) >>> 32);
    }

    static int hashCodeAsciiSanitize(int value) {
        return value & 0x1F1F1F1F;
    }

    static int hashCodeAsciiSanitize(short value) {
        return value & 0x1F1F;
    }

    static int hashCodeAsciiSanitize(byte value) {
        return value & 0x1F;
    }

    static ClassLoader getClassLoader(final Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        });
    }

    static ClassLoader getContextClassLoader() {
        if (System.getSecurityManager() == null) {
            return Thread.currentThread().getContextClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return ClassLoader.getSystemClassLoader();
            }
        });
    }

    static int addressSize() {
        return UNSAFE.addressSize();
    }

    static long allocateMemory(long size) {
        return UNSAFE.allocateMemory(size);
    }

    static void freeMemory(long address) {
        UNSAFE.freeMemory(address);
    }

    private PlatformDependent0() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Unsafe unsafe;
        ByteBuffer direct;
        logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
        Field addressField = null;
        if (PlatformDependent.isExplicitNoUnsafe()) {
            direct = null;
            addressField = null;
            unsafe = null;
        } else {
            long byteArrayIndexScale;
            Unsafe finalUnsafe;
            direct = ByteBuffer.allocateDirect(1);
            Object maybeUnsafe = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    try {
                        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                        Throwable cause = ReflectionUtil.trySetAccessible(unsafeField);
                        if (cause != null) {
                            return cause;
                        }
                        return unsafeField.get(null);
                    } catch (NoSuchFieldException e) {
                        return e;
                    } catch (SecurityException e) {
                        return e;
                    } catch (IllegalAccessException e) {
                        return e;
                    }
                }
            });
            if (maybeUnsafe instanceof Exception) {
                unsafe = null;
                logger.debug("sun.misc.Unsafe.theUnsafe: unavailable", (Exception)maybeUnsafe);
            } else {
                unsafe = (Unsafe)maybeUnsafe;
                logger.debug("sun.misc.Unsafe.theUnsafe: available");
            }
            if (unsafe != null) {
                finalUnsafe = unsafe;
                Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        try {
                            finalUnsafe.getClass().getDeclaredMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
                            return null;
                        } catch (NoSuchMethodException e) {
                            return e;
                        } catch (SecurityException e) {
                            return e;
                        }
                    }
                });
                if (maybeException == null) {
                    logger.debug("sun.misc.Unsafe.copyMemory: available");
                } else {
                    unsafe = null;
                    logger.debug("sun.misc.Unsafe.copyMemory: unavailable", (Throwable)maybeException);
                }
            }
            if (unsafe != null) {
                finalUnsafe = unsafe;
                Object maybeAddressField = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        try {
                            Field field = Buffer.class.getDeclaredField("address");
                            long offset = finalUnsafe.objectFieldOffset(field);
                            long address = finalUnsafe.getLong((Object)direct, offset);
                            if (address == 0L) {
                                return null;
                            }
                            return field;
                        } catch (NoSuchFieldException e) {
                            return e;
                        } catch (SecurityException e) {
                            return e;
                        }
                    }
                });
                if (maybeAddressField instanceof Field) {
                    addressField = (Field)maybeAddressField;
                    logger.debug("java.nio.Buffer.address: available");
                } else {
                    logger.debug("java.nio.Buffer.address: unavailable", (Throwable)maybeAddressField);
                    unsafe = null;
                }
            }
            if (unsafe != null && (byteArrayIndexScale = (long)unsafe.arrayIndexScale(byte[].class)) != 1L) {
                logger.debug("unsafe.arrayIndexScale is {} (expected: 1). Not using unsafe.", (Object)byteArrayIndexScale);
                unsafe = null;
            }
        }
        UNSAFE = unsafe;
        if (unsafe == null) {
            ADDRESS_FIELD_OFFSET = -1L;
            BYTE_ARRAY_BASE_OFFSET = -1L;
            UNALIGNED = false;
            DIRECT_BUFFER_CONSTRUCTOR = null;
        } else {
            boolean unaligned;
            Constructor directBufferConstructor;
            long address = -1L;
            try {
                Object maybeDirectBufferConstructor = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        try {
                            Constructor<?> constructor = direct.getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                            Throwable cause = ReflectionUtil.trySetAccessible(constructor);
                            if (cause != null) {
                                return cause;
                            }
                            return constructor;
                        } catch (NoSuchMethodException e) {
                            return e;
                        } catch (SecurityException e) {
                            return e;
                        }
                    }
                });
                if (maybeDirectBufferConstructor instanceof Constructor) {
                    address = UNSAFE.allocateMemory(1L);
                    try {
                        ((Constructor)maybeDirectBufferConstructor).newInstance(address, 1);
                        directBufferConstructor = (Constructor)maybeDirectBufferConstructor;
                        logger.debug("direct buffer constructor: available");
                    } catch (InstantiationException e) {
                        directBufferConstructor = null;
                    } catch (IllegalAccessException e) {
                        directBufferConstructor = null;
                    } catch (InvocationTargetException e) {
                        directBufferConstructor = null;
                    }
                } else {
                    logger.debug("direct buffer constructor: unavailable", (Throwable)maybeDirectBufferConstructor);
                    directBufferConstructor = null;
                }
            } finally {
                if (address != -1L) {
                    UNSAFE.freeMemory(address);
                }
            }
            DIRECT_BUFFER_CONSTRUCTOR = directBufferConstructor;
            ADDRESS_FIELD_OFFSET = PlatformDependent0.objectFieldOffset(addressField);
            BYTE_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
            Object maybeUnaligned = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    try {
                        Class<?> bitsClass = Class.forName("java.nio.Bits", false, PlatformDependent.getSystemClassLoader());
                        Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned", new Class[0]);
                        Throwable cause = ReflectionUtil.trySetAccessible(unalignedMethod);
                        if (cause != null) {
                            return cause;
                        }
                        return unalignedMethod.invoke(null, new Object[0]);
                    } catch (NoSuchMethodException e) {
                        return e;
                    } catch (SecurityException e) {
                        return e;
                    } catch (IllegalAccessException e) {
                        return e;
                    } catch (ClassNotFoundException e) {
                        return e;
                    } catch (InvocationTargetException e) {
                        return e;
                    }
                }
            });
            if (maybeUnaligned instanceof Boolean) {
                unaligned = (Boolean)maybeUnaligned;
                logger.debug("java.nio.Bits.unaligned: available, {}", (Object)unaligned);
            } else {
                String arch = SystemPropertyUtil.get("os.arch", "");
                unaligned = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
                Throwable t = (Throwable)maybeUnaligned;
                logger.debug("java.nio.Bits.unaligned: unavailable {}", (Object)unaligned, (Object)t);
            }
            UNALIGNED = unaligned;
        }
        logger.debug("java.nio.DirectByteBuffer.<init>(long, int): {}", (Object)(DIRECT_BUFFER_CONSTRUCTOR != null ? "available" : "unavailable"));
        if (direct != null) {
            PlatformDependent0.freeDirectBuffer(direct);
        }
    }
}

