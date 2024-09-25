/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaInjector;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.api.platform.ViaPlatformLoader;
import us.myles.ViaVersion.api.platform.providers.ViaProviders;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.commands.ViaCommandHandler;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.TabCompleteThread;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ViaIdleThread;
import us.myles.ViaVersion.update.UpdateUtil;

public class ViaManager {
    private final ViaPlatform<?> platform;
    private final ViaProviders providers = new ViaProviders();
    private final ViaInjector injector;
    private final ViaCommandHandler commandHandler;
    private final ViaPlatformLoader loader;
    private final Set<String> subPlatforms = new HashSet<String>();
    private List<Runnable> enableListeners = new ArrayList<Runnable>();
    private TaskId mappingLoadingTask;
    private boolean debug;

    public ViaManager(ViaPlatform<?> platform, ViaInjector injector, ViaCommandHandler commandHandler, ViaPlatformLoader loader) {
        this.platform = platform;
        this.injector = injector;
        this.commandHandler = commandHandler;
        this.loader = loader;
    }

    public static ViaManagerBuilder builder() {
        return new ViaManagerBuilder();
    }

    public void init() {
        if (System.getProperty("ViaVersion") != null) {
            this.platform.onReload();
        }
        if (this.platform.getConf().isCheckForUpdates()) {
            UpdateUtil.sendUpdateMessage();
        }
        ProtocolRegistry.init();
        try {
            this.injector.inject();
        }
        catch (Exception e) {
            this.platform.getLogger().severe("ViaVersion failed to inject:");
            e.printStackTrace();
            return;
        }
        System.setProperty("ViaVersion", this.platform.getPluginVersion());
        for (Runnable listener : this.enableListeners) {
            listener.run();
        }
        this.enableListeners = null;
        this.platform.runSync(this::onServerLoaded);
    }

    public void onServerLoaded() {
        try {
            ProtocolRegistry.SERVER_PROTOCOL = ProtocolVersion.getProtocol(this.injector.getServerProtocolVersion()).getVersion();
        }
        catch (Exception e) {
            this.platform.getLogger().severe("ViaVersion failed to get the server protocol!");
            e.printStackTrace();
        }
        if (ProtocolRegistry.SERVER_PROTOCOL != -1) {
            this.platform.getLogger().info("ViaVersion detected server version: " + ProtocolVersion.getProtocol(ProtocolRegistry.SERVER_PROTOCOL));
            if (!ProtocolRegistry.isWorkingPipe() && !this.platform.isProxy()) {
                this.platform.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
                this.platform.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
                this.platform.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
                this.platform.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://jo0001.github.io/ViaSetup");
                this.platform.getLogger().warning("and if you're still unsure, feel free to join our Discord-Server for further assistance.");
            } else if (ProtocolRegistry.SERVER_PROTOCOL == ProtocolVersion.v1_8.getVersion() && !this.platform.isProxy()) {
                this.platform.getLogger().warning("This version of Minecraft is over half a decade old and support for it will be fully dropped eventually. Please upgrade to a newer version to avoid encountering bugs and stability issues that have long been fixed.");
            }
        }
        ProtocolRegistry.onServerLoaded();
        this.loader.load();
        this.mappingLoadingTask = Via.getPlatform().runRepeatingSync(() -> {
            if (ProtocolRegistry.checkForMappingCompletion()) {
                this.platform.cancelTask(this.mappingLoadingTask);
                this.mappingLoadingTask = null;
            }
        }, 10L);
        if (ProtocolRegistry.SERVER_PROTOCOL < ProtocolVersion.v1_9.getVersion() && Via.getConfig().isSimulatePlayerTick()) {
            Via.getPlatform().runRepeatingSync(new ViaIdleThread(), 1L);
        }
        if (ProtocolRegistry.SERVER_PROTOCOL < ProtocolVersion.v1_13.getVersion() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
            Via.getPlatform().runRepeatingSync(new TabCompleteThread(), 1L);
        }
        ProtocolRegistry.refreshVersions();
    }

    public void destroy() {
        this.platform.getLogger().info("ViaVersion is disabling, if this is a reload and you experience issues consider rebooting.");
        try {
            this.injector.uninject();
        }
        catch (Exception e) {
            this.platform.getLogger().severe("ViaVersion failed to uninject:");
            e.printStackTrace();
        }
        this.loader.unload();
    }

    public Set<UserConnection> getConnections() {
        return this.platform.getConnectionManager().getConnections();
    }

    @Deprecated
    public Map<UUID, UserConnection> getPortedPlayers() {
        return this.getConnectedClients();
    }

    public Map<UUID, UserConnection> getConnectedClients() {
        return this.platform.getConnectionManager().getConnectedClients();
    }

    public UUID getConnectedClientId(UserConnection conn) {
        return this.platform.getConnectionManager().getConnectedClientId(conn);
    }

    public boolean isClientConnected(UUID player) {
        return this.platform.getConnectionManager().isClientConnected(player);
    }

    public void handleLoginSuccess(UserConnection info) {
        this.platform.getConnectionManager().onLoginSuccess(info);
    }

    public ViaPlatform<?> getPlatform() {
        return this.platform;
    }

    public ViaProviders getProviders() {
        return this.providers;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public ViaInjector getInjector() {
        return this.injector;
    }

    public ViaCommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    public ViaPlatformLoader getLoader() {
        return this.loader;
    }

    public Set<String> getSubPlatforms() {
        return this.subPlatforms;
    }

    @Nullable
    public UserConnection getConnection(UUID playerUUID) {
        return this.platform.getConnectionManager().getConnectedClient(playerUUID);
    }

    public void addEnableListener(Runnable runnable) {
        this.enableListeners.add(runnable);
    }

    public static final class ViaManagerBuilder {
        private ViaPlatform<?> platform;
        private ViaInjector injector;
        private ViaCommandHandler commandHandler;
        private ViaPlatformLoader loader;

        public ViaManagerBuilder platform(ViaPlatform<?> platform) {
            this.platform = platform;
            return this;
        }

        public ViaManagerBuilder injector(ViaInjector injector) {
            this.injector = injector;
            return this;
        }

        public ViaManagerBuilder loader(ViaPlatformLoader loader) {
            this.loader = loader;
            return this;
        }

        public ViaManagerBuilder commandHandler(ViaCommandHandler commandHandler) {
            this.commandHandler = commandHandler;
            return this;
        }

        public ViaManager build() {
            return new ViaManager(this.platform, this.injector, this.commandHandler, this.loader);
        }
    }
}

