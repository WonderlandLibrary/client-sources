/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import java.io.File;
import java.util.logging.Logger;
import via.ViaLoadingBase;

public class ViaBackwardsPlatformImpl
implements ViaBackwardsPlatform {
    private final File directory;

    public ViaBackwardsPlatformImpl(File file) {
        this.directory = file;
        this.init(this.directory);
    }

    @Override
    public Logger getLogger() {
        return ViaLoadingBase.LOGGER;
    }

    @Override
    public boolean isOutdated() {
        return true;
    }

    @Override
    public void disable() {
    }

    @Override
    public File getDataFolder() {
        return this.directory;
    }
}

