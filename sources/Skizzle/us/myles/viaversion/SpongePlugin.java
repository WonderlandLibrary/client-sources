/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.inject.Inject
 *  org.spongepowered.api.Game
 *  org.spongepowered.api.command.CommandCallable
 *  org.spongepowered.api.command.CommandSource
 *  org.spongepowered.api.config.DefaultConfig
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.event.Listener
 *  org.spongepowered.api.event.game.state.GameAboutToStartServerEvent
 *  org.spongepowered.api.event.game.state.GameInitializationEvent
 *  org.spongepowered.api.event.game.state.GameStoppingServerEvent
 *  org.spongepowered.api.plugin.Plugin
 *  org.spongepowered.api.plugin.PluginContainer
 *  org.spongepowered.api.scheduler.Task
 *  org.spongepowered.api.text.serializer.TextSerializers
 */
package us.myles.ViaVersion;

import com.google.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.serializer.TextSerializers;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.dump.PluginInfo;
import us.myles.ViaVersion.sponge.commands.SpongeCommandHandler;
import us.myles.ViaVersion.sponge.commands.SpongeCommandSender;
import us.myles.ViaVersion.sponge.platform.SpongeTaskId;
import us.myles.ViaVersion.sponge.platform.SpongeViaAPI;
import us.myles.ViaVersion.sponge.platform.SpongeViaConfig;
import us.myles.ViaVersion.sponge.platform.SpongeViaInjector;
import us.myles.ViaVersion.sponge.platform.SpongeViaLoader;
import us.myles.ViaVersion.sponge.util.LoggerWrapper;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;
import us.myles.viaversion.libs.gson.JsonObject;

@Plugin(id="viaversion", name="ViaVersion", version="3.3.0-20w45a", authors={"_MylesC", "creeper123123321", "Gerrygames", "KennyTV", "Matsv"}, description="Allow newer Minecraft versions to connect to an older server version.")
public class SpongePlugin
implements ViaPlatform<Player> {
    @Inject
    private Game game;
    @Inject
    private PluginContainer container;
    @Inject
    @DefaultConfig(sharedRoot=false)
    private File spongeConfig;
    private final ViaConnectionManager connectionManager = new ViaConnectionManager();
    private final SpongeViaAPI api = new SpongeViaAPI();
    private SpongeViaConfig conf;
    private Logger logger;

    @Listener
    public void onGameStart(GameInitializationEvent event) {
        this.logger = new LoggerWrapper(this.container.getLogger());
        this.conf = new SpongeViaConfig(this.container, this.spongeConfig.getParentFile());
        SpongeCommandHandler commandHandler = new SpongeCommandHandler();
        this.game.getCommandManager().register((Object)this, (CommandCallable)commandHandler, new String[]{"viaversion", "viaver", "vvsponge"});
        this.logger.info("ViaVersion " + this.getPluginVersion() + " is now loaded!");
        Via.init(ViaManager.builder().platform(this).commandHandler(commandHandler).injector(new SpongeViaInjector()).loader(new SpongeViaLoader(this)).build());
    }

    @Listener
    public void onServerStart(GameAboutToStartServerEvent event) {
        if (this.game.getPluginManager().getPlugin("viabackwards").isPresent()) {
            MappingDataLoader.enableMappingsCache();
        }
        this.logger.info("ViaVersion is injecting!");
        Via.getManager().init();
    }

    @Listener
    public void onServerStop(GameStoppingServerEvent event) {
        Via.getManager().destroy();
    }

    @Override
    public String getPlatformName() {
        return this.game.getPlatform().getImplementation().getName();
    }

    @Override
    public String getPlatformVersion() {
        return this.game.getPlatform().getImplementation().getVersion().orElse("Unknown Version");
    }

    @Override
    public String getPluginVersion() {
        return this.container.getVersion().orElse("Unknown Version");
    }

    @Override
    public TaskId runAsync(Runnable runnable) {
        return new SpongeTaskId(Task.builder().execute(runnable).async().submit((Object)this));
    }

    @Override
    public TaskId runSync(Runnable runnable) {
        return new SpongeTaskId(Task.builder().execute(runnable).submit((Object)this));
    }

    @Override
    public TaskId runSync(Runnable runnable, Long ticks) {
        return new SpongeTaskId(Task.builder().execute(runnable).delayTicks(ticks.longValue()).submit((Object)this));
    }

    @Override
    public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
        return new SpongeTaskId(Task.builder().execute(runnable).intervalTicks(ticks.longValue()).submit((Object)this));
    }

    @Override
    public void cancelTask(TaskId taskId) {
        if (taskId == null) {
            return;
        }
        if (taskId.getObject() == null) {
            return;
        }
        if (taskId instanceof SpongeTaskId) {
            ((SpongeTaskId)taskId).getObject().cancel();
        }
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        ViaCommandSender[] array = new ViaCommandSender[this.game.getServer().getOnlinePlayers().size()];
        int i = 0;
        for (Player player : this.game.getServer().getOnlinePlayers()) {
            array[i++] = new SpongeCommandSender((CommandSource)player);
        }
        return array;
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        this.game.getServer().getPlayer(uuid).ifPresent(player -> player.sendMessage(TextSerializers.JSON.deserialize(ComponentSerializer.toString(TextComponent.fromLegacyText(message)))));
    }

    @Override
    public boolean kickPlayer(UUID uuid, String message) {
        return this.game.getServer().getPlayer(uuid).map(player -> {
            player.kick(TextSerializers.formattingCode((char)'\u00a7').deserialize(message));
            return true;
        }).orElse(false);
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }

    @Override
    public File getDataFolder() {
        return this.spongeConfig.getParentFile();
    }

    @Override
    public void onReload() {
        this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
    }

    @Override
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (PluginContainer p : this.game.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, p.getName(), p.getVersion().orElse("Unknown Version"), p.getInstance().isPresent() ? p.getInstance().get().getClass().getCanonicalName() : "Unknown", p.getAuthors()));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        return platformSpecific;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override
    public ViaConnectionManager getConnectionManager() {
        return this.connectionManager;
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
}

