/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Platform;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.jemalloc.MallocMessageCallbackI;

public class JEmalloc {
    public static final int JEMALLOC_VERSION_MAJOR = 5;
    public static final int JEMALLOC_VERSION_MINOR = 2;
    public static final int JEMALLOC_VERSION_BUGFIX = 0;
    public static final int JEMALLOC_VERSION_NREV = 0;
    public static final String JEMALLOC_VERSION_GID = "b0b3e49a54ec29e32636f4577d9d5a896d67fd20";
    public static final String JEMALLOC_VERSION = "5.2.0-0-gb0b3e49a54ec29e32636f4577d9d5a896d67fd20";
    public static final int MALLOCX_ZERO = 64;
    public static final int MALLOCX_TCACHE_NONE = JEmalloc.MALLOCX_TCACHE(-1);
    public static final int MALLCTL_ARENAS_ALL = 4096;
    public static final int MALLCTL_ARENAS_DESTROYED = 4097;
    private static final SharedLibrary JEMALLOC = Library.loadNative(JEmalloc.class, Configuration.JEMALLOC_LIBRARY_NAME.get(Platform.mapLibraryNameBundled("jemalloc")), true);

    protected JEmalloc() {
        throw new UnsupportedOperationException();
    }

    public static SharedLibrary getLibrary() {
        return JEMALLOC;
    }

    @NativeType(value="void (*) (void *, char const *) *")
    public static PointerBuffer je_malloc_message() {
        long l = Functions.malloc_message;
        return MemoryUtil.memPointerBuffer(l, 1);
    }

