/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind;

import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;

public class ViaRewind {
    private static ViaRewindPlatform platform;
    private static ViaRewindConfig config;

    public static void init(ViaRewindPlatform platform, ViaRewindConfig config) {
        ViaRewind.platform = platform;
        ViaRewind.config = config;
    }

    public static ViaRewindPlatform getPlatform() {
        return platform;
    }

    public static ViaRewindConfig getConfig() {
        return config;
    }
}

