/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.CommandSender
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.plugin.Command
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.api.plugin.Plugin
 *  net.md_5.bungee.protocol.ProtocolConstants
 */
package us.myles.ViaVersion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.protocol.ProtocolConstants;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.bungee.commands.BungeeCommand;
import us.myles.ViaVersion.bungee.commands.BungeeCommandHandler;
import us.myles.ViaVersion.bungee.commands.BungeeCommandSender;
import us.myles.ViaVersion.bungee.platform.BungeeTaskId;
import us.myles.ViaVersion.bungee.platform.BungeeViaAPI;
import us.myles.ViaVersion.bungee.platform.BungeeViaConfig;
import us.myles.ViaVersion.bungee.platform.BungeeViaInjector;
import us.myles.ViaVersion.bungee.platform.BungeeViaLoader;
import us.myles.ViaVersion.bungee.service.ProtocolDetectorService;
import us.myles.ViaVersion.dump.PluginInfo;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonObject;

public class BungeePlugin
extends Plugin
implements ViaPlatform<ProxiedPlayer>,
Listener {
    private final ViaConnectionManager connectionManager = new ViaConnectionManager();
    private BungeeViaAPI api;
    private BungeeViaConfig config;

    public void onLoad() {
        try {
            ProtocolConstants.class.getField("MINECRAFT_1_16_3");
        }
        catch (NoSuchFieldException e) {
            this.getLogger().warning("      / \\");
            this.getLogger().warning("     /   \\");
            this.getLogger().warning("    /  |  \\");
            this.getLogger().warning("   /   |   \\         BUNGEECORD IS OUTDATED");
            this.getLogger().warning("  /         \\   VIAVERSION MAY NOT WORK AS INTENDED");
            this.getLogger().warning(" /     o     \\");
            this.getLogger().warning("/_____________\\");
        }
        this.api = new BungeeViaAPI();
        this.config = new BungeeViaConfig(this.getDataFolder());
        BungeeCommandHandler commandHandler = new BungeeCommandHandler();
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new BungeeCommand(commandHandler));
        Via.init(ViaManager.builder().platform(this).injector(new BungeeViaInjector()).loader(new BungeeViaLoader(this)).commandHandler(commandHandler).build());
    }

    public void onEnable() {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        Via.getManager().init();
    }

    @Override
    public String getPlatformName() {
        return this.getProxy().getName();
    }

    @Override
    public String getPlatformVersion() {
        return this.getProxy().getVersion();
    }

    @Override
    public boolean isProxy() {
        return true;
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public TaskId runAsync(Runnable runnable) {
        return new BungeeTaskId(this.getProxy().getScheduler().runAsync((Plugin)this, runnable).getId());
    }

    @Override
    public TaskId runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public TaskId runSync(Runnable runnable, Long ticks) {
        return new BungeeTaskId(this.getProxy().getScheduler().schedule((Plugin)this, runnable, ticks * 50L, TimeUnit.MILLISECONDS).getId());
    }

    @Override
    public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
        return new BungeeTaskId(this.getProxy().getScheduler().schedule((Plugin)this, runnable, 0L, ticks * 50L, TimeUnit.MILLISECONDS).getId());
    }

    @Override
    public void cancelTask(TaskId taskId) {
        if (taskId == null) {
            return;
        }
        if (taskId.getObject() == null) {
            return;
        }
        if (taskId instanceof BungeeTaskId) {
            this.getProxy().getScheduler().cancel(((Integer)taskId.getObject()).intValue());
        }
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        ViaCommandSender[] array = new ViaCommandSender[this.getProxy().getPlayers().size()];
        int i = 0;
        for (ProxiedPlayer player : this.getProxy().getPlayers()) {
            array[i++] = new BungeeCommandSender((CommandSender)player);
        }
        return array;
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        this.getProxy().getPlayer(uuid).sendMessage(message);
    }

    @Override
    public boolean kickPlayer(UUID uuid, String message) {
        ProxiedPlayer player = this.getProxy().getPlayer(uuid);
        if (player != null) {
            player.disconnect(message);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ViaAPI<ProxiedPlayer> getApi() {
        return this.api;
    }

    @Override
    public BungeeViaConfig getConf() {
        return this.config;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }

    @Override
    public void onReload() {
    }

    @Override
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (Plugin p : ProxyServer.getInstance().getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(true, p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), Collections.singletonList(p.getDescription().getAuthor())));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        platformSpecific.add("servers", GsonUtil.getGson().toJsonTree(ProtocolDetectorService.getDetectedIds()));
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
}

