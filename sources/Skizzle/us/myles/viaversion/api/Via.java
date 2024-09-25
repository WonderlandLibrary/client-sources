/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.ViaVersion.api;

import com.google.common.base.Preconditions;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.platform.ViaPlatform;

public class Via {
    private static ViaPlatform platform;
    private static ViaManager manager;

    public static void init(ViaManager viaManager) {
        Preconditions.checkArgument((manager == null ? 1 : 0) != 0, (Object)"ViaManager is already set");
        platform = viaManager.getPlatform();
        manager = viaManager;
    }

    public static ViaAPI getAPI() {
        Preconditions.checkArgument((platform != null ? 1 : 0) != 0, (Object)"ViaVersion has not loaded the Platform");
        return platform.getApi();
    }

    public static ViaVersionConfig getConfig() {
        Preconditions.checkArgument((platform != null ? 1 : 0) != 0, (Object)"ViaVersion has not loaded the Platform");
        return platform.getConf();
    }

    public static ViaPlatform getPlatform() {
        return platform;
    }

    public static ViaManager getManager() {
        return manager;
    }
}

