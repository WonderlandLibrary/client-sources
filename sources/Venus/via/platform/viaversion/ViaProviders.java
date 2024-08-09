/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import via.ViaLoadingBase;
import via.platform.providers.ViaMovementTransmitterProvider;
import via.provider.ViaBaseVersionProvider;

public class ViaProviders
implements ViaPlatformLoader {
    @Override
    public void load() {
        com.viaversion.viaversion.api.platform.providers.ViaProviders viaProviders = Via.getManager().getProviders();
        viaProviders.use(VersionProvider.class, new ViaBaseVersionProvider());
        viaProviders.use(MovementTransmitterProvider.class, new ViaMovementTransmitterProvider());
        if (ViaLoadingBase.getInstance().getProviders() != null) {
            ViaLoadingBase.getInstance().getProviders().accept(viaProviders);
        }
    }

    @Override
    public void unload() {
    }
}

