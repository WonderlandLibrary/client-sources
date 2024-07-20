/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.platform.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import dev.intave.vialoadingbase.ViaLoadingBase;
import dev.intave.vialoadingbase.platform.providers.VLBMovementTransmitterProvider;
import dev.intave.vialoadingbase.provider.VLBBaseVersionProvider;

public class VLBViaProviders
implements ViaPlatformLoader {
    @Override
    public void load() {
        ViaProviders providers = Via.getManager().getProviders();
        providers.use(VersionProvider.class, new VLBBaseVersionProvider());
        providers.use(MovementTransmitterProvider.class, new VLBMovementTransmitterProvider());
        if (ViaLoadingBase.getInstance().getProviders() != null) {
            ViaLoadingBase.getInstance().getProviders().accept(providers);
        }
    }

    @Override
    public void unload() {
    }
}

