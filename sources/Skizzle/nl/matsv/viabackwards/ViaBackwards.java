/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package nl.matsv.viabackwards;

import com.google.common.base.Preconditions;
import nl.matsv.viabackwards.api.ViaBackwardsConfig;
import nl.matsv.viabackwards.api.ViaBackwardsPlatform;

public class ViaBackwards {
    private static ViaBackwardsPlatform platform;
    private static ViaBackwardsConfig config;

    public static void init(ViaBackwardsPlatform platform, ViaBackwardsConfig config) {
        Preconditions.checkArgument((platform != null ? 1 : 0) != 0, (Object)"ViaBackwards is already initialized");
        ViaBackwards.platform = platform;
        ViaBackwards.config = config;
    }

    public static ViaBackwardsPlatform getPlatform() {
        return platform;
    }

    public static ViaBackwardsConfig getConfig() {
        return config;
    }
}

