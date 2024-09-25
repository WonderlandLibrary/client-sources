/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.inject.Inject
 *  com.velocitypowered.api.command.Command
 *  com.velocitypowered.api.event.PostOrder
 *  com.velocitypowered.api.event.Subscribe
 *  com.velocitypowered.api.event.proxy.ProxyInitializeEvent
 *  com.velocitypowered.api.plugin.Plugin
 *  com.velocitypowered.api.plugin.PluginContainer
 *  com.velocitypowered.api.plugin.annotation.DataDirectory
 *  com.velocitypowered.api.proxy.Player
 *  com.velocitypowered.api.proxy.ProxyServer
 *  net.kyori.text.serializer.gson.GsonComponentSerializer
 *  org.slf4j.Logger
 */
package us.myles.ViaVersion;

import com.google.inject.Inject;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.kyori.text.serializer.gson.GsonComponentSerializer;
import org.slf4j.Logger;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.dump.PluginInfo;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.ViaVersion.velocity.command.VelocityCommandHandler;
import us.myles.ViaVersion.velocity.command.VelocityCommandSender;
import us.myles.ViaVersion.velocity.platform.VelocityTaskId;
import us.myles.ViaVersion.velocity.platform.VelocityViaAPI;
import us.myles.ViaVersion.velocity.platform.VelocityViaConfig;
import us.myles.ViaVersion.velocity.platform.VelocityViaInjector;
import us.myles.ViaVersion.velocity.platform.VelocityViaLoader;
import us.myles.ViaVersion.velocity.service.ProtocolDetectorService;
import us.myles.ViaVersion.velocity.util.LoggerWrapper;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;
import us.myles.viaversion.libs.gson.JsonObject;

@Plugin(id="viaversion", name="ViaVersion", version="3.3.0-20w45a", authors={"_MylesC", "creeper123123321", "Gerrygames", "KennyTV", "Matsv"}, description="Allow newer Minecraft versions to connect to an older server version.", url="https://viaversion.com")
public class VelocityPlugin
implements ViaPlatform<Player> {
    public static ProxyServer PROXY;
    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger loggerslf4j;
    @Inject
    @DataDirectory
    private Path configDir;
    private VelocityViaAPI api;
    private java.util.logging.Logger logger;
    private VelocityViaConfig conf;
    private ViaConnectionManager connectionManager;

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent e) {
        PROXY = this.proxy;
        VelocityCommandHandler commandHandler = new VelocityCommandHandler();
        PROXY.getCommandManager().register((Command)commandHandler, new String[]{"viaver", "vvvelocity", "viaversion"});
        this.api = new VelocityViaAPI();
        this.conf = new VelocityViaConfig(this.configDir.toFile());
        this.logger = new LoggerWrapper(this.loggerslf4j);
        this.connectionManager = new ViaConnectionManager();
        Via.init(ViaManager.builder().platform(this).commandHandler(commandHandler).loader(new VelocityViaLoader()).injector(new VelocityViaInjector()).build());
        if (this.proxy.getPluginManager().getPlugin("viabackwards").isPresent()) {
            MappingDataLoader.enableMappingsCache();
        }
    }

    @Subscribe(order=PostOrder.LAST)
    public void onProxyLateInit(ProxyInitializeEvent e) {
        Via.getManager().init();
    }

    @Override
    public String getPlatformName() {
        String proxyImpl = ProxyServer.class.getPackage().getImplementationTitle();
        return proxyImpl != null ? proxyImpl : "Velocity";
    }

    @Override
    public String getPlatformVersion() {
        String version = ProxyServer.class.getPackage().getImplementationVersion();
        return version != null ? version : "Unknown";
    }

    @Override
    public boolean isProxy() {
        return true;
    }

    @Override
    public String getPluginVersion() {
        return "3.3.0-20w45a";
    }

    @Override
    public TaskId runAsync(Runnable runnable) {
        return this.runSync(runnable);
    }

    @Override
    public TaskId runSync(Runnable runnable) {
        return this.runSync(runnable, 0L);
    }

    @Override
    public TaskId runSync(Runnable runnable, Long ticks) {
        return new VelocityTaskId(PROXY.getScheduler().buildTask((Object)this, runnable).delay(ticks * 50L, TimeUnit.MILLISECONDS).schedule());
    }

    @Override
    public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
        return new VelocityTaskId(PROXY.getScheduler().buildTask((Object)this, runnable).repeat(ticks * 50L, TimeUnit.MILLISECONDS).schedule());
    }

    @Override
    public void cancelTask(TaskId taskId) {
        if (taskId instanceof VelocityTaskId) {
            ((VelocityTaskId)taskId).getObject().cancel();
        }
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return (ViaCommandSender[])PROXY.getAllPlayers().stream().map(VelocityCommandSender::new).toArray(ViaCommandSender[]::new);
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        PROXY.getPlayer(uuid).ifPresent(it -> it.sendMessage(GsonComponentSerializer.INSTANCE.deserialize(ComponentSerializer.toString(TextComponent.fromLegacyText(message)))));
    }

    @Override
    public boolean kickPlayer(UUID uuid, String message) {
        return PROXY.getPlayer(uuid).map(it -> {
            it.disconnect(GsonComponentSerializer.INSTANCE.deserialize(ComponentSerializer.toString(TextComponent.fromLegacyText(message))));
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
        return this.configDir.toFile();
    }

    public VelocityViaAPI getApi() {
        return this.api;
    }

    @Override
    public VelocityViaConfig getConf() {
        return this.conf;
    }

    @Override
    public void onReload() {
    }

    @Override
    public JsonObject getDump() {
        JsonObject extra = new JsonObject();
        ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
        Iterator iterator = PROXY.getPluginManager().getPlugins().iterator();
        while (iterator.hasNext()) {
            PluginContainer p;
            plugins.add(new PluginInfo(true, p.getDescription().getName().orElse(p.getDescription().getId()), p.getDescription().getVersion().orElse("Unknown Version"), (p = (PluginContainer)iterator.next()).getInstance().isPresent() ? p.getInstance().get().getClass().getCanonicalName() : "Unknown", p.getDescription().getAuthors()));
        }
        extra.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        extra.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
        return extra;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override
    public java.util.logging.Logger getLogger() {
        return this.logger;
    }

    @Override
    public ViaConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
}

