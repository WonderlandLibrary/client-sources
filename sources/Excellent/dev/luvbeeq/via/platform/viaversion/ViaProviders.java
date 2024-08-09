package dev.luvbeeq.via.platform.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import dev.luvbeeq.via.ViaLoadingBase;
import dev.luvbeeq.via.platform.providers.ViaMovementTransmitterProvider;
import dev.luvbeeq.via.provider.ViaBaseVersionProvider;

public class ViaProviders implements ViaPlatformLoader {

    @Override
    public void load() {
        final com.viaversion.viaversion.api.platform.providers.ViaProviders providers = Via.getManager().getProviders();
        providers.use(VersionProvider.class, new ViaBaseVersionProvider());
        providers.use(MovementTransmitterProvider.class, new ViaMovementTransmitterProvider());

        if (ViaLoadingBase.getInstance().getProviders() != null)
            ViaLoadingBase.getInstance().getProviders().accept(providers);
    }

    @Override
    public void unload() {
    }
}
