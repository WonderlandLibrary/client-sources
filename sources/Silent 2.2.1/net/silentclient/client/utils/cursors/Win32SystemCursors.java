package net.silentclient.client.utils.cursors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class Win32SystemCursors {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Long[] CACHE = new Long[SystemCursors.SIZE];
    private static MethodHandle getHwndMethod;

    static {
        if (Util.foundInputImplementationMethod()) {
            try {
                Class<?> windowsDisplay = Class.forName("org.lwjgl.opengl.WindowsDisplay");
                Method getHwnd = windowsDisplay.getDeclaredMethod("getHwnd");
                getHwnd.setAccessible(true);
                getHwndMethod = MethodHandles.lookup().unreflect(getHwnd);

                Util.loadLibrary("lwjglLegacyCursorsWin32");
            } catch (Throwable error) {
                LOGGER.error("Could not perform reflection/load natives", error);
                SystemCursors.markUnsupported();
            }
        } else
            SystemCursors.markUnsupported();
    }

    public static void setCursor(byte cursor) {
        nSetCursor(getHwnd(), getDefaultCursorHandle(cursor));
    }

    private static long getDefaultCursorHandle(byte cursor) {
        if (CACHE[cursor] != null)
            return CACHE[cursor];

        return CACHE[cursor] = nGetDefaultCursorHandle(cursor);
    }

    private static long getHwnd() {
        try {
            return (long) getHwndMethod.invoke(Util.getDisplayImplementation());
        } catch (Throwable error) {
            throw Util.sneakyThrow(error);
        }
    }

    private static native long nGetDefaultCursorHandle(byte cursor);

    private static native void nSetCursor(long hwnd, long cursor);
}
