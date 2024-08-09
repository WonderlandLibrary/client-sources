/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.plugin.PluginContainer
 */
package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.plugin.PluginContainer;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import com.viaversion.viaversion.velocity.listeners.UpdateListener;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.providers.VelocityBossBarProvider;
import com.viaversion.viaversion.velocity.providers.VelocityVersionProvider;

public class VelocityViaLoader
implements ViaPlatformLoader {
    @Override
    public void load() {
        Object u = VelocityPlugin.PROXY.getPluginManager().getPlugin("viaversion").flatMap(PluginContainer::getInstance).get();
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(BossBarProvider.class, new VelocityBossBarProvider());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new VelocityVersionProvider());
        VelocityPlugin.PROXY.getEventManager().register(u, (Object)new UpdateListener());
        int n = ((VelocityViaConfig)Via.getPlatform().getConf()).getVelocityPingInterval();
        if (n > 0) {
            Via.getPlatform().runRepeatingAsync(VelocityViaLoader::lambda$load$0, (long)n * 20L);
        }
    }

    @Override
    public void unload() {
    }

    private static void lambda$load$0() {
        Via.proxyPlatform().protocolDetectorService().probeAllServers();
    }
}

