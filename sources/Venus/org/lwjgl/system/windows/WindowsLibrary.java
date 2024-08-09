/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.windows.WinBase;
import org.lwjgl.system.windows.WindowsUtil;

public class WindowsLibrary
extends SharedLibrary.Default {
    public static final long HINSTANCE;

    public WindowsLibrary(String string) {
        this(string, WindowsLibrary.loadLibrary(string));
    }

    public WindowsLibrary(String string, long l) {
        super(string, l);
    }

    private static long loadLibrary(String string) {
        long l;
        MemoryStack memoryStack = MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            l = WinBase.LoadLibrary(memoryStack.UTF16(string));
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (memoryStack != null) {
                WindowsLibrary.$closeResource(throwable, memoryStack);
            }
        }
        if (l == 0L) {
            throw new UnsatisfiedLinkError("Failed to load library: " + string + " (error code = " + WinBase.getLastError() + ")");
        }
        return l;
    }

    @Override
    public long getFunctionAddress(ByteBuffer byteBuffer) {
        return WinBase.GetProcAddress(this.address(), byteBuffer);
    }

    @Override
    public void free() {
        if (!WinBase.FreeLibrary(this.address())) {
            WindowsUtil.windowsThrowException("Failed to unload library: " + this.getName());
        }
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

    static {
        MemoryStack memoryStack = MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            HINSTANCE = WinBase.GetModuleHandle(memoryStack.UTF16(Library.JNI_LIBRARY_NAME));
            if (HINSTANCE == 0L) {
                throw new RuntimeException("Failed to retrieve LWJGL module handle.");
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (memoryStack != null) {
                WindowsLibrary.$closeResource(throwable, memoryStack);
            }
        }
    }
}

