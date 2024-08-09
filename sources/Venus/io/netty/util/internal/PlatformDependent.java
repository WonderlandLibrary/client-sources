/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.Cleaner;
import io.netty.util.internal.CleanerJava6;
import io.netty.util.internal.CleanerJava9;
import io.netty.util.internal.ConstantTimeUtils;
import io.netty.util.internal.LongAdderCounter;
import io.netty.util.internal.LongCounter;
import io.netty.util.internal.OutOfDirectMemoryError;
import io.netty.util.internal.PlatformDependent0;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThreadLocalRandom;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscChunkedArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.MpscUnboundedArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.SpscLinkedQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscGrowableAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;
import io.netty.util.internal.shaded.org.jctools.queues.atomic.SpscLinkedAtomicQueue;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import java.io.File;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PlatformDependent {
    private static final InternalLogger logger;
    private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN;
    private static final boolean IS_WINDOWS;
    private static final boolean IS_OSX;
    private static final boolean MAYBE_SUPER_USER;
    private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    private static final Throwable UNSAFE_UNAVAILABILITY_CAUSE;
    private static final boolean DIRECT_BUFFER_PREFERRED;
    private static final long MAX_DIRECT_MEMORY;
    private static final int MPSC_CHUNK_SIZE = 1024;
    private static final int MIN_MAX_MPSC_CAPACITY = 2048;
    private static final int MAX_ALLOWED_MPSC_CAPACITY = 0x40000000;
    private static final long BYTE_ARRAY_BASE_OFFSET;
    private static final File TMPDIR;
    private static final int BIT_MODE;
    private static final String NORMALIZED_ARCH;
    private static final String NORMALIZED_OS;
    private static final int ADDRESS_SIZE;
    private static final boolean USE_DIRECT_BUFFER_NO_CLEANER;
    private static final AtomicLong DIRECT_MEMORY_COUNTER;
    private static final long DIRECT_MEMORY_LIMIT;
    private static final ThreadLocalRandomProvider RANDOM_PROVIDER;
    private static final Cleaner CLEANER;
    private static final int UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD;
    public static final boolean BIG_ENDIAN_NATIVE_ORDER;
    private static final Cleaner NOOP;
    static final boolean $assertionsDisabled;

    public static boolean hasDirectBufferNoCleanerConstructor() {
        return PlatformDependent0.hasDirectBufferNoCleanerConstructor();
    }

    public static byte[] allocateUninitializedArray(int n) {
        return UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD < 0 || UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD > n ? new byte[n] : PlatformDependent0.allocateUninitializedArray(n);
    }

    public static boolean isAndroid() {
        return PlatformDependent0.isAndroid();
    }

    public static boolean isWindows() {
        return IS_WINDOWS;
    }

    public static boolean isOsx() {
        return IS_OSX;
    }

    public static boolean maybeSuperUser() {
        return MAYBE_SUPER_USER;
    }

    public static int javaVersion() {
        return PlatformDependent0.javaVersion();
    }

    public static boolean canEnableTcpNoDelayByDefault() {
        return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    }

    public static boolean hasUnsafe() {
        return UNSAFE_UNAVAILABILITY_CAUSE == null;
    }

    public static Throwable getUnsafeUnavailabilityCause() {
        return UNSAFE_UNAVAILABILITY_CAUSE;
    }

    public static boolean isUnaligned() {
        return PlatformDependent0.isUnaligned();
    }

    public static boolean directBufferPreferred() {
        return DIRECT_BUFFER_PREFERRED;
    }

    public static long maxDirectMemory() {
        return MAX_DIRECT_MEMORY;
    }

    public static File tmpdir() {
        return TMPDIR;
    }

    public static int bitMode() {
        return BIT_MODE;
    }

    public static int addressSize() {
        return ADDRESS_SIZE;
    }

    public static long allocateMemory(long l) {
        return PlatformDependent0.allocateMemory(l);
    }

    public static void freeMemory(long l) {
        PlatformDependent0.freeMemory(l);
    }

    public static long reallocateMemory(long l, long l2) {
        return PlatformDependent0.reallocateMemory(l, l2);
    }

    public static void throwException(Throwable throwable) {
        if (PlatformDependent.hasUnsafe()) {
            PlatformDependent0.throwException(throwable);
        } else {
            PlatformDependent.throwException0(throwable);
        }
    }

    private static <E extends Throwable> void throwException0(Throwable throwable) throws E {
        throw throwable;
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static LongCounter newLongCounter() {
        if (PlatformDependent.javaVersion() >= 8) {
            return new LongAdderCounter();
        }
        return new AtomicLongCounter(null);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int n) {
        return new ConcurrentHashMap(n);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int n, float f) {
        return new ConcurrentHashMap(n, f);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int n, float f, int n2) {
        return new ConcurrentHashMap(n, f, n2);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
        return new ConcurrentHashMap<K, V>(map);
    }

    public static void freeDirectBuffer(ByteBuffer byteBuffer) {
        CLEANER.freeDirectBuffer(byteBuffer);
    }

    public static long directBufferAddress(ByteBuffer byteBuffer) {
        return PlatformDependent0.directBufferAddress(byteBuffer);
    }

    public static ByteBuffer directBuffer(long l, int n) {
        if (PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            return PlatformDependent0.newDirectBuffer(l, n);
        }
        throw new UnsupportedOperationException("sun.misc.Unsafe or java.nio.DirectByteBuffer.<init>(long, int) not available");
    }

    public static int getInt(Object object, long l) {
        return PlatformDependent0.getInt(object, l);
    }

    public static byte getByte(long l) {
        return PlatformDependent0.getByte(l);
    }

    public static short getShort(long l) {
        return PlatformDependent0.getShort(l);
    }

    public static int getInt(long l) {
        return PlatformDependent0.getInt(l);
    }

    public static long getLong(long l) {
        return PlatformDependent0.getLong(l);
    }

    public static byte getByte(byte[] byArray, int n) {
        return PlatformDependent0.getByte(byArray, n);
    }

    public static short getShort(byte[] byArray, int n) {
        return PlatformDependent0.getShort(byArray, n);
    }

    public static int getInt(byte[] byArray, int n) {
        return PlatformDependent0.getInt(byArray, n);
    }

    public static long getLong(byte[] byArray, int n) {
        return PlatformDependent0.getLong(byArray, n);
    }

    private static long getLongSafe(byte[] byArray, int n) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (long)byArray[n] << 56 | ((long)byArray[n + 1] & 0xFFL) << 48 | ((long)byArray[n + 2] & 0xFFL) << 40 | ((long)byArray[n + 3] & 0xFFL) << 32 | ((long)byArray[n + 4] & 0xFFL) << 24 | ((long)byArray[n + 5] & 0xFFL) << 16 | ((long)byArray[n + 6] & 0xFFL) << 8 | (long)byArray[n + 7] & 0xFFL;
        }
        return (long)byArray[n] & 0xFFL | ((long)byArray[n + 1] & 0xFFL) << 8 | ((long)byArray[n + 2] & 0xFFL) << 16 | ((long)byArray[n + 3] & 0xFFL) << 24 | ((long)byArray[n + 4] & 0xFFL) << 32 | ((long)byArray[n + 5] & 0xFFL) << 40 | ((long)byArray[n + 6] & 0xFFL) << 48 | (long)byArray[n + 7] << 56;
    }

    private static int getIntSafe(byte[] byArray, int n) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return byArray[n] << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
        }
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16 | byArray[n + 3] << 24;
    }

    private static short getShortSafe(byte[] byArray, int n) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return (short)(byArray[n] << 8 | byArray[n + 1] & 0xFF);
        }
        return (short)(byArray[n] & 0xFF | byArray[n + 1] << 8);
    }

    private static int hashCodeAsciiCompute(CharSequence charSequence, int n, int n2) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return n2 * -862048943 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, n + 4) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, n);
        }
        return n2 * -862048943 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, n) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, n + 4);
    }

    private static int hashCodeAsciiSanitizeInt(CharSequence charSequence, int n) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return charSequence.charAt(n + 3) & 0x1F | (charSequence.charAt(n + 2) & 0x1F) << 8 | (charSequence.charAt(n + 1) & 0x1F) << 16 | (charSequence.charAt(n) & 0x1F) << 24;
        }
        return (charSequence.charAt(n + 3) & 0x1F) << 24 | (charSequence.charAt(n + 2) & 0x1F) << 16 | (charSequence.charAt(n + 1) & 0x1F) << 8 | charSequence.charAt(n) & 0x1F;
    }

    private static int hashCodeAsciiSanitizeShort(CharSequence charSequence, int n) {
        if (BIG_ENDIAN_NATIVE_ORDER) {
            return charSequence.charAt(n + 1) & 0x1F | (charSequence.charAt(n) & 0x1F) << 8;
        }
        return (charSequence.charAt(n + 1) & 0x1F) << 8 | charSequence.charAt(n) & 0x1F;
    }

    private static int hashCodeAsciiSanitizeByte(char c) {
        return c & 0x1F;
    }

    public static void putByte(long l, byte by) {
        PlatformDependent0.putByte(l, by);
    }

    public static void putShort(long l, short s) {
        PlatformDependent0.putShort(l, s);
    }

    public static void putInt(long l, int n) {
        PlatformDependent0.putInt(l, n);
    }

    public static void putLong(long l, long l2) {
        PlatformDependent0.putLong(l, l2);
    }

    public static void putByte(byte[] byArray, int n, byte by) {
        PlatformDependent0.putByte(byArray, n, by);
    }

    public static void putShort(byte[] byArray, int n, short s) {
        PlatformDependent0.putShort(byArray, n, s);
    }

    public static void putInt(byte[] byArray, int n, int n2) {
        PlatformDependent0.putInt(byArray, n, n2);
    }

    public static void putLong(byte[] byArray, int n, long l) {
        PlatformDependent0.putLong(byArray, n, l);
    }

    public static void copyMemory(long l, long l2, long l3) {
        PlatformDependent0.copyMemory(l, l2, l3);
    }

    public static void copyMemory(byte[] byArray, int n, long l, long l2) {
        PlatformDependent0.copyMemory(byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, null, l, l2);
    }

    public static void copyMemory(long l, byte[] byArray, int n, long l2) {
        PlatformDependent0.copyMemory(null, l, byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, l2);
    }

    public static void setMemory(byte[] byArray, int n, long l, byte by) {
        PlatformDependent0.setMemory(byArray, BYTE_ARRAY_BASE_OFFSET + (long)n, l, by);
    }

    public static void setMemory(long l, long l2, byte by) {
        PlatformDependent0.setMemory(l, l2, by);
    }

    public static ByteBuffer allocateDirectNoCleaner(int n) {
        if (!$assertionsDisabled && !USE_DIRECT_BUFFER_NO_CLEANER) {
            throw new AssertionError();
        }
        PlatformDependent.incrementMemoryCounter(n);
        try {
            return PlatformDependent0.allocateDirectNoCleaner(n);
        } catch (Throwable throwable) {
            PlatformDependent.decrementMemoryCounter(n);
            PlatformDependent.throwException(throwable);
            return null;
        }
    }

    public static ByteBuffer reallocateDirectNoCleaner(ByteBuffer byteBuffer, int n) {
        if (!$assertionsDisabled && !USE_DIRECT_BUFFER_NO_CLEANER) {
            throw new AssertionError();
        }
        int n2 = n - byteBuffer.capacity();
        PlatformDependent.incrementMemoryCounter(n2);
        try {
            return PlatformDependent0.reallocateDirectNoCleaner(byteBuffer, n);
        } catch (Throwable throwable) {
            PlatformDependent.decrementMemoryCounter(n2);
            PlatformDependent.throwException(throwable);
            return null;
        }
    }

    public static void freeDirectNoCleaner(ByteBuffer byteBuffer) {
        if (!$assertionsDisabled && !USE_DIRECT_BUFFER_NO_CLEANER) {
            throw new AssertionError();
        }
        int n = byteBuffer.capacity();
        PlatformDependent0.freeMemory(PlatformDependent0.directBufferAddress(byteBuffer));
        PlatformDependent.decrementMemoryCounter(n);
    }

    private static void incrementMemoryCounter(int n) {
        block1: {
            long l;
            long l2;
            if (DIRECT_MEMORY_COUNTER == null) break block1;
            do {
                if ((l = (l2 = DIRECT_MEMORY_COUNTER.get()) + (long)n) <= DIRECT_MEMORY_LIMIT) continue;
                throw new OutOfDirectMemoryError("failed to allocate " + n + " byte(s) of direct memory (used: " + l2 + ", max: " + DIRECT_MEMORY_LIMIT + ')');
            } while (!DIRECT_MEMORY_COUNTER.compareAndSet(l2, l));
        }
    }

    private static void decrementMemoryCounter(int n) {
        if (DIRECT_MEMORY_COUNTER != null) {
            long l = DIRECT_MEMORY_COUNTER.addAndGet(-n);
            if (!$assertionsDisabled && l < 0L) {
                throw new AssertionError();
            }
        }
    }

    public static boolean useDirectBufferNoCleaner() {
        return USE_DIRECT_BUFFER_NO_CLEANER;
    }

    public static boolean equals(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        return !PlatformDependent.hasUnsafe() || !PlatformDependent0.unalignedAccess() ? PlatformDependent.equalsSafe(byArray, n, byArray2, n2, n3) : PlatformDependent0.equals(byArray, n, byArray2, n2, n3);
    }

    public static boolean isZero(byte[] byArray, int n, int n2) {
        return !PlatformDependent.hasUnsafe() || !PlatformDependent0.unalignedAccess() ? PlatformDependent.isZeroSafe(byArray, n, n2) : PlatformDependent0.isZero(byArray, n, n2);
    }

    public static int equalsConstantTime(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        return !PlatformDependent.hasUnsafe() || !PlatformDependent0.unalignedAccess() ? ConstantTimeUtils.equalsConstantTime(byArray, n, byArray2, n2, n3) : PlatformDependent0.equalsConstantTime(byArray, n, byArray2, n2, n3);
    }

    public static int hashCodeAscii(byte[] byArray, int n, int n2) {
        return !PlatformDependent.hasUnsafe() || !PlatformDependent0.unalignedAccess() ? PlatformDependent.hashCodeAsciiSafe(byArray, n, n2) : PlatformDependent0.hashCodeAscii(byArray, n, n2);
    }

    public static int hashCodeAscii(CharSequence charSequence) {
        int n = -1028477387;
        int n2 = charSequence.length() & 7;
        switch (charSequence.length()) {
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: {
                n = PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 24, PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 16, PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 8, n)));
                break;
            }
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: {
                n = PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 16, PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 8, n));
                break;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                n = PlatformDependent.hashCodeAsciiCompute(charSequence, charSequence.length() - 8, n);
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                break;
            }
            default: {
                for (int i = charSequence.length() - 8; i >= n2; i -= 8) {
                    n = PlatformDependent.hashCodeAsciiCompute(charSequence, i, n);
                }
            }
        }
        switch (n2) {
            case 7: {
                return ((n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeShort(charSequence, 1)) * -862048943 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, 3);
            }
            case 6: {
                return (n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeShort(charSequence, 0)) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, 2);
            }
            case 5: {
                return (n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, 1);
            }
            case 4: {
                return n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeInt(charSequence, 0);
            }
            case 3: {
                return (n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeByte(charSequence.charAt(0))) * 461845907 + PlatformDependent.hashCodeAsciiSanitizeShort(charSequence, 1);
            }
            case 2: {
                return n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeShort(charSequence, 0);
            }
            case 1: {
                return n * -862048943 + PlatformDependent.hashCodeAsciiSanitizeByte(charSequence.charAt(0));
            }
        }
        return n;
    }

    public static <T> Queue<T> newMpscQueue() {
        return Mpsc.newMpscQueue();
    }

    public static <T> Queue<T> newMpscQueue(int n) {
        return Mpsc.newMpscQueue(n);
    }

    public static <T> Queue<T> newSpscQueue() {
        return PlatformDependent.hasUnsafe() ? new SpscLinkedQueue() : new SpscLinkedAtomicQueue();
    }

    public static <T> Queue<T> newFixedMpscQueue(int n) {
        return PlatformDependent.hasUnsafe() ? new MpscArrayQueue(n) : new MpscAtomicArrayQueue(n);
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        return PlatformDependent0.getClassLoader(clazz);
    }

    public static ClassLoader getContextClassLoader() {
        return PlatformDependent0.getContextClassLoader();
    }

    public static ClassLoader getSystemClassLoader() {
        return PlatformDependent0.getSystemClassLoader();
    }

    public static <C> Deque<C> newConcurrentDeque() {
        if (PlatformDependent.javaVersion() < 7) {
            return new LinkedBlockingDeque();
        }
        return new ConcurrentLinkedDeque();
    }

    public static Random threadLocalRandom() {
        return RANDOM_PROVIDER.current();
    }

    private static boolean isWindows0() {
        boolean bl = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
        if (bl) {
            logger.debug("Platform: Windows");
        }
        return bl;
    }

    private static boolean isOsx0() {
        boolean bl;
        String string = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
        boolean bl2 = bl = string.startsWith("macosx") || string.startsWith("osx");
        if (bl) {
            logger.debug("Platform: MacOS");
        }
        return bl;
    }

    private static boolean maybeSuperUser0() {
        String string = SystemPropertyUtil.get("user.name");
        if (PlatformDependent.isWindows()) {
            return "Administrator".equals(string);
        }
        return "root".equals(string) || "toor".equals(string);
    }

    private static Throwable unsafeUnavailabilityCause0() {
        if (PlatformDependent.isAndroid()) {
            logger.debug("sun.misc.Unsafe: unavailable (Android)");
            return new UnsupportedOperationException("sun.misc.Unsafe: unavailable (Android)");
        }
        Throwable throwable = PlatformDependent0.getUnsafeUnavailabilityCause();
        if (throwable != null) {
            return throwable;
        }
        try {
            boolean bl = PlatformDependent0.hasUnsafe();
            logger.debug("sun.misc.Unsafe: {}", (Object)(bl ? "available" : "unavailable"));
            return bl ? null : PlatformDependent0.getUnsafeUnavailabilityCause();
        } catch (Throwable throwable2) {
            logger.trace("Could not determine if Unsafe is available", throwable2);
            return new UnsupportedOperationException("Could not determine if Unsafe is available", throwable2);
        }
    }

    private static long maxDirectMemory0() {
        GenericDeclaration genericDeclaration;
        Class<?> clazz;
        long l = 0L;
        ClassLoader classLoader = null;
        try {
            classLoader = PlatformDependent.getSystemClassLoader();
            if (!SystemPropertyUtil.get("os.name", "").toLowerCase().contains("z/os")) {
                clazz = Class.forName("sun.misc.VM", true, classLoader);
                genericDeclaration = clazz.getDeclaredMethod("maxDirectMemory", new Class[0]);
                l = ((Number)((Method)genericDeclaration).invoke(null, new Object[0])).longValue();
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        if (l > 0L) {
            return l;
        }
        try {
            clazz = Class.forName("java.lang.management.ManagementFactory", true, classLoader);
            genericDeclaration = Class.forName("java.lang.management.RuntimeMXBean", true, classLoader);
            Object object = clazz.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke(null, new Object[0]);
            List list = (List)((Class)genericDeclaration).getDeclaredMethod("getInputArguments", new Class[0]).invoke(object, new Object[0]);
            for (int i = list.size() - 1; i >= 0; --i) {
                Matcher matcher = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher((CharSequence)list.get(i));
                if (!matcher.matches()) continue;
                l = Long.parseLong(matcher.group(1));
                switch (matcher.group(2).charAt(0)) {
                    case 'K': 
                    case 'k': {
                        l *= 1024L;
                        break;
                    }
                    case 'M': 
                    case 'm': {
                        l *= 0x100000L;
                        break;
                    }
                    case 'G': 
                    case 'g': {
                        l *= 0x40000000L;
                    }
                }
                break;
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        if (l <= 0L) {
            l = Runtime.getRuntime().maxMemory();
            logger.debug("maxDirectMemory: {} bytes (maybe)", (Object)l);
        } else {
            logger.debug("maxDirectMemory: {} bytes", (Object)l);
        }
        return l;
    }

    private static File tmpdir0() {
        File file;
        try {
            file = PlatformDependent.toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
            if (file != null) {
                logger.debug("-Dio.netty.tmpdir: {}", (Object)file);
                return file;
            }
            file = PlatformDependent.toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
            if (file != null) {
                logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", (Object)file);
                return file;
            }
            if (PlatformDependent.isWindows()) {
                file = PlatformDependent.toDirectory(System.getenv("TEMP"));
                if (file != null) {
                    logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", (Object)file);
                    return file;
                }
                String string = System.getenv("USERPROFILE");
                if (string != null) {
                    file = PlatformDependent.toDirectory(string + "\\AppData\\Local\\Temp");
                    if (file != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", (Object)file);
                        return file;
                    }
                    file = PlatformDependent.toDirectory(string + "\\Local Settings\\Temp");
                    if (file != null) {
                        logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", (Object)file);
                        return file;
                    }
                }
            } else {
                file = PlatformDependent.toDirectory(System.getenv("TMPDIR"));
                if (file != null) {
                    logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", (Object)file);
                    return file;
                }
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        file = PlatformDependent.isWindows() ? new File("C:\\Windows\\Temp") : new File("/tmp");
        logger.warn("Failed to get the temporary directory; falling back to: {}", (Object)file);
        return file;
    }

    private static File toDirectory(String string) {
        if (string == null) {
            return null;
        }
        File file = new File(string);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        try {
            return file.getAbsoluteFile();
        } catch (Exception exception) {
            return file;
        }
    }

    private static int bitMode0() {
        int n = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
        if (n > 0) {
            logger.debug("-Dio.netty.bitMode: {}", (Object)n);
            return n;
        }
        n = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
        if (n > 0) {
            logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", (Object)n);
            return n;
        }
        n = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
        if (n > 0) {
            logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", (Object)n);
            return n;
        }
        String string = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
        if ("amd64".equals(string) || "x86_64".equals(string)) {
            n = 64;
        } else if ("i386".equals(string) || "i486".equals(string) || "i586".equals(string) || "i686".equals(string)) {
            n = 32;
        }
        if (n > 0) {
            logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", (Object)n, (Object)string);
        }
        String string2 = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
        Pattern pattern = Pattern.compile("([1-9][0-9]+)-?bit");
        Matcher matcher = pattern.matcher(string2);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 1;
    }

    private static int addressSize0() {
        if (!PlatformDependent.hasUnsafe()) {
            return 1;
        }
        return PlatformDependent0.addressSize();
    }

    private static long byteArrayBaseOffset0() {
        if (!PlatformDependent.hasUnsafe()) {
            return -1L;
        }
        return PlatformDependent0.byteArrayBaseOffset();
    }

    private static boolean equalsSafe(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        int n4 = n + n3;
        while (n < n4) {
            if (byArray[n] != byArray2[n2]) {
                return true;
            }
            ++n;
            ++n2;
        }
        return false;
    }

    private static boolean isZeroSafe(byte[] byArray, int n, int n2) {
        int n3 = n + n2;
        while (n < n3) {
            if (byArray[n] != 0) {
                return true;
            }
            ++n;
        }
        return false;
    }

    static int hashCodeAsciiSafe(byte[] byArray, int n, int n2) {
        int n3 = -1028477387;
        int n4 = n2 & 7;
        int n5 = n + n4;
        for (int i = n - 8 + n2; i >= n5; i -= 8) {
            n3 = PlatformDependent0.hashCodeAsciiCompute(PlatformDependent.getLongSafe(byArray, i), n3);
        }
        switch (n4) {
            case 7: {
                return ((n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(byArray[n])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getShortSafe(byArray, n + 1))) * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getIntSafe(byArray, n + 3));
            }
            case 6: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getShortSafe(byArray, n))) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getIntSafe(byArray, n + 2));
            }
            case 5: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(byArray[n])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getIntSafe(byArray, n + 1));
            }
            case 4: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getIntSafe(byArray, n));
            }
            case 3: {
                return (n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(byArray[n])) * 461845907 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getShortSafe(byArray, n + 1));
            }
            case 2: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(PlatformDependent.getShortSafe(byArray, n));
            }
            case 1: {
                return n3 * -862048943 + PlatformDependent0.hashCodeAsciiSanitize(byArray[n]);
            }
        }
        return n3;
    }

    public static String normalizedArch() {
        return NORMALIZED_ARCH;
    }

    public static String normalizedOs() {
        return NORMALIZED_OS;
    }

    private static String normalize(String string) {
        return string.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }

    private static String normalizeArch(String string) {
        if ((string = PlatformDependent.normalize(string)).matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (string.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (string.matches("^(ia64|itanium64)$")) {
            return "itanium_64";
        }
        if (string.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (string.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (string.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(string)) {
            return "aarch_64";
        }
        if (string.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if ("ppc64".equals(string)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(string)) {
            return "ppcle_64";
        }
        if ("s390".equals(string)) {
            return "s390_32";
        }
        if ("s390x".equals(string)) {
            return "s390_64";
        }
        return "unknown";
    }

    private static String normalizeOs(String string) {
        if ((string = PlatformDependent.normalize(string)).startsWith("aix")) {
            return "aix";
        }
        if (string.startsWith("hpux")) {
            return "hpux";
        }
        if (string.startsWith("os400") && (string.length() <= 5 || !Character.isDigit(string.charAt(5)))) {
            return "os400";
        }
        if (string.startsWith("linux")) {
            return "linux";
        }
        if (string.startsWith("macosx") || string.startsWith("osx")) {
            return "osx";
        }
        if (string.startsWith("freebsd")) {
            return "freebsd";
        }
        if (string.startsWith("openbsd")) {
            return "openbsd";
        }
        if (string.startsWith("netbsd")) {
            return "netbsd";
        }
        if (string.startsWith("solaris") || string.startsWith("sunos")) {
            return "sunos";
        }
        if (string.startsWith("windows")) {
            return "windows";
        }
        return "unknown";
    }

    private PlatformDependent() {
    }

    static InternalLogger access$100() {
        return logger;
    }

    static {
        long l;
        $assertionsDisabled = !PlatformDependent.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
        MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
        IS_WINDOWS = PlatformDependent.isWindows0();
        IS_OSX = PlatformDependent.isOsx0();
        CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !PlatformDependent.isAndroid();
        UNSAFE_UNAVAILABILITY_CAUSE = PlatformDependent.unsafeUnavailabilityCause0();
        DIRECT_BUFFER_PREFERRED = UNSAFE_UNAVAILABILITY_CAUSE == null && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false);
        MAX_DIRECT_MEMORY = PlatformDependent.maxDirectMemory0();
        BYTE_ARRAY_BASE_OFFSET = PlatformDependent.byteArrayBaseOffset0();
        TMPDIR = PlatformDependent.tmpdir0();
        BIT_MODE = PlatformDependent.bitMode0();
        NORMALIZED_ARCH = PlatformDependent.normalizeArch(SystemPropertyUtil.get("os.arch", ""));
        NORMALIZED_OS = PlatformDependent.normalizeOs(SystemPropertyUtil.get("os.name", ""));
        ADDRESS_SIZE = PlatformDependent.addressSize0();
        BIG_ENDIAN_NATIVE_ORDER = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        NOOP = new Cleaner(){

            @Override
            public void freeDirectBuffer(ByteBuffer byteBuffer) {
            }
        };
        RANDOM_PROVIDER = PlatformDependent.javaVersion() >= 7 ? new ThreadLocalRandomProvider(){

            @Override
            public Random current() {
                return java.util.concurrent.ThreadLocalRandom.current();
            }
        } : new ThreadLocalRandomProvider(){

            @Override
            public Random current() {
                return ThreadLocalRandom.current();
            }
        };
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noPreferDirect: {}", (Object)(!DIRECT_BUFFER_PREFERRED ? 1 : 0));
        }
        if (!(PlatformDependent.hasUnsafe() || PlatformDependent.isAndroid() || PlatformDependent0.isExplicitNoUnsafe())) {
            logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system instability.");
        }
        if ((l = SystemPropertyUtil.getLong("io.netty.maxDirectMemory", -1L)) == 0L || !PlatformDependent.hasUnsafe() || !PlatformDependent0.hasDirectBufferNoCleanerConstructor()) {
            USE_DIRECT_BUFFER_NO_CLEANER = false;
            DIRECT_MEMORY_COUNTER = null;
        } else {
            USE_DIRECT_BUFFER_NO_CLEANER = true;
            DIRECT_MEMORY_COUNTER = l < 0L ? ((l = PlatformDependent.maxDirectMemory0()) <= 0L ? null : new AtomicLong()) : new AtomicLong();
        }
        DIRECT_MEMORY_LIMIT = l;
        logger.debug("-Dio.netty.maxDirectMemory: {} bytes", (Object)l);
        int n = SystemPropertyUtil.getInt("io.netty.uninitializedArrayAllocationThreshold", 1024);
        UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD = PlatformDependent.javaVersion() >= 9 && PlatformDependent0.hasAllocateArrayMethod() ? n : -1;
        logger.debug("-Dio.netty.uninitializedArrayAllocationThreshold: {}", (Object)UNINITIALIZED_ARRAY_ALLOCATION_THRESHOLD);
        MAYBE_SUPER_USER = PlatformDependent.maybeSuperUser0();
        CLEANER = !PlatformDependent.isAndroid() && PlatformDependent.hasUnsafe() ? (PlatformDependent.javaVersion() >= 9 ? (CleanerJava9.isSupported() ? new CleanerJava9() : NOOP) : (CleanerJava6.isSupported() ? new CleanerJava6() : NOOP)) : NOOP;
    }

    private static interface ThreadLocalRandomProvider {
        public Random current();
    }

    private static final class AtomicLongCounter
    extends AtomicLong
    implements LongCounter {
        private static final long serialVersionUID = 4074772784610639305L;

        private AtomicLongCounter() {
        }

        @Override
        public void add(long l) {
            this.addAndGet(l);
        }

        @Override
        public void increment() {
            this.incrementAndGet();
        }

        @Override
        public void decrement() {
            this.decrementAndGet();
        }

        @Override
        public long value() {
            return this.get();
        }

        AtomicLongCounter(1 var1_1) {
            this();
        }
    }

    private static final class Mpsc {
        private static final boolean USE_MPSC_CHUNKED_ARRAY_QUEUE;

        private Mpsc() {
        }

        static <T> Queue<T> newMpscQueue(int n) {
            int n2 = Math.max(Math.min(n, 0x40000000), 2048);
            return USE_MPSC_CHUNKED_ARRAY_QUEUE ? new MpscChunkedArrayQueue(1024, n2) : new MpscGrowableAtomicArrayQueue(1024, n2);
        }

        static <T> Queue<T> newMpscQueue() {
            return USE_MPSC_CHUNKED_ARRAY_QUEUE ? new MpscUnboundedArrayQueue(1024) : new MpscUnboundedAtomicArrayQueue(1024);
        }

        static {
            Object object = null;
            if (PlatformDependent.hasUnsafe()) {
                object = AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        return UnsafeAccess.UNSAFE;
                    }
                });
            }
            if (object == null) {
                PlatformDependent.access$100().debug("org.jctools-core.MpscChunkedArrayQueue: unavailable");
                USE_MPSC_CHUNKED_ARRAY_QUEUE = false;
            } else {
                PlatformDependent.access$100().debug("org.jctools-core.MpscChunkedArrayQueue: available");
                USE_MPSC_CHUNKED_ARRAY_QUEUE = true;
            }
        }
    }
}

