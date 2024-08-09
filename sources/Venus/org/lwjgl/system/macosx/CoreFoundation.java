/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class CoreFoundation {
    public static final byte TRUE = 1;
    public static final byte FALSE = 0;
    public static final int kCFStringEncodingMacRoman = 0;
    public static final int kCFStringEncodingWindowsLatin1 = 1280;
    public static final int kCFStringEncodingISOLatin1 = 513;
    public static final int kCFStringEncodingNextStepLatin = 2817;
    public static final int kCFStringEncodingASCII = 1536;
    public static final int kCFStringEncodingUnicode = 256;
    public static final int kCFStringEncodingUTF8 = 0x8000100;
    public static final int kCFStringEncodingNonLossyASCII = 3071;
    public static final int kCFStringEncodingUTF16 = 256;
    public static final int kCFStringEncodingUTF16BE = 0x10000100;
    public static final int kCFStringEncodingUTF16LE = 0x14000100;
    public static final int kCFStringEncodingUTF32 = 0xC000100;
    public static final int kCFStringEncodingUTF32BE = 0x18000100;
    public static final int kCFStringEncodingUTF32LE = 0x1C000100;
    public static final int kCFURLPOSIXPathStyle = 0;
    public static final int kCFURLHFSPathStyle = 1;
    public static final int kCFURLWindowsPathStyle = 2;
    public static final long kCFAllocatorDefault;
    public static final long kCFAllocatorSystemDefault;
    public static final long kCFAllocatorMalloc;
    public static final long kCFAllocatorMallocZone;
    public static final long kCFAllocatorNull;
    public static final long kCFAllocatorUseContext;

    protected CoreFoundation() {
        throw new UnsupportedOperationException();
    }

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorDefault();

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorSystemDefault();

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorMalloc();

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorMallocZone();

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorNull();

    @NativeType(value="CFAllocatorRef")
    private static native long kCFAllocatorUseContext();

    public static native long nCFRetain(long var0);

    @NativeType(value="CFTypeRef")
    public static long CFRetain(@NativeType(value="CFTypeRef") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return CoreFoundation.nCFRetain(l);
    }

    public static native void nCFRelease(long var0);

    public static void CFRelease(@NativeType(value="CFTypeRef") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        CoreFoundation.nCFRelease(l);
    }

    public static native long nCFBundleCreate(long var0, long var2);

    @NativeType(value="CFBundleRef")
    public static long CFBundleCreate(@NativeType(value="CFAllocatorRef") long l, @NativeType(value="CFURLRef") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return CoreFoundation.nCFBundleCreate(l, l2);
    }

    public static native long nCFBundleGetBundleWithIdentifier(long var0);

    @NativeType(value="CFBundleRef")
    public static long CFBundleGetBundleWithIdentifier(@NativeType(value="CFStringRef") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return CoreFoundation.nCFBundleGetBundleWithIdentifier(l);
    }

    public static native long nCFBundleGetFunctionPointerForName(long var0, long var2);

    @NativeType(value="void *")
    public static long CFBundleGetFunctionPointerForName(@NativeType(value="CFBundleRef") long l, @NativeType(value="CFStringRef") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(l2);
        }
        return CoreFoundation.nCFBundleGetFunctionPointerForName(l, l2);
    }

    public static native long nCFStringCreateWithCString(long var0, long var2, int var4);

    @NativeType(value="CFStringRef")
    public static long CFStringCreateWithCString(@NativeType(value="CFAllocatorRef") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="CFStringEncoding") int n) {
        return CoreFoundation.nCFStringCreateWithCString(l, MemoryUtil.memAddress(byteBuffer), n);
    }

    public static native long nCFStringCreateWithCStringNoCopy(long var0, long var2, int var4, long var5);

    @NativeType(value="CFStringRef")
    public static long CFStringCreateWithCStringNoCopy(@NativeType(value="CFAllocatorRef") long l, @NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="CFStringEncoding") int n, @NativeType(value="CFAllocatorRef") long l2) {
        return CoreFoundation.nCFStringCreateWithCStringNoCopy(l, MemoryUtil.memAddress(byteBuffer), n, l2);
    }

    public static native long nCFURLCreateWithFileSystemPath(long var0, long var2, long var4, boolean var6);

    @NativeType(value="CFURLRef")
    public static long CFURLCreateWithFileSystemPath(@NativeType(value="CFAllocatorRef") long l, @NativeType(value="CFStringRef") long l2, @NativeType(value="CFURLPathStyle") long l3, @NativeType(value="Boolean") boolean bl) {
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        return CoreFoundation.nCFURLCreateWithFileSystemPath(l, l2, l3, bl);
    }

    static {
        Library.initialize();
        kCFAllocatorDefault = CoreFoundation.kCFAllocatorDefault();
        kCFAllocatorSystemDefault = CoreFoundation.kCFAllocatorSystemDefault();
        kCFAllocatorMalloc = CoreFoundation.kCFAllocatorMalloc();
        kCFAllocatorMallocZone = CoreFoundation.kCFAllocatorMallocZone();
        kCFAllocatorNull = CoreFoundation.kCFAllocatorNull();
        kCFAllocatorUseContext = CoreFoundation.kCFAllocatorUseContext();
    }
}

