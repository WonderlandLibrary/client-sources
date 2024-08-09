/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.macosx.CoreFoundation;
import org.lwjgl.system.macosx.MacOSXLibrary;

public class MacOSXLibraryBundle
extends MacOSXLibrary {
    public MacOSXLibraryBundle(String string, long l) {
        super(string, l);
    }

    /*
     * Loose catch block
     */
    public static MacOSXLibraryBundle getWithIdentifier(String string) {
        long l = 0L;
        try {
            MemoryStack memoryStack = MemoryStack.stackPush();
            Throwable throwable = null;
            try {
                l = MacOSXLibraryBundle.CString2CFString(memoryStack.ASCII(string), 1536);
                long l2 = CoreFoundation.CFBundleGetBundleWithIdentifier(l);
                if (l2 == 0L) {
                    throw new UnsatisfiedLinkError("Failed to retrieve bundle with identifier: " + string);
                }
                CoreFoundation.CFRetain(l2);
                MacOSXLibraryBundle macOSXLibraryBundle = new MacOSXLibraryBundle(string, l2);
                return macOSXLibraryBundle;
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (memoryStack != null) {
                    MacOSXLibraryBundle.$closeResource(throwable, memoryStack);
                }
            }
            {
                catch (Throwable throwable3) {
                    throw throwable3;
                }
            }
        } finally {
            if (l != 0L) {
                CoreFoundation.CFRelease(l);
            }
        }
    }

    /*
     * Loose catch block
     */
    public static MacOSXLibraryBundle create(String string) {
        long l = 0L;
        long l2 = 0L;
        try {
            MemoryStack memoryStack = MemoryStack.stackPush();
            Throwable throwable = null;
            try {
                l = MacOSXLibraryBundle.CString2CFString(memoryStack.UTF8(string), 0x8000100);
                l2 = Checks.check(CoreFoundation.CFURLCreateWithFileSystemPath(0L, l, 0L, true));
                long l3 = CoreFoundation.CFBundleCreate(0L, l2);
                if (l3 == 0L) {
                    throw new UnsatisfiedLinkError("Failed to create bundle: " + string);
                }
                MacOSXLibraryBundle macOSXLibraryBundle = new MacOSXLibraryBundle(string, l3);
                return macOSXLibraryBundle;
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (memoryStack != null) {
                    MacOSXLibraryBundle.$closeResource(throwable, memoryStack);
                }
            }
            {
                catch (Throwable throwable3) {
                    throw throwable3;
                }
            }
        } finally {
            if (l2 != 0L) {
                CoreFoundation.CFRelease(l2);
            }
            if (l != 0L) {
                CoreFoundation.CFRelease(l);
            }
        }
    }

    @Override
    @Nullable
    public String getPath() {
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getFunctionAddress(ByteBuffer byteBuffer) {
        long l = MacOSXLibraryBundle.CString2CFString(byteBuffer, 1536);
        try {
            long l2 = CoreFoundation.CFBundleGetFunctionPointerForName(this.address(), l);
            return l2;
        } finally {
            CoreFoundation.CFRelease(l);
        }
    }

    private static long CString2CFString(ByteBuffer byteBuffer, int n) {
        return Checks.check(CoreFoundation.CFStringCreateWithCStringNoCopy(0L, byteBuffer, n, CoreFoundation.kCFAllocatorNull));
    }

    @Override
    public void free() {
        CoreFoundation.CFRelease(this.address());
    }

    private static void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            } catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }
}