    public static long nje_malloc(long l) {
        long l2 = Functions.malloc;
        return JNI.invokePP(l, l2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_malloc(@NativeType(value="size_t") long l) {
        long l2 = JEmalloc.nje_malloc(l);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static long nje_calloc(long l, long l2) {
        long l3 = Functions.calloc;
        return JNI.invokePPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_calloc(@NativeType(value="size_t") long l, @NativeType(value="size_t") long l2) {
        long l3 = JEmalloc.nje_calloc(l, l2);
        return MemoryUtil.memByteBufferSafe(l3, (int)l * (int)l2);
    }

    public static int nje_posix_memalign(long l, long l2, long l3) {
        long l4 = Functions.posix_memalign;
        return JNI.invokePPPI(l, l2, l3, l4);
    }

    public static int je_posix_memalign(@NativeType(value="void **") PointerBuffer pointerBuffer, @NativeType(value="size_t") long l, @NativeType(value="size_t") long l2) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return JEmalloc.nje_posix_memalign(MemoryUtil.memAddress(pointerBuffer), l, l2);
    }

    public static long nje_aligned_alloc(long l, long l2) {
        long l3 = Functions.aligned_alloc;
        return JNI.invokePPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_aligned_alloc(@NativeType(value="size_t") long l, @NativeType(value="size_t") long l2) {
        long l3 = JEmalloc.nje_aligned_alloc(l, l2);
        return MemoryUtil.memByteBufferSafe(l3, (int)l2);
    }

    public static long nje_realloc(long l, long l2) {
        long l3 = Functions.realloc;
        return JNI.invokePPP(l, l2, l3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_realloc(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l) {
        long l2 = JEmalloc.nje_realloc(MemoryUtil.memAddressSafe(byteBuffer), l);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static void nje_free(long l) {
        long l2 = Functions.free;
        JNI.invokePV(l, l2);
    }

    public static void je_free(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") ShortBuffer shortBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") IntBuffer intBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") LongBuffer longBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(longBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") FloatBuffer floatBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static void je_free(@Nullable @NativeType(value="void *") PointerBuffer pointerBuffer) {
        JEmalloc.nje_free(MemoryUtil.memAddressSafe(pointerBuffer));
    }

    public static long nje_mallocx(long l, int n) {
        long l2 = Functions.mallocx;
        return JNI.invokePP(l, n, l2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_mallocx(@NativeType(value="size_t") long l, int n) {
        long l2 = JEmalloc.nje_mallocx(l, n);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static long nje_rallocx(long l, long l2, int n) {
        long l3 = Functions.rallocx;
        return JNI.invokePPP(l, l2, n, l3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_rallocx(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l, int n) {
        long l2 = JEmalloc.nje_rallocx(MemoryUtil.memAddressSafe(byteBuffer), l, n);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static long nje_xallocx(long l, long l2, long l3, int n) {
        long l4 = Functions.xallocx;
        return JNI.invokePPPP(l, l2, l3, n, l4);
    }

    @NativeType(value="size_t")
    public static long je_xallocx(@Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @NativeType(value="size_t") long l, @NativeType(value="size_t") long l2, int n) {
        return JEmalloc.nje_xallocx(MemoryUtil.memAddressSafe(byteBuffer), l, l2, n);
    }

    public static long nje_sallocx(long l, int n) {
        long l2 = Functions.sallocx;
        return JNI.invokePP(l, n, l2);
    }

    @NativeType(value="size_t")
    public static long je_sallocx(@NativeType(value="void const *") ByteBuffer byteBuffer, int n) {
        return JEmalloc.nje_sallocx(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static void nje_dallocx(long l, int n) {
        long l2 = Functions.dallocx;
        JNI.invokePV(l, n, l2);
    }

    public static void je_dallocx(@NativeType(value="void *") ByteBuffer byteBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") ShortBuffer shortBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(shortBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") IntBuffer intBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(intBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") LongBuffer longBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(longBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") FloatBuffer floatBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(floatBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") DoubleBuffer doubleBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(doubleBuffer), n);
    }

    public static void je_dallocx(@NativeType(value="void *") PointerBuffer pointerBuffer, int n) {
        JEmalloc.nje_dallocx(MemoryUtil.memAddress(pointerBuffer), n);
    }

    public static void nje_sdallocx(long l, long l2, int n) {
        long l3 = Functions.sdallocx;
        JNI.invokePPV(l, l2, n, l3);
    }

    public static void je_sdallocx(@NativeType(value="void *") ByteBuffer byteBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), n);
    }

    public static void je_sdallocx(@NativeType(value="void *") ShortBuffer shortBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(shortBuffer), Integer.toUnsignedLong(shortBuffer.remaining()) << 1, n);
    }

    public static void je_sdallocx(@NativeType(value="void *") IntBuffer intBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(intBuffer), Integer.toUnsignedLong(intBuffer.remaining()) << 2, n);
    }

    public static void je_sdallocx(@NativeType(value="void *") LongBuffer longBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(longBuffer), Integer.toUnsignedLong(longBuffer.remaining()) << 3, n);
    }

    public static void je_sdallocx(@NativeType(value="void *") FloatBuffer floatBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(floatBuffer), Integer.toUnsignedLong(floatBuffer.remaining()) << 2, n);
    }

    public static void je_sdallocx(@NativeType(value="void *") DoubleBuffer doubleBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(doubleBuffer), Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, n);
    }

    public static void je_sdallocx(@NativeType(value="void *") PointerBuffer pointerBuffer, int n) {
        JEmalloc.nje_sdallocx(MemoryUtil.memAddress(pointerBuffer), Integer.toUnsignedLong(pointerBuffer.remaining()) << Pointer.POINTER_SHIFT, n);
    }

    public static long nje_nallocx(long l, int n) {
        long l2 = Functions.nallocx;
        return JNI.invokePP(l, n, l2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer je_nallocx(@NativeType(value="size_t") long l, int n) {
        long l2 = JEmalloc.nje_nallocx(l, n);
        return MemoryUtil.memByteBufferSafe(l2, (int)l);
    }

    public static int nje_mallctl(long l, long l2, long l3, long l4, long l5) {
        long l6 = Functions.mallctl;
        return JNI.invokePPPPPI(l, l2, l3, l4, l5, l6);
    }

    public static int je_mallctl(@NativeType(value="char const *") ByteBuffer byteBuffer, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer2, @Nullable @NativeType(value="size_t *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.checkSafe(pointerBuffer, 1);
        }
        return JEmalloc.nje_mallctl(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddressSafe(byteBuffer2), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(byteBuffer3), Checks.remainingSafe(byteBuffer3));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int je_mallctl(@NativeType(value="char const *") CharSequence charSequence, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @Nullable @NativeType(value="size_t *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(pointerBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = JEmalloc.nje_mallctl(l, MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(pointerBuffer), MemoryUtil.memAddressSafe(byteBuffer2), Checks.remainingSafe(byteBuffer2));
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static int nje_mallctlnametomib(long l, long l2, long l3) {
        long l4 = Functions.mallctlnametomib;
        return JNI.invokePPPI(l, l2, l3, l4);
    }

    public static int je_mallctlnametomib(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="size_t *") PointerBuffer pointerBuffer, @NativeType(value="size_t *") PointerBuffer pointerBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
            Checks.check(pointerBuffer2, 1);
            Checks.check(pointerBuffer, pointerBuffer2.get(pointerBuffer2.position()));
        }
        return JEmalloc.nje_mallctlnametomib(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(pointerBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int je_mallctlnametomib(@NativeType(value="char const *") CharSequence charSequence, @NativeType(value="size_t *") PointerBuffer pointerBuffer, @NativeType(value="size_t *") PointerBuffer pointerBuffer2) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer2, 1);
            Checks.check(pointerBuffer, pointerBuffer2.get(pointerBuffer2.position()));
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCII(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n2 = JEmalloc.nje_mallctlnametomib(l, MemoryUtil.memAddress(pointerBuffer), MemoryUtil.memAddress(pointerBuffer2));
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static int nje_mallctlbymib(long l, long l2, long l3, long l4, long l5, long l6) {
        long l7 = Functions.mallctlbymib;
        return JNI.invokePPPPPPI(l, l2, l3, l4, l5, l6, l7);
    }

    public static int je_mallctlbymib(@NativeType(value="size_t const *") PointerBuffer pointerBuffer, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer, @Nullable @NativeType(value="size_t *") PointerBuffer pointerBuffer2, @Nullable @NativeType(value="void *") ByteBuffer byteBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(pointerBuffer2, 1);
        }
        return JEmalloc.nje_mallctlbymib(MemoryUtil.memAddress(pointerBuffer), pointerBuffer.remaining(), MemoryUtil.memAddressSafe(byteBuffer), MemoryUtil.memAddressSafe(pointerBuffer2), MemoryUtil.memAddressSafe(byteBuffer2), Checks.remainingSafe(byteBuffer2));
    }

    public static void nje_malloc_stats_print(long l, long l2, long l3) {
        long l4 = Functions.malloc_stats_print;
        JNI.invokePPPV(l, l2, l3, l4);
    }

    public static void je_malloc_stats_print(@Nullable @NativeType(value="void (*) (void *, char const *)") MallocMessageCallbackI mallocMessageCallbackI, @NativeType(value="void *") long l, @Nullable @NativeType(value="char const *") ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1Safe(byteBuffer);
        }
        JEmalloc.nje_malloc_stats_print(MemoryUtil.memAddressSafe(mallocMessageCallbackI), l, MemoryUtil.memAddressSafe(byteBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void je_malloc_stats_print(@Nullable @NativeType(value="void (*) (void *, char const *)") MallocMessageCallbackI mallocMessageCallbackI, @NativeType(value="void *") long l, @Nullable @NativeType(value="char const *") CharSequence charSequence) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            memoryStack.nASCIISafe(charSequence, false);
            long l2 = charSequence == null ? 0L : memoryStack.getPointerAddress();
            JEmalloc.nje_malloc_stats_print(MemoryUtil.memAddressSafe(mallocMessageCallbackI), l, l2);
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static long nje_malloc_usable_size(long l) {
        long l2 = Functions.malloc_usable_size;
        return JNI.invokePP(l, l2);
    }

    @NativeType(value="size_t")
    public static long je_malloc_usable_size(@NativeType(value="void const *") ByteBuffer byteBuffer) {
        return JEmalloc.nje_malloc_usable_size(MemoryUtil.memAddress(byteBuffer));
    }

    public static int MALLOCX_LG_ALIGN(int n) {
        return n;
    }

    public static int MALLOCX_ALIGN(int n) {
        return Integer.numberOfTrailingZeros(n);
    }

    public static int MALLOCX_TCACHE(int n) {
        return n + 2 << 8;
    }

    public static int MALLOCX_ARENA(int n) {
        return n + 1 << 20;
    }

    static SharedLibrary access$000() {
        return JEMALLOC;
    }

    static {
        if (Platform.get() == Platform.WINDOWS) {
            JEmalloc.nje_free(JEmalloc.nje_malloc(8L));
        }
    }

    public static final class Functions {
        public static final long malloc_message = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_malloc_message");
        public static final long malloc = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_malloc");
        public static final long calloc = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_calloc");
        public static final long posix_memalign = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_posix_memalign");
        public static final long aligned_alloc = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_aligned_alloc");
        public static final long realloc = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_realloc");
        public static final long free = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_free");
        public static final long mallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_mallocx");
        public static final long rallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_rallocx");
        public static final long xallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_xallocx");
        public static final long sallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_sallocx");
        public static final long dallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_dallocx");
        public static final long sdallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_sdallocx");
        public static final long nallocx = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_nallocx");
        public static final long mallctl = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_mallctl");
        public static final long mallctlnametomib = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_mallctlnametomib");
        public static final long mallctlbymib = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_mallctlbymib");
        public static final long malloc_stats_print = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_malloc_stats_print");
        public static final long malloc_usable_size = APIUtil.apiGetFunctionAddress(JEmalloc.access$000(), "je_malloc_usable_size");

        private Functions() {
        }
    }
}

