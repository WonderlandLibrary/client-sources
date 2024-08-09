/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.lwjgl.system.macosx.LibC;
import org.lwjgl.system.macosx.MacOSXLibrary;
import org.lwjgl.system.macosx.ObjCRuntime;

final class EventLoop {
    private EventLoop() {
    }

    private static boolean isMainThread() {
        if (!Configuration.GLFW_CHECK_THREAD0.get(true).booleanValue()) {
            return false;
        }
        long l = ObjCRuntime.getLibrary().getFunctionAddress("objc_msgSend");
        long l2 = ObjCRuntime.objc_getClass("NSThread");
        long l3 = JNI.invokePPP(l2, ObjCRuntime.sel_getUid("currentThread"), l);
        return JNI.invokePPZ(l3, ObjCRuntime.sel_getUid("isMainThread"), l);
    }

    private static boolean isJavaStartedOnFirstThread() {
        return "1".equals(System.getenv().get("JAVA_STARTED_ON_FIRST_THREAD_" + LibC.getpid()));
    }

    static boolean access$000() {
        return EventLoop.isMainThread();
    }

    static boolean access$100() {
        return EventLoop.isJavaStartedOnFirstThread();
    }

    static final class OnScreen {
        private OnScreen() {
        }

        static void check() {
        }

        static {
            if (Platform.get() == Platform.MACOSX && !EventLoop.access$000()) {
                throw new IllegalStateException("Please run the JVM with -XstartOnFirstThread and make sure a window toolkit other than GLFW (e.g. AWT or JavaFX) is not initialized.");
            }
        }
    }

    static final class OffScreen {
        private OffScreen() {
        }

        static void check() {
        }

        static {
            if (Platform.get() == Platform.MACOSX && !EventLoop.access$000()) {
                MacOSXLibrary macOSXLibrary = MacOSXLibrary.getWithIdentifier("com.apple.AppKit");
                try {
                    long l = macOSXLibrary.getFunctionAddress("NSApp");
                    if (MemoryUtil.memGetAddress(l) == 0L) {
                        throw new IllegalStateException(EventLoop.access$100() ? "GLFW windows may only be created on the main thread." : "GLFW windows may only be created on the main thread and that thread must be the first thread in the process. Please run the JVM with -XstartOnFirstThread. For offscreen rendering, make sure another window toolkit (e.g. AWT or JavaFX) is initialized before GLFW.");
                    }
                    APIUtil.apiLog("GLFW can only be used for offscreen rendering.");
                } finally {
                    macOSXLibrary.free();
                }
            }
        }
    }
}

