/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.plugin.PluginContainer
 */
package us.myles.ViaVersion.velocity.platform;

import com.velocitypowered.api.plugin.PluginContainer;
import us.myles.ViaVersion.VelocityPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.platform.ViaPlatformLoader;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.protocols.base.VersionProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import us.myles.ViaVersion.velocity.listeners.UpdateListener;
import us.myles.ViaVersion.velocity.platform.VelocityViaConfig;
import us.myles.ViaVersion.velocity.providers.VelocityBossBarProvider;
import us.myles.ViaVersion.velocity.providers.VelocityMovementTransmitter;
import us.myles.ViaVersion.velocity.providers.VelocityVersionProvider;
import us.myles.ViaVersion.velocity.service.ProtocolDetectorService;

public class VelocityViaLoader
implements ViaPlatformLoader {
    @Override
    public void load() {
        Object plugin = VelocityPlugin.PROXY.getPluginManager().getPlugin("viaversion").flatMap(PluginContainer::getInstance).get();
        if (ProtocolRegistry.SERVER_PROTOCOL < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new VelocityMovementTransmitter());
            Via.getManager().getProviders().use(BossBarProvider.class, new VelocityBossBarProvider());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new VelocityVersionProvider());
        VelocityPlugin.PROXY.getEventManager().register(plugin, (Object)new UpdateListener());
        int pingInterval = ((VelocityViaConfig)Via.getPlatform().getConf()).getVelocityPingInterval();
        if (pingInterval > 0) {
            Via.getPlatform().runRepeatingSync(new ProtocolDetectorService(), (long)pingInterval * 20L);
        }
    }

    @Override
    public void unload() {
    }
}

