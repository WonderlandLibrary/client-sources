/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import org.lwjgl.system.windows.WinBase;

public final class WindowsUtil {
    private WindowsUtil() {
    }

    public static void windowsThrowException(String string) {
        throw new RuntimeException(string + " (error code = " + WinBase.getLastError() + ")");
    }
}

