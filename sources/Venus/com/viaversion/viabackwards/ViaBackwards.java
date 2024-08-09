/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.ViaBackwardsConfig;
import com.viaversion.viabackwards.api.ViaBackwardsPlatform;

public final class ViaBackwards {
    private static ViaBackwardsPlatform platform;
    private static ViaBackwardsConfig config;

    public static void init(ViaBackwardsPlatform viaBackwardsPlatform, ViaBackwardsConfig viaBackwardsConfig) {
        Preconditions.checkArgument(viaBackwardsPlatform != null, "ViaBackwards is already initialized");
        platform = viaBackwardsPlatform;
        config = viaBackwardsConfig;
    }

    public static ViaBackwardsPlatform getPlatform() {
        return platform;
    }

    public static ViaBackwardsConfig getConfig() {
        return config;
    }
}

