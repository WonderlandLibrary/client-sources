/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.kyori.adventure.text.Component
 *  net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
 *  org.spongepowered.api.Game
 *  org.spongepowered.api.Platform$Component
 *  org.spongepowered.api.Server
 *  org.spongepowered.api.Sponge
 *  org.spongepowered.api.command.Command$Raw
 *  org.spongepowered.api.command.registrar.CommandRegistrar
 *  org.spongepowered.api.config.ConfigDir
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.entity.living.player.server.ServerPlayer
 *  org.spongepowered.api.event.Listener
 *  org.spongepowered.api.event.lifecycle.ConstructPluginEvent
 *  org.spongepowered.api.event.lifecycle.StartedEngineEvent
 *  org.spongepowered.api.event.lifecycle.StartingEngineEvent
 *  org.spongepowered.api.event.lifecycle.StoppingEngineEvent
 *  org.spongepowered.api.scheduler.Task
 *  org.spongepowered.api.util.Ticks
 *  org.spongepowered.plugin.PluginContainer
 *  org.spongepowered.plugin.builtin.jvm.Plugin
 *  org.spongepowered.plugin.metadata.PluginMetadata
 *  org.spongepowered.plugin.metadata.model.PluginContributor
 */
package com.viaversion.viaversion;

import com.google.inject.Inject;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.sponge.commands.SpongeCommandHandler;
import com.viaversion.viaversion.sponge.commands.SpongePlayer;
import com.viaversion.viaversion.sponge.platform.SpongeViaAPI;
import com.viaversion.viaversion.sponge.platform.SpongeViaConfig;
import com.viaversion.viaversion.sponge.platform.SpongeViaInjector;
import com.viaversion.viaversion.sponge.platform.SpongeViaLoader;
import com.viaversion.viaversion.sponge.platform.SpongeViaTask;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.spongepowered.api.Game;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.registrar.CommandRegistrar;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Ticks;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;
import org.spongepowered.plugin.metadata.PluginMetadata;
import org.spongepowered.plugin.metadata.model.PluginContributor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(value="viaversion")
public class SpongePlugin
implements ViaPlatform<Player> {
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder().extractUrls().build();
    private final SpongeViaAPI api = new SpongeViaAPI();
    private final PluginContainer container;
    private final Game game;
    private final Logger logger;
    private SpongeViaConfig conf;
    @Inject
    @ConfigDir(sharedRoot=false)
    private Path configDir;

    @Inject
    SpongePlugin(PluginContainer pluginContainer, Game game, org.apache.logging.log4j.Logger logger) {
        this.container = pluginContainer;
        this.game = game;
        this.logger = new LoggerWrapper(logger);
    }

    @Listener
    public void constructPlugin(ConstructPluginEvent constructPluginEvent) {
        this.conf = new SpongeViaConfig(this.configDir.toFile());
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler(new SpongeCommandHandler()).injector(new SpongeViaInjector()).loader(new SpongeViaLoader(this)).build());
    }

    @Listener
    public void onServerStart(StartingEngineEvent<Server> startingEngineEvent) {
        ((CommandRegistrar)Sponge.server().commandManager().registrar(Command.Raw.class).get()).register(this.container, (Object)((Command.Raw)Via.getManager().getCommandHandler()), "viaversion", new String[]{"viaver", "vvsponge"});
        ViaManagerImpl viaManagerImpl = (ViaManagerImpl)Via.getManager();
        viaManagerImpl.init();
    }

    @Listener
    public void onServerStarted(StartedEngineEvent<Server> startedEngineEvent) {
        ViaManagerImpl viaManagerImpl = (ViaManagerImpl)Via.getManager();
        viaManagerImpl.onServerLoaded();
    }

    @Listener
    public void onServerStop(StoppingEngineEvent<Server> stoppingEngineEvent) {
        ((ViaManagerImpl)Via.getManager()).destroy();
    }

    @Override
    public String getPlatformName() {
        return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().name().orElse("unknown");
    }

    @Override
    public String getPlatformVersion() {
        return this.game.platform().container(Platform.Component.IMPLEMENTATION).metadata().version().toString();
    }

    @Override
    public String getPluginVersion() {
        return this.container.metadata().version().toString();
    }

    @Override
    public PlatformTask runAsync(Runnable runnable) {
        Task task = Task.builder().plugin(this.container).execute(runnable).build();
        return new SpongeViaTask(this.game.asyncScheduler().submit(task));
    }

    @Override
    public PlatformTask runRepeatingAsync(Runnable runnable, long l) {
        Task task = Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of((long)l)).build();
        return new SpongeViaTask(this.game.asyncScheduler().submit(task));
    }

    @Override
    public PlatformTask runSync(Runnable runnable) {
        Task task = Task.builder().plugin(this.container).execute(runnable).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override
    public PlatformTask runSync(Runnable runnable, long l) {
        Task task = Task.builder().plugin(this.container).execute(runnable).delay(Ticks.of((long)l)).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override
    public PlatformTask runRepeatingSync(Runnable runnable, long l) {
        Task task = Task.builder().plugin(this.container).execute(runnable).interval(Ticks.of((long)l)).build();
        return new SpongeViaTask(this.game.server().scheduler().submit(task));
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        Collection collection = this.game.server().onlinePlayers();
        ViaCommandSender[] viaCommandSenderArray = new ViaCommandSender[collection.size()];
        int n = 0;
        for (ServerPlayer serverPlayer : collection) {
            viaCommandSenderArray[n++] = new SpongePlayer(serverPlayer);
        }
        return viaCommandSenderArray;
    }

    @Override
    public void sendMessage(UUID uUID, String string) {
        this.game.server().player(uUID).ifPresent(arg_0 -> SpongePlugin.lambda$sendMessage$0(string, arg_0));
    }

    @Override
    public boolean kickPlayer(UUID uUID, String string) {
        return this.game.server().player(uUID).map(arg_0 -> SpongePlugin.lambda$kickPlayer$1(string, arg_0)).orElse(false);
    }

    @Override
    public boolean isPluginEnabled() {
        return false;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }

    @Override
    public File getDataFolder() {
        return this.configDir.toFile();
    }

    @Override
    public void onReload() {
        this.logger.severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }

    @Override
    public JsonObject getDump() {
        JsonObject jsonObject = new JsonObject();
        ArrayList<PluginInfo> arrayList = new ArrayList<PluginInfo>();
        for (PluginContainer pluginContainer : this.game.pluginManager().plugins()) {
            PluginMetadata pluginMetadata = pluginContainer.metadata();
            arrayList.add(new PluginInfo(true, pluginMetadata.name().orElse("Unknown"), pluginMetadata.version().toString(), pluginContainer.instance().getClass().getCanonicalName(), pluginMetadata.contributors().stream().map(PluginContributor::name).collect(Collectors.toList())));
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(arrayList));
        return jsonObject;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return false;
    }

    @Override
    public boolean hasPlugin(String string) {
        return this.game.pluginManager().plugin(string).isPresent();
    }

    public SpongeViaAPI getApi() {
        return this.api;
    }

    @Override
    public SpongeViaConfig getConf() {
        return this.conf;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    public PluginContainer container() {
        return this.container;
    }

    @Override
    public ViaVersionConfig getConf() {
        return this.getConf();
    }

    @Override
    public ViaAPI getApi() {
        return this.getApi();
    }

    private static Boolean lambda$kickPlayer$1(String string, ServerPlayer serverPlayer) {
        serverPlayer.kick((Component)LegacyComponentSerializer.legacySection().deserialize(string));
        return true;
    }

    private static void lambda$sendMessage$0(String string, ServerPlayer serverPlayer) {
        serverPlayer.sendMessage((Component)LEGACY_SERIALIZER.deserialize(string));
    }
}

