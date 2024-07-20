/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.platform;

import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import dev.intave.vialoadingbase.ViaLoadingBase;
import java.io.File;
import java.util.logging.Logger;

public class ViaRewindPlatformImpl
implements ViaRewindPlatform {
    public ViaRewindPlatformImpl(File directory) {
        ViaRewindConfig config = new ViaRewindConfig(new File(directory, "viarewind.yml"));
        config.reloadConfig();
        this.init(config);
    }

    @Override
    public Logger getLogger() {
        return ViaLoadingBase.LOGGER;
    }
}

