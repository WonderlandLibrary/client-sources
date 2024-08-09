/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.platform;

import com.sun.jna.Platform;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.platform.win32.User32;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

public class KeyboardUtils {
    static final NativeKeyboardUtils INSTANCE;

    public static boolean isPressed(int n, int n2) {
        return INSTANCE.isPressed(n, n2);
    }

    public static boolean isPressed(int n) {
        return INSTANCE.isPressed(n);
    }

    static {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("KeyboardUtils requires a keyboard");
        }
        if (Platform.isWindows()) {
            INSTANCE = new W32KeyboardUtils(null);
        } else {
            if (Platform.isMac()) {
                INSTANCE = new MacKeyboardUtils(null);
                throw new UnsupportedOperationException("No support (yet) for " + System.getProperty("os.name"));
            }
            INSTANCE = new X11KeyboardUtils(null);
        }
    }

    static class 1 {
    }

    private static class X11KeyboardUtils
    extends NativeKeyboardUtils {
        private X11KeyboardUtils() {
            super(null);
        }

        private int toKeySym(int n, int n2) {
            if (n >= 65 && n <= 90) {
                return 97 + (n - 65);
            }
            if (n >= 48 && n <= 57) {
                return 48 + (n - 48);
            }
            if (n == 16) {
                if ((n2 & 3) != 0) {
                    return 0;
                }
                return 0;
            }
            if (n == 17) {
                if ((n2 & 3) != 0) {
                    return 1;
                }
                return 0;
            }
            if (n == 18) {
                if ((n2 & 3) != 0) {
                    return 1;
                }
                return 0;
            }
            if (n == 157) {
                if ((n2 & 3) != 0) {
                    return 1;
                }
                return 0;
            }
            return 1;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean isPressed(int n, int n2) {
            X11 x11 = X11.INSTANCE;
            X11.Display display = x11.XOpenDisplay(null);
            if (display == null) {
                throw new Error("Can't open X Display");
            }
            try {
                byte[] byArray = new byte[32];
                x11.XQueryKeymap(display, byArray);
                int n3 = this.toKeySym(n, n2);
                for (int i = 5; i < 256; ++i) {
                    int n4;
                    int n5 = i / 8;
                    int n6 = i % 8;
                    if ((byArray[n5] & 1 << n6) == 0 || (n4 = x11.XKeycodeToKeysym(display, (byte)i, 0).intValue()) != n3) continue;
                    boolean bl = true;
                    return bl;
                }
            } finally {
                x11.XCloseDisplay(display);
            }
            return true;
        }

        X11KeyboardUtils(1 var1_1) {
            this();
        }
    }

    private static class MacKeyboardUtils
    extends NativeKeyboardUtils {
        private MacKeyboardUtils() {
            super(null);
        }

        public boolean isPressed(int n, int n2) {
            return true;
        }

        MacKeyboardUtils(1 var1_1) {
            this();
        }
    }

    private static class W32KeyboardUtils
    extends NativeKeyboardUtils {
        private W32KeyboardUtils() {
            super(null);
        }

        private int toNative(int n, int n2) {
            if (n >= 65 && n <= 90 || n >= 48 && n <= 57) {
                return n;
            }
            if (n == 16) {
                if ((n2 & 3) != 0) {
                    return 0;
                }
                if ((n2 & 2) != 0) {
                    return 1;
                }
                return 1;
            }
            if (n == 17) {
                if ((n2 & 3) != 0) {
                    return 0;
                }
                if ((n2 & 2) != 0) {
                    return 1;
                }
                return 0;
            }
            if (n == 18) {
                if ((n2 & 3) != 0) {
                    return 0;
                }
                if ((n2 & 2) != 0) {
                    return 1;
                }
                return 1;
            }
            return 1;
        }

        public boolean isPressed(int n, int n2) {
            User32 user32 = User32.INSTANCE;
            return (user32.GetAsyncKeyState(this.toNative(n, n2)) & 0x8000) != 0;
        }

        W32KeyboardUtils(1 var1_1) {
            this();
        }
    }

    private static abstract class NativeKeyboardUtils {
        private NativeKeyboardUtils() {
        }

        public abstract boolean isPressed(int var1, int var2);

        public boolean isPressed(int n) {
            return this.isPressed(n, 1);
        }

        NativeKeyboardUtils(1 var1_1) {
            this();
        }
    }
}

