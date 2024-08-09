/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.command.ViaVersionCommand;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.ProtocolManager;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.api.scheduler.Scheduler;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.connection.ConnectionManagerImpl;
import com.viaversion.viaversion.debug.DebugHandlerImpl;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import com.viaversion.viaversion.protocol.ServerProtocolVersionRange;
import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.TabCompleteThread;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ViaIdleThread;
import com.viaversion.viaversion.scheduler.TaskScheduler;
import com.viaversion.viaversion.update.UpdateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ViaManagerImpl
implements ViaManager {
    private final ProtocolManagerImpl protocolManager = new ProtocolManagerImpl();
    private final ConnectionManager connectionManager = new ConnectionManagerImpl();
    private final DebugHandler debugHandler = new DebugHandlerImpl();
    private final ViaProviders providers = new ViaProviders();
    private final Scheduler scheduler = new TaskScheduler();
    private final ViaPlatform<?> platform;
    private final ViaInjector injector;
    private final ViaCommandHandler commandHandler;
    private final ViaPlatformLoader loader;
    private final Set<String> subPlatforms = new HashSet<String>();
    private List<Runnable> enableListeners = new ArrayList<Runnable>();
    private PlatformTask<?> mappingLoadingTask;
    private boolean initialized;

    public ViaManagerImpl(ViaPlatform<?> viaPlatform, ViaInjector viaInjector, ViaCommandHandler viaCommandHandler, ViaPlatformLoader viaPlatformLoader) {
        this.platform = viaPlatform;
        this.injector = viaInjector;
        this.commandHandler = viaCommandHandler;
        this.loader = viaPlatformLoader;
    }

    public static ViaManagerBuilder builder() {
        return new ViaManagerBuilder();
    }

    public void init() {
        if (System.getProperty("ViaVersion") != null) {
            this.platform.onReload();
        }
        if (!this.injector.lateProtocolVersionSetting()) {
            this.loadServerProtocol();
        }
        this.protocolManager.registerProtocols();
        try {
            this.injector.inject();
        } catch (Exception exception) {
            this.platform.getLogger().severe("ViaVersion failed to inject:");
            exception.printStackTrace();
            return;
        }
        System.setProperty("ViaVersion", this.platform.getPluginVersion());
        for (Runnable runnable : this.enableListeners) {
            runnable.run();
        }
        this.enableListeners = null;
        this.initialized = true;
    }

    public void onServerLoaded() {
        ServerProtocolVersion serverProtocolVersion;
        if (this.platform.getConf().isCheckForUpdates()) {
            UpdateUtil.sendUpdateMessage();
        }
        if (!this.protocolManager.getServerProtocolVersion().isKnown()) {
            this.loadServerProtocol();
        }
        if ((serverProtocolVersion = this.protocolManager.getServerProtocolVersion()).isKnown()) {
            if (this.platform.isProxy()) {
                this.platform.getLogger().info("ViaVersion detected lowest supported version by the proxy: " + ProtocolVersion.getProtocol(serverProtocolVersion.lowestSupportedVersion()));
                this.platform.getLogger().info("Highest supported version by the proxy: " + ProtocolVersion.getProtocol(serverProtocolVersion.highestSupportedVersion()));
                if (this.debugHandler.enabled()) {
                    this.platform.getLogger().info("Supported version range: " + Arrays.toString(serverProtocolVersion.supportedVersions().toArray(new int[0])));
                }
            } else {
                this.platform.getLogger().info("ViaVersion detected server version: " + ProtocolVersion.getProtocol(serverProtocolVersion.highestSupportedVersion()));
            }
            if (!this.protocolManager.isWorkingPipe()) {
                this.platform.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
                this.platform.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
                this.platform.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
                this.platform.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://jo0001.github.io/ViaSetup");
                this.platform.getLogger().warning("and if you're still unsure, feel free to join our Discord-Server for further assistance.");
            } else if (serverProtocolVersion.highestSupportedVersion() <= ProtocolVersion.v1_12_2.getVersion()) {
                this.platform.getLogger().warning("This version of Minecraft is extremely outdated and support for it has reached its end of life. You will still be able to run Via on this Minecraft version, but we are unlikely to provide any further fixes or help with problems specific to legacy Minecraft versions. Please consider updating to give your players a better experience and to avoid issues that have long been fixed.");
            }
        }
        this.checkJavaVersion();
        this.unsupportedSoftwareWarning();
        this.loader.load();
        this.mappingLoadingTask = Via.getPlatform().runRepeatingAsync(this::lambda$onServerLoaded$0, 10L);
        int n = this.protocolManager.getServerProtocolVersion().lowestSupportedVersion();
        if (n < ProtocolVersion.v1_9.getVersion() && Via.getConfig().isSimulatePlayerTick()) {
            Via.getPlatform().runRepeatingSync(new ViaIdleThread(), 1L);
        }
        if (n < ProtocolVersion.v1_13.getVersion() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
            Via.getPlatform().runRepeatingSync(new TabCompleteThread(), 1L);
        }
        this.protocolManager.refreshVersions();
    }

    private void loadServerProtocol() {
        try {
            ServerProtocolVersion serverProtocolVersion;
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(this.injector.getServerProtocolVersion());
            if (this.platform.isProxy()) {
                IntSortedSet intSortedSet = this.injector.getServerProtocolVersions();
                serverProtocolVersion = new ServerProtocolVersionRange(intSortedSet.firstInt(), intSortedSet.lastInt(), intSortedSet);
            } else {
                serverProtocolVersion = new ServerProtocolVersionSingleton(protocolVersion.getVersion());
            }
            this.protocolManager.setServerProtocol(serverProtocolVersion);
        } catch (Exception exception) {
            this.platform.getLogger().severe("ViaVersion failed to get the server protocol!");
            exception.printStackTrace();
        }
    }

    public void destroy() {
        this.platform.getLogger().info("ViaVersion is disabling, if this is a reload and you experience issues consider rebooting.");
        try {
            this.injector.uninject();
        } catch (Exception exception) {
            this.platform.getLogger().severe("ViaVersion failed to uninject:");
            exception.printStackTrace();
        }
        this.loader.unload();
        this.scheduler.shutdown();
    }

    private void checkJavaVersion() {
        int n;
        String string = System.getProperty("java.version");
        Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(string);
        if (!matcher.find()) {
            this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + string);
            return;
        }
        String string2 = matcher.group(1);
        try {
            n = Integer.parseInt(string2);
        } catch (NumberFormatException numberFormatException) {
            this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + string2);
            numberFormatException.printStackTrace();
            return;
        }
        if (n < 17) {
            this.platform.getLogger().warning("You are running an outdated Java version, please consider updating it to at least Java 17 (your version is " + string + "). At some point in the future, ViaVersion will no longer be compatible with this version of Java.");
        }
    }

    private void unsupportedSoftwareWarning() {
        boolean bl = false;
        for (UnsupportedSoftware unsupportedSoftware : this.platform.getUnsupportedSoftwareClasses()) {
            String string = unsupportedSoftware.match();
            if (string == null) continue;
            if (!bl) {
                this.platform.getLogger().severe("************************************************");
                this.platform.getLogger().severe("You are using unsupported software and may encounter unforeseeable issues.");
                this.platform.getLogger().severe("");
                bl = true;
            }
            this.platform.getLogger().severe("We strongly advise against using " + string + ":");
            this.platform.getLogger().severe(unsupportedSoftware.getReason());
            this.platform.getLogger().severe("");
        }
        if (bl) {
            this.platform.getLogger().severe("We will not provide support in case you encounter issues possibly related to this software.");
            this.platform.getLogger().severe("************************************************");
        }
    }

    @Override
    public ViaPlatform<?> getPlatform() {
        return this.platform;
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    @Override
    public ProtocolManager getProtocolManager() {
        return this.protocolManager;
    }

    @Override
    public ViaProviders getProviders() {
        return this.providers;
    }

    @Override
    public DebugHandler debugHandler() {
        return this.debugHandler;
    }

    @Override
    public ViaInjector getInjector() {
        return this.injector;
    }

    @Override
    public ViaCommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public ViaPlatformLoader getLoader() {
        return this.loader;
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public Set<String> getSubPlatforms() {
        return this.subPlatforms;
    }

    @Override
    public void addEnableListener(Runnable runnable) {
        this.enableListeners.add(runnable);
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public ViaVersionCommand getCommandHandler() {
        return this.getCommandHandler();
    }

    private void lambda$onServerLoaded$0() {
        if (this.protocolManager.checkForMappingCompletion() && this.mappingLoadingTask != null) {
            this.mappingLoadingTask.cancel();
            this.mappingLoadingTask = null;
        }
    }

    public static final class ViaManagerBuilder {
        private ViaPlatform<?> platform;
        private ViaInjector injector;
        private ViaCommandHandler commandHandler;
        private ViaPlatformLoader loader;

        public ViaManagerBuilder platform(ViaPlatform<?> viaPlatform) {
            this.platform = viaPlatform;
            return this;
        }

        public ViaManagerBuilder injector(ViaInjector viaInjector) {
            this.injector = viaInjector;
            return this;
        }

        public ViaManagerBuilder loader(ViaPlatformLoader viaPlatformLoader) {
            this.loader = viaPlatformLoader;
            return this;
        }

        public ViaManagerBuilder commandHandler(ViaCommandHandler viaCommandHandler) {
            this.commandHandler = viaCommandHandler;
            return this;
        }

        public ViaManagerImpl build() {
            return new ViaManagerImpl(this.platform, this.injector, this.commandHandler, this.loader);
        }
    }
}

