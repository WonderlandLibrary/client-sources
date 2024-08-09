/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.command.Command
 *  com.velocitypowered.api.event.PostOrder
 *  com.velocitypowered.api.event.Subscribe
 *  com.velocitypowered.api.event.proxy.ProxyInitializeEvent
 *  com.velocitypowered.api.plugin.Plugin
 *  com.velocitypowered.api.plugin.PluginContainer
 *  com.velocitypowered.api.plugin.annotation.DataDirectory
 *  com.velocitypowered.api.proxy.Player
 *  com.velocitypowered.api.proxy.ProxyServer
 *  net.kyori.adventure.text.Component
 *  net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
 */
package com.viaversion.viaversion;

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
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ViaServerProxyPlatform;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.velocity.command.VelocityCommandHandler;
import com.viaversion.viaversion.velocity.command.VelocityCommandSender;
import com.viaversion.viaversion.velocity.platform.VelocityViaAPI;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import com.viaversion.viaversion.velocity.platform.VelocityViaLoader;
import com.viaversion.viaversion.velocity.platform.VelocityViaTask;
import com.viaversion.viaversion.velocity.service.ProtocolDetectorService;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(id="viaversion", name="ViaVersion", version="4.7.1-SNAPSHOT", authors={"_MylesC", "creeper123123321", "Gerrygames", "kennytv", "Matsv"}, description="Allow newer Minecraft versions to connect to an older server version.", url="https://viaversion.com")
public class VelocityPlugin
implements ViaServerProxyPlatform<Player> {
    public static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character('\u00a7').extractUrls().build();
    public static ProxyServer PROXY;
    @Inject
    private ProxyServer proxy;
    @Inject
    private Logger loggerslf4j;
    @Inject
    @DataDirectory
    private Path configDir;
    private final ProtocolDetectorService protocolDetectorService = new ProtocolDetectorService();
    private VelocityViaAPI api;
    private java.util.logging.Logger logger;
    private VelocityViaConfig conf;

    @Subscribe
    public void onProxyInit(ProxyInitializeEvent proxyInitializeEvent) {
        Object object;
        if (!this.hasConnectionEvent()) {
            object = this.loggerslf4j;
            object.error("      / \\");
            object.error("     /   \\");
            object.error("    /  |  \\");
            object.error("   /   |   \\        VELOCITY 3.0.0 IS REQUIRED");
            object.error("  /         \\   VIAVERSION WILL NOT WORK AS INTENDED");
            object.error(" /     o     \\");
            object.error("/_____________\\");
        }
        PROXY = this.proxy;
        object = new VelocityCommandHandler();
        PROXY.getCommandManager().register("viaver", (Command)object, new String[]{"vvvelocity", "viaversion"});
        this.api = new VelocityViaAPI();
        this.conf = new VelocityViaConfig(this.configDir.toFile());
        this.logger = new LoggerWrapper(this.loggerslf4j);
        Via.init(ViaManagerImpl.builder().platform(this).commandHandler((ViaCommandHandler)object).loader(new VelocityViaLoader()).injector(new VelocityViaInjector()).build());
    }

    @Subscribe(order=PostOrder.LAST)
    public void onProxyLateInit(ProxyInitializeEvent proxyInitializeEvent) {
        ViaManagerImpl viaManagerImpl = (ViaManagerImpl)Via.getManager();
        viaManagerImpl.init();
        viaManagerImpl.onServerLoaded();
    }

    @Override
    public String getPlatformName() {
        String string = ProxyServer.class.getPackage().getImplementationTitle();
        return string != null ? string : "Velocity";
    }

    @Override
    public String getPlatformVersion() {
        String string = ProxyServer.class.getPackage().getImplementationVersion();
        return string != null ? string : "Unknown";
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    @Override
    public String getPluginVersion() {
        return "4.7.1-SNAPSHOT";
    }

    @Override
    public PlatformTask runAsync(Runnable runnable) {
        return this.runSync(runnable);
    }

    @Override
    public PlatformTask runRepeatingAsync(Runnable runnable, long l) {
        return new VelocityViaTask(PROXY.getScheduler().buildTask((Object)this, runnable).repeat(l * 50L, TimeUnit.MILLISECONDS).schedule());
    }

    @Override
    public PlatformTask runSync(Runnable runnable) {
        return this.runSync(runnable, 0L);
    }

    @Override
    public PlatformTask runSync(Runnable runnable, long l) {
        return new VelocityViaTask(PROXY.getScheduler().buildTask((Object)this, runnable).delay(l * 50L, TimeUnit.MILLISECONDS).schedule());
    }

    @Override
    public PlatformTask runRepeatingSync(Runnable runnable, long l) {
        return this.runRepeatingAsync(runnable, l);
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return (ViaCommandSender[])PROXY.getAllPlayers().stream().map(VelocityCommandSender::new).toArray(VelocityPlugin::lambda$getOnlinePlayers$0);
    }

    @Override
    public void sendMessage(UUID uUID, String string) {
        PROXY.getPlayer(uUID).ifPresent(arg_0 -> VelocityPlugin.lambda$sendMessage$1(string, arg_0));
    }

    @Override
    public boolean kickPlayer(UUID uUID, String string) {
        return PROXY.getPlayer(uUID).map(arg_0 -> VelocityPlugin.lambda$kickPlayer$2(string, arg_0)).orElse(false);
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
        JsonObject jsonObject = new JsonObject();
        ArrayList<PluginInfo> arrayList = new ArrayList<PluginInfo>();
        Iterator iterator2 = PROXY.getPluginManager().getPlugins().iterator();
        while (iterator2.hasNext()) {
            PluginContainer pluginContainer;
            arrayList.add(new PluginInfo(true, pluginContainer.getDescription().getName().orElse(pluginContainer.getDescription().getId()), pluginContainer.getDescription().getVersion().orElse("Unknown Version"), (pluginContainer = (PluginContainer)iterator2.next()).getInstance().isPresent() ? pluginContainer.getInstance().get().getClass().getCanonicalName() : "Unknown", pluginContainer.getDescription().getAuthors()));
        }
        jsonObject.add("plugins", GsonUtil.getGson().toJsonTree(arrayList));
        jsonObject.add("servers", GsonUtil.getGson().toJsonTree(this.protocolDetectorService.detectedProtocolVersions()));
        return jsonObject;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return false;
    }

    @Override
    public boolean hasPlugin(String string) {
        return this.proxy.getPluginManager().getPlugin(string).isPresent();
    }

    @Override
    public java.util.logging.Logger getLogger() {
        return this.logger;
    }

    @Override
    public ProtocolDetectorService protocolDetectorService() {
        return this.protocolDetectorService;
    }

    private boolean hasConnectionEvent() {
        try {
            Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            return true;
        }
    }

    @Override
    public com.viaversion.viaversion.api.platform.ProtocolDetectorService protocolDetectorService() {
        return this.protocolDetectorService();
    }

    @Override
    public ViaVersionConfig getConf() {
        return this.getConf();
    }

    @Override
    public ViaAPI getApi() {
        return this.getApi();
    }

    private static Boolean lambda$kickPlayer$2(String string, Player player) {
        player.disconnect((Component)LegacyComponentSerializer.legacySection().deserialize(string));
        return true;
    }

    private static void lambda$sendMessage$1(String string, Player player) {
        player.sendMessage((Component)COMPONENT_SERIALIZER.deserialize(string));
    }

    private static ViaCommandSender[] lambda$getOnlinePlayers$0(int n) {
        return new ViaCommandSender[n];
    }
}

