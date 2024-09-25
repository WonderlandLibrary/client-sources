/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.api.plugin.Plugin
 *  net.md_5.bungee.api.scheduler.ScheduledTask
 */
package us.myles.ViaVersion.bungee.platform;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import us.myles.ViaVersion.BungeePlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.platform.ViaPlatformLoader;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.bungee.handlers.BungeeServerHandler;
import us.myles.ViaVersion.bungee.listeners.ElytraPatch;
import us.myles.ViaVersion.bungee.listeners.UpdateListener;
import us.myles.ViaVersion.bungee.providers.BungeeBossBarProvider;
import us.myles.ViaVersion.bungee.providers.BungeeEntityIdProvider;
import us.myles.ViaVersion.bungee.providers.BungeeMainHandProvider;
import us.myles.ViaVersion.bungee.providers.BungeeMovementTransmitter;
import us.myles.ViaVersion.bungee.providers.BungeeVersionProvider;
import us.myles.ViaVersion.bungee.service.ProtocolDetectorService;
import us.myles.ViaVersion.protocols.base.VersionProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BossBarProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

public class BungeeViaLoader
implements ViaPlatformLoader {
    private final BungeePlugin plugin;
    private final Set<Listener> listeners = new HashSet<Listener>();
    private final Set<ScheduledTask> tasks = new HashSet<ScheduledTask>();

    public BungeeViaLoader(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    private void registerListener(Listener listener) {
        this.listeners.add(listener);
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this.plugin, listener);
    }

    @Override
    public void load() {
        this.registerListener(this.plugin);
        this.registerListener(new UpdateListener());
        this.registerListener(new BungeeServerHandler());
        if (ProtocolRegistry.SERVER_PROTOCOL < ProtocolVersion.v1_9.getVersion()) {
            this.registerListener(new ElytraPatch());
        }
        Via.getManager().getProviders().use(VersionProvider.class, new BungeeVersionProvider());
        Via.getManager().getProviders().use(EntityIdProvider.class, new BungeeEntityIdProvider());
        if (ProtocolRegistry.SERVER_PROTOCOL < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
            Via.getManager().getProviders().use(BossBarProvider.class, new BungeeBossBarProvider());
            Via.getManager().getProviders().use(MainHandProvider.class, new BungeeMainHandProvider());
        }
        if (this.plugin.getConf().getBungeePingInterval() > 0) {
            this.tasks.add(this.plugin.getProxy().getScheduler().schedule((Plugin)this.plugin, (Runnable)new ProtocolDetectorService(this.plugin), 0L, (long)this.plugin.getConf().getBungeePingInterval(), TimeUnit.SECONDS));
        }
    }

    @Override
    public void unload() {
        for (Listener listener : this.listeners) {
            ProxyServer.getInstance().getPluginManager().unregisterListener(listener);
        }
        this.listeners.clear();
        for (ScheduledTask task : this.tasks) {
            task.cancel();
        }
        this.tasks.clear();
    }
}

