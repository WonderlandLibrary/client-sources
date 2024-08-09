/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.ConstantTimeUtils;
import io.netty.util.internal.ObjectUtil;
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
    private static final long ADDRESS_FIELD_OFFSET;
    private static final long BYTE_ARRAY_BASE_OFFSET;
    private static final Constructor<?> DIRECT_BUFFER_CONSTRUCTOR;
    private static final Throwable EXPLICIT_NO_UNSAFE_CAUSE;
    private static final Method ALLOCATE_ARRAY_METHOD;
    private static final int JAVA_VERSION;
    private static final boolean IS_ANDROID;
    private static final Throwable UNSAFE_UNAVAILABILITY_CAUSE;
    private static final Object INTERNAL_UNSAFE;
    private static final boolean IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE;
    static final Unsafe UNSAFE;
    static final int HASH_CODE_ASCII_SEED = -1028477387;
    static final int HASH_CODE_C1 = -862048943;
    static final int HASH_CODE_C2 = 461845907;
    private static final long UNSAFE_COPY_THRESHOLD = 0x100000L;
    private static final boolean UNALIGNED;
    static final boolean $assertionsDisabled;

    static boolean isExplicitNoUnsafe() {
        return EXPLICIT_NO_UNSAFE_CAUSE == null;
    }

    private static Throwable explicitNoUnsafeCause0() {
        boolean bl = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        logger.debug("-Dio.netty.noUnsafe: {}", (Object)bl);
        if (bl) {
            logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
            return new UnsupportedOperationException("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
        }
        String string = SystemPropertyUtil.contains("io.netty.tryUnsafe") ? "io.netty.tryUnsafe" : "org.jboss.netty.tryUnsafe";
        if (!SystemPropertyUtil.getBoolean(string, true)) {
            String string2 = "sun.misc.Unsafe: unavailable (" + string + ")";
            logger.debug(string2);
            return new UnsupportedOperationException(string2);
        }
        return null;
    }

    static boolean isUnaligned() {
        return UNALIGNED;
    }

    static boolean hasUnsafe() {
        return UNSAFE != null;
    }

    static Throwable getUnsafeUnavailabilityCause() {
        return UNSAFE_UNAVAILABILITY_CAUSE;
    }

    static boolean unalignedAccess() {
        return UNALIGNED;
    }

    static void throwException(Throwable throwable) {
        UNSAFE.throwException(ObjectUtil.checkNotNull(throwable, "cause"));
    }

    static boolean hasDirectBufferNoCleanerConstructor() {
        return DIRECT_BUFFER_CONSTRUCTOR != null;
    }

    static ByteBuffer reallocateDirectNoCleaner(ByteBuffer byteBuffer, int n) {
        return PlatformDependent0.newDirectBuffer(UNSAFE.reallocateMemory(PlatformDependent0.directBufferAddress(byteBuffer), n), n);
    }

    static ByteBuffer allocateDirectNoCleaner(int n) {
        return PlatformDependent0.newDirectBuffer(UNSAFE.allocateMemory(n), n);
    }

    static boolean hasAllocateArrayMethod() {
        return ALLOCATE_ARRAY_METHOD != null;
    }

    static byte[] allocateUninitializedArray(int n) {
        try {
            return (byte[])ALLOCATE_ARRAY_METHOD.invoke(INTERNAL_UNSAFE, Byte.TYPE, n);
        } catch (IllegalAccessException illegalAccessException) {
            throw new Error(illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            throw new Error(invocationTargetException);
        }
    }

    static ByteBuffer newDirectBuffer(long l, int n) {
        ObjectUtil.checkPositiveOrZero(n, "capacity");
        try {
            return (ByteBuffer)DIRECT_BUFFER_CONSTRUCTOR.newInstance(l, n);
        } catch (Throwable throwable) {
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            throw new Error(throwable);
        }
    }

    static long directBufferAddress(ByteBuffer byteBuffer) {
        return PlatformDependent0.getLong(byteBuffer, ADDRESS_FIELD_OFFSET);
    }

    static long byteArrayBaseOffset() {
        return BYTE_ARRAY_BASE_OFFSET;
    }

    static Object getObject(Object object, long l) {
        return UNSAFE.getObject(object, l);
    }

    static int getInt(Object object, long l) {
        return UNSAFE.getInt(object, l);
    }

    private static long getLong(Object object, long l) {
        return UNSAFE.getLong(object, l);
    }

    static long objectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
    }

    static byte getByte(long l) {
        return UNSAFE.getByte(l);
    }

    static short getShort(long l) {
        return UNSAFE.getShort(l);
    }

    static int getInt(long l) {
        return UNSAFE.getInt(l);
    }

    static long getLong(long l) {
        return UNSAFE.getLong(l);
    }

    static byte getByte(byte[] byArray, int n) {
        return UNSAFE.getByte((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n);
    }

    static short getShort(byte[] byArray, int n) {
        return UNSAFE.getShort((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n);
    }

    static int getInt(byte[] byArray, int n) {
        return UNSAFE.getInt((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n);
    }

    static long getLong(byte[] byArray, int n) {
        return UNSAFE.getLong((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n);
    }

    static void putByte(long l, byte by) {
        UNSAFE.putByte(l, by);
    }

    static void putShort(long l, short s) {
        UNSAFE.putShort(l, s);
    }

    static void putInt(long l, int n) {
        UNSAFE.putInt(l, n);
    }

    static void putLong(long l, long l2) {
        UNSAFE.putLong(l, l2);
    }

    static void putByte(byte[] byArray, int n, byte by) {
        UNSAFE.putByte((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, by);
    }

    static void putShort(byte[] byArray, int n, short s) {
        UNSAFE.putShort((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, s);
    }

    static void putInt(byte[] byArray, int n, int n2) {
        UNSAFE.putInt((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, n2);
    }

    static void putLong(byte[] byArray, int n, long l) {
        UNSAFE.putLong((Object)byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, l);
    }

    static void copyMemory(long l, long l2, long l3) {
        while (l3 > 0L) {
            long l4 = Math.min(l3, 0x100000L);
            UNSAFE.copyMemory(l, l2, l4);
            l3 -= l4;
            l += l4;
            l2 += l4;
        }
    }

    static void copyMemory(Object object, long l, Object object2, long l2, long l3) {
        while (l3 > 0L) {
            long l4 = Math.min(l3, 0x100000L);
            UNSAFE.copyMemory(object, l, object2, l2, l4);
            l3 -= l4;
            l += l4;
            l2 += l4;
        }
    }

    static void setMemory(long l, long l2, byte by) {
        UNSAFE.setMemory(l, l2, by);
    }

    static void setMemory(Object object, long l, long l2, byte by) {
        UNSAFE.setMemory(object, l, l2, by);
    }

    static boolean equals(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        if (n3 <= 0) {
            return false;
        }
        long l = BYTE_ARRAY_BASE_OFFSET + (long)n;
        long l2 = BYTE_ARRAY_BASE_OFFSET + (long)n2;
        int n4 = n3 & 7;
        long l3 = l + (long)n4;
        long l4 = l - 8L + (long)n3;
        long l5 = l2 - 8L + (long)n3;
        while (l4 >= l3) {
            if (UNSAFE.getLong((Object)byArray, l4) != UNSAFE.getLong((Object)byArray2, l5)) {
                return true;
            }
            l4 -= 8L;
            l5 -= 8L;
        }
        if (n4 >= 4 && UNSAFE.getInt((Object)byArray, l + (long)(n4 -= 4)) != UNSAFE.getInt((Object)byArray2, l2 + (long)n4)) {
            return true;
        }
        if (n4 >= 2) {
            return UNSAFE.getChar((Object)byArray, l) == UNSAFE.getChar((Object)byArray2, l2) && (n4 == 2 || byArray[n + 2] == byArray2[n2 + 2]);
        }
        return byArray[n] == byArray2[n2];
    }

    static int equalsConstantTime(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        long l = 0L;
        long l2 = BYTE_ARRAY_BASE_OFFSET + (long)n;
        long l3 = BYTE_ARRAY_BASE_OFFSET + (long)n2;
        int n4 = n3 & 7;
        long l4 = l2 + (long)n4;
        long l5 = l2 - 8L + (long)n3;
        long l6 = l3 - 8L + (long)n3;
        while (l5 >= l4) {
            l |= UNSAFE.getLong((Object)byArray, l5) ^ UNSAFE.getLong((Object)byArray2, l6);
            l5 -= 8L;
            l6 -= 8L;
        }
        switch (n4) {
            case 7: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getInt((Object)byArray, l2 + 3L) ^ UNSAFE.getInt((Object)byArray2, l3 + 3L)) | (long)(UNSAFE.getChar((Object)byArray, l2 + 1L) ^ UNSAFE.getChar((Object)byArray2, l3 + 1L)) | (long)(UNSAFE.getByte((Object)byArray, l2) ^ UNSAFE.getByte((Object)byArray2, l3)), 0L);
            }
            case 6: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getInt((Object)byArray, l2 + 2L) ^ UNSAFE.getInt((Object)byArray2, l3 + 2L)) | (long)(UNSAFE.getChar((Object)byArray, l2) ^ UNSAFE.getChar((Object)byArray2, l3)), 0L);
            }
            case 5: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getInt((Object)byArray, l2 + 1L) ^ UNSAFE.getInt((Object)byArray2, l3 + 1L)) | (long)(UNSAFE.getByte((Object)byArray, l2) ^ UNSAFE.getByte((Object)byArray2, l3)), 0L);
            }
            case 4: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getInt((Object)byArray, l2) ^ UNSAFE.getInt((Object)byArray2, l3)), 0L);
            }
            case 3: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getChar((Object)byArray, l2 + 1L) ^ UNSAFE.getChar((Object)byArray2, l3 + 1L)) | (long)(UNSAFE.getByte((Object)byArray, l2) ^ UNSAFE.getByte((Object)byArray2, l3)), 0L);
            }
            case 2: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getChar((Object)byArray, l2) ^ UNSAFE.getChar((Object)byArray2, l3)), 0L);
            }
            case 1: {
                return ConstantTimeUtils.equalsConstantTime(l | (long)(UNSAFE.getByte((Object)byArray, l2) ^ UNSAFE.getByte((Object)byArray2, l3)), 0L);
            }
        }
        return ConstantTimeUtils.equalsConstantTime(l, 0L);
    }

    static boolean isZero(byte[] byArray, int n, int n2) {
        if (n2 <= 0) {
            return false;
        }
        long l = BYTE_ARRAY_BASE_OFFSET + (long)n;
        int n3 = n2 & 7;
        long l2 = l + (long)n3;
        for (long i = l - 8L + (long)n2; i >= l2; i -= 8L) {
            if (UNSAFE.getLong((Object)byArray, i) == 0L) continue;
            return true;
        }
        if (n3 >= 4 && UNSAFE.getInt((Object)byArray, l + (long)(n3 -= 4)) != 0) {
            return true;
        }
        if (n3 >= 2) {
            return UNSAFE.getChar((Object)byArray, l) == '\u0000' && (n3 == 2 || byArray[n + 2] == 0);
        }
        return byArray[n] == 0;
    }

    static int hashCodeAscii(byte[] byArray, int n, int n2) {
        int n3 = -1028477387;
        long l = BYTE_ARRAY_BASE_OFFSET + (long)n;
        int n4 = n2 & 7;
        long l2 = l + (long)n4;
        for (long i = l - 8L + (long)n2; i >= l2; i -= 8L) {
            n3 = PlatformDependent0.hashCodeAsciiCompute(UNSAFE.getLong((Object)byArray, i), n3);
        }
        switch (n4) {
            case 7: {
                return ((n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)byArray, l))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)byArray, l + 1L))) * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)byArray, l + 3L));
            }
            case 6: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)byArray, l))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)byArray, l + 2L));
            }
            case 5: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)byArray, l))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)byArray, l + 1L));
            }
            case 4: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getInt((Object)byArray, l));
            }
            case 3: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)byArray, l))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)byArray, l + 1L));
            }
            case 2: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getShort((Object)byArray, l));
            }
            case 1: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(UNSAFE.getByte((Object)byArray, l));
            }
        }
        return n3;
    }

    static int hashCodeAsciiCompute(long l, int n) {
        return n * -862048943 + PlatformDependent0.hashCodeAsciiSanitize((int)l) * 461845907 + (int)((l & 0x1F1F1F1F00000000L) >>> 32);
    }

    static int hashCodeAsciiSanitize(int n) {
        return n & 0x1F1F1F1F;
    }

    static int hashCodeAsciiSanitize(short s) {
        return s & 0x1F1F;
    }

    static int hashCodeAsciiSanitize(byte by) {
        return by & 0x1F;
    }

    static ClassLoader getClassLoader(Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        }
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(clazz){
            final Class val$clazz;
            {
                this.val$clazz = clazz;
            }

            @Override
            public ClassLoader run() {
                return this.val$clazz.getClassLoader();
            }

            @Override
            public Object run() {
                return this.run();
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

            @Override
            public Object run() {
                return this.run();
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

            @Override
            public Object run() {
                return this.run();
            }
        });
    }

    static int addressSize() {
        return UNSAFE.addressSize();
    }

    static long allocateMemory(long l) {
        return UNSAFE.allocateMemory(l);
    }

    static void freeMemory(long l) {
        UNSAFE.freeMemory(l);
    }

    static long reallocateMemory(long l, long l2) {
        return UNSAFE.reallocateMemory(l, l2);
    }

    static boolean isAndroid() {
        return IS_ANDROID;
    }

    private static boolean isAndroid0() {
        String string = SystemPropertyUtil.get("java.vm.name");
        boolean bl = "Dalvik".equals(string);
        if (bl) {
            logger.debug("Platform: Android");
        }
        return bl;
    }

    private static boolean explicitTryReflectionSetAccessible0() {
        return SystemPropertyUtil.getBoolean("io.netty.tryReflectionSetAccessible", PlatformDependent0.javaVersion() < 9);
    }

    static boolean isExplicitTryReflectionSetAccessible() {
        return IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE;
    }

    static int javaVersion() {
        return JAVA_VERSION;
    }

    private static int javaVersion0() {
        int n = PlatformDependent0.isAndroid0() ? 6 : PlatformDependent0.majorVersionFromJavaSpecificationVersion();
        logger.debug("Java version: {}", (Object)n);
        return n;
    }

    static int majorVersionFromJavaSpecificationVersion() {
        return PlatformDependent0.majorVersion(SystemPropertyUtil.get("java.specification.version", "1.6"));
    }

    static int majorVersion(String string) {
        String[] stringArray = string.split("\\.");
        int[] nArray = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            nArray[i] = Integer.parseInt(stringArray[i]);
        }
        if (nArray[0] == 1) {
            if (!$assertionsDisabled && nArray[1] < 6) {
                throw new AssertionError();
            }
            return nArray[1];
        }
        return nArray[0];
    }

    private PlatformDependent0() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Object object;
        Unsafe unsafe;
        ByteBuffer byteBuffer;
        $assertionsDisabled = !PlatformDependent0.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(PlatformDependent0.class);
        EXPLICIT_NO_UNSAFE_CAUSE = PlatformDependent0.explicitNoUnsafeCause0();
        JAVA_VERSION = PlatformDependent0.javaVersion0();
        IS_ANDROID = PlatformDependent0.isAndroid0();
        IS_EXPLICIT_TRY_REFLECTION_SET_ACCESSIBLE = PlatformDependent0.explicitTryReflectionSetAccessible0();
        Field field = null;
        Method method = null;
        Throwable throwable = null;
        Object object2 = null;
        throwable = EXPLICIT_NO_UNSAFE_CAUSE;
        if (throwable != null) {
            byteBuffer = null;
            field = null;
            unsafe = null;
            object2 = null;
        } else {
            long l;
            Object object3;
            Unsafe unsafe2;
            byteBuffer = ByteBuffer.allocateDirect(1);
            object = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    try {
                        Field field = Unsafe.class.getDeclaredField("theUnsafe");
                        Throwable throwable = ReflectionUtil.trySetAccessible(field, false);
                        if (throwable != null) {
                            return throwable;
                        }
                        return field.get(null);
                    } catch (NoSuchFieldException noSuchFieldException) {
                        return noSuchFieldException;
                    } catch (SecurityException securityException) {
                        return securityException;
                    } catch (IllegalAccessException illegalAccessException) {
                        return illegalAccessException;
                    } catch (NoClassDefFoundError noClassDefFoundError) {
                        return noClassDefFoundError;
                    }
                }
            });
            if (object instanceof Throwable) {
                unsafe = null;
                throwable = (Throwable)object;
                logger.debug("sun.misc.Unsafe.theUnsafe: unavailable", (Throwable)object);
            } else {
                unsafe = (Unsafe)object;
                logger.debug("sun.misc.Unsafe.theUnsafe: available");
            }
            if (unsafe != null) {
                unsafe2 = unsafe;
                object3 = AccessController.doPrivileged(new PrivilegedAction<Object>(unsafe2){
                    final Unsafe val$finalUnsafe;
                    {
                        this.val$finalUnsafe = unsafe;
                    }

                    @Override
                    public Object run() {
                        try {
                            this.val$finalUnsafe.getClass().getDeclaredMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
                            return null;
                        } catch (NoSuchMethodException noSuchMethodException) {
                            return noSuchMethodException;
                        } catch (SecurityException securityException) {
                            return securityException;
                        }
                    }
                });
                if (object3 == null) {
                    logger.debug("sun.misc.Unsafe.copyMemory: available");
                } else {
                    unsafe = null;
                    throwable = (Throwable)object3;
                    logger.debug("sun.misc.Unsafe.copyMemory: unavailable", (Throwable)object3);
                }
            }
            if (unsafe != null) {
                unsafe2 = unsafe;
                object3 = AccessController.doPrivileged(new PrivilegedAction<Object>(unsafe2, byteBuffer){
                    final Unsafe val$finalUnsafe;
                    final ByteBuffer val$direct;
                    {
                        this.val$finalUnsafe = unsafe;
                        this.val$direct = byteBuffer;
                    }

                    @Override
                    public Object run() {
                        try {
                            Field field = Buffer.class.getDeclaredField("address");
                            long l = this.val$finalUnsafe.objectFieldOffset(field);
                            long l2 = this.val$finalUnsafe.getLong((Object)this.val$direct, l);
                            if (l2 == 0L) {
                                return null;
                            }
                            return field;
                        } catch (NoSuchFieldException noSuchFieldException) {
                            return noSuchFieldException;
                        } catch (SecurityException securityException) {
                            return securityException;
                        }
                    }
                });
                if (object3 instanceof Field) {
                    field = (Field)object3;
                    logger.debug("java.nio.Buffer.address: available");
                } else {
                    throwable = (Throwable)object3;
                    logger.debug("java.nio.Buffer.address: unavailable", (Throwable)object3);
                    unsafe = null;
                }
            }
            if (unsafe != null && (l = (long)unsafe.arrayIndexScale(byte[].class)) != 1L) {
                logger.debug("unsafe.arrayIndexScale is {} (expected: 1). Not using unsafe.", (Object)l);
                throwable = new UnsupportedOperationException("Unexpected unsafe.arrayIndexScale");
                unsafe = null;
            }
        }
        UNSAFE_UNAVAILABILITY_CAUSE = throwable;
        UNSAFE = unsafe;
        if (unsafe == null) {
            ADDRESS_FIELD_OFFSET = -1L;
            BYTE_ARRAY_BASE_OFFSET = -1L;
            UNALIGNED = false;
            DIRECT_BUFFER_CONSTRUCTOR = null;
            ALLOCATE_ARRAY_METHOD = null;
        } else {
            Object object4;
            Object object5;
            boolean bl;
            long l = -1L;
            try {
                Object object6 = AccessController.doPrivileged(new PrivilegedAction<Object>(byteBuffer){
                    final ByteBuffer val$direct;
                    {
                        this.val$direct = byteBuffer;
                    }

                    @Override
                    public Object run() {
                        try {
                            Constructor<?> constructor = this.val$direct.getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                            Throwable throwable = ReflectionUtil.trySetAccessible(constructor, true);
                            if (throwable != null) {
                                return throwable;
                            }
                            return constructor;
                        } catch (NoSuchMethodException noSuchMethodException) {
                            return noSuchMethodException;
                        } catch (SecurityException securityException) {
                            return securityException;
                        }
                    }
                });
                if (object6 instanceof Constructor) {
                    l = UNSAFE.allocateMemory(1L);
                    try {
                        ((Constructor)object6).newInstance(l, 1);
                        object = (Constructor)object6;
                        logger.debug("direct buffer constructor: available");
                    } catch (InstantiationException instantiationException) {
                        object = null;
                    } catch (IllegalAccessException illegalAccessException) {
                        object = null;
                    } catch (InvocationTargetException invocationTargetException) {
                        object = null;
                    }
                } else {
                    logger.debug("direct buffer constructor: unavailable", (Throwable)object6);
                    object = null;
                }
            } finally {
                if (l != -1L) {
                    UNSAFE.freeMemory(l);
                }
            }
            DIRECT_BUFFER_CONSTRUCTOR = object;
            ADDRESS_FIELD_OFFSET = PlatformDependent0.objectFieldOffset(field);
            BYTE_ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(byte[].class);
            Object object7 = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                @Override
                public Object run() {
                    try {
                        Class<?> clazz = Class.forName("java.nio.Bits", false, PlatformDependent0.getSystemClassLoader());
                        Method method = clazz.getDeclaredMethod("unaligned", new Class[0]);
                        Throwable throwable = ReflectionUtil.trySetAccessible(method, true);
                        if (throwable != null) {
                            return throwable;
                        }
                        return method.invoke(null, new Object[0]);
                    } catch (NoSuchMethodException noSuchMethodException) {
                        return noSuchMethodException;
                    } catch (SecurityException securityException) {
                        return securityException;
                    } catch (IllegalAccessException illegalAccessException) {
                        return illegalAccessException;
                    } catch (ClassNotFoundException classNotFoundException) {
                        return classNotFoundException;
                    } catch (InvocationTargetException invocationTargetException) {
                        return invocationTargetException;
                    }
                }
            });
            if (object7 instanceof Boolean) {
                bl = (Boolean)object7;
                logger.debug("java.nio.Bits.unaligned: available, {}", (Object)bl);
            } else {
                object5 = SystemPropertyUtil.get("os.arch", "");
                bl = ((String)object5).matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
                object4 = (Throwable)object7;
                logger.debug("java.nio.Bits.unaligned: unavailable {}", (Object)bl, object4);
            }
            UNALIGNED = bl;
            if (PlatformDependent0.javaVersion() >= 9) {
                object5 = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        try {
                            Class<?> clazz = PlatformDependent0.getClassLoader(PlatformDependent0.class).loadClass("jdk.internal.misc.Unsafe");
                            Method method = clazz.getDeclaredMethod("getUnsafe", new Class[0]);
                            return method.invoke(null, new Object[0]);
                        } catch (Throwable throwable) {
                            return throwable;
                        }
                    }
                });
                if (!(object5 instanceof Throwable) && (object5 = AccessController.doPrivileged(new PrivilegedAction<Object>(object4 = (object2 = object5)){
                    final Object val$finalInternalUnsafe;
                    {
                        this.val$finalInternalUnsafe = object;
                    }

                    @Override
                    public Object run() {
                        try {
                            return this.val$finalInternalUnsafe.getClass().getDeclaredMethod("allocateUninitializedArray", Class.class, Integer.TYPE);
                        } catch (NoSuchMethodException noSuchMethodException) {
                            return noSuchMethodException;
                        } catch (SecurityException securityException) {
                            return securityException;
                        }
                    }
                })) instanceof Method) {
                    try {
                        Method method2 = (Method)object5;
                        byte[] byArray = (byte[])method2.invoke(object4, Byte.TYPE, 8);
                        if (!$assertionsDisabled && byArray.length != 8) {
                            throw new AssertionError();
                        }
                        method = method2;
                    } catch (IllegalAccessException illegalAccessException) {
                        object5 = illegalAccessException;
                    } catch (InvocationTargetException invocationTargetException) {
                        object5 = invocationTargetException;
                    }
                }
                if (object5 instanceof Throwable) {
                    logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable", (Throwable)object5);
                } else {
                    logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): available");
                }
            } else {
                logger.debug("jdk.internal.misc.Unsafe.allocateUninitializedArray(int): unavailable prior to Java9");
            }
            ALLOCATE_ARRAY_METHOD = method;
        }
        INTERNAL_UNSAFE = object2;
        logger.debug("java.nio.DirectByteBuffer.<init>(long, int): {}", (Object)(DIRECT_BUFFER_CONSTRUCTOR != null ? "available" : "unavailable"));
    }
}

