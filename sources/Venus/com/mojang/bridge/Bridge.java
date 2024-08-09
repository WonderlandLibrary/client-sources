/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.bridge;

import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.LauncherProvider;
import java.util.ServiceLoader;

public class Bridge {
    private static boolean INITIALIZED;
    private static Launcher LAUNCHER;

    private Bridge() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Launcher getLauncher() {
        if (INITIALIZED) return LAUNCHER;
        Class<Bridge> clazz = Bridge.class;
        synchronized (Bridge.class) {
            if (INITIALIZED) return LAUNCHER;
            LAUNCHER = Bridge.createLauncher();
            INITIALIZED = true;
            // ** MonitorExit[var0] (shouldn't be in output)
            return LAUNCHER;
        }
    }

    private static Launcher createLauncher() {
        for (LauncherProvider launcherProvider : ServiceLoader.load(LauncherProvider.class)) {
            Launcher launcher = launcherProvider.createLauncher();
            if (launcher == null) continue;
            return launcher;
        }
        return null;
    }
}

