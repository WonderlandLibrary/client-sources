/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
package com.viaversion.viaversion;

import com.google.common.collect.ImmutableList;
import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
import com.viaversion.viaversion.api.platform.ViaServerProxyPlatform;
import com.viaversion.viaversion.bungee.commands.BungeeCommand;
import com.viaversion.viaversion.bungee.commands.BungeeCommandHandler;
import com.viaversion.viaversion.bungee.commands.BungeeCommandSender;
import com.viaversion.viaversion.bungee.platform.BungeeViaAPI;
import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
import com.viaversion.viaversion.bungee.platform.BungeeViaInjector;
import com.viaversion.viaversion.bungee.platform.BungeeViaLoader;
import com.viaversion.viaversion.bungee.platform.BungeeViaTask;
import com.viaversion.viaversion.dump.PluginInfo;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.unsupported.UnsupportedServerSoftware;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.ArrayList;
import java.util.Collection;
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BungeePlugin
extends Plugin
implements ViaServerProxyPlatform<ProxiedPlayer>,
Listener {
    private final com.viaversion.viaversion.bungee.service.ProtocolDetectorService protocolDetectorService = new com.viaversion.viaversion.bungee.service.ProtocolDetectorService();
    private BungeeViaAPI api;
    private BungeeViaConfig config;

    public void onLoad() {
        try {
            ProtocolConstants.class.getField("MINECRAFT_1_19_4");
        } catch (NoSuchFieldException noSuchFieldException) {
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
        BungeeCommandHandler bungeeCommandHandler = new BungeeCommandHandler();
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new BungeeCommand(bungeeCommandHandler));
        Via.init(ViaManagerImpl.builder().platform(this).injector(new BungeeViaInjector()).loader(new BungeeViaLoader(this)).commandHandler(bungeeCommandHandler).build());
    }

    public void onEnable() {
        ViaManagerImpl viaManagerImpl = (ViaManagerImpl)Via.getManager();
        viaManagerImpl.init();
        viaManagerImpl.onServerLoaded();
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
        return false;
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public PlatformTask runAsync(Runnable runnable) {
        return new BungeeViaTask(this.getProxy().getScheduler().runAsync((Plugin)this, runnable));
    }

    @Override
    public PlatformTask runRepeatingAsync(Runnable runnable, long l) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, 0L, l * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public PlatformTask runSync(Runnable runnable) {
        return this.runAsync(runnable);
    }

    @Override
    public PlatformTask runSync(Runnable runnable, long l) {
        return new BungeeViaTask(this.getProxy().getScheduler().schedule((Plugin)this, runnable, l * 50L, TimeUnit.MILLISECONDS));
    }

    @Override
    public PlatformTask runRepeatingSync(Runnable runnable, long l) {
        return this.runRepeatingAsync(runnable, l);
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        Collection collection = this.getProxy().getPlayers();
        ViaCommandSender[] viaCommandSenderArray = new ViaCommandSender[collection.size()];
        int n = 0;
        for (ProxiedPlayer proxiedPlayer : collection) {
            viaCommandSenderArray[n++] = new BungeeCommandSender((CommandSender)proxiedPlayer);
        }
        return viaCommandSenderArray;
    }

    @Override
    public void sendMessage(UUID uUID, String string) {
        this.getProxy().getPlayer(uUID).sendMessage(string);
    }

    @Override
    public boolean kickPlayer(UUID uUID, String string) {
        ProxiedPlayer proxiedPlayer = this.getProxy().getPlayer(uUID);
        if (proxiedPlayer != null) {
            proxiedPlayer.disconnect(string);
            return false;
        }
        return true;
    }

    @Override
    public boolean isPluginEnabled() {
        return false;
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
        JsonObject jsonObject = new JsonObject();
        ArrayList<PluginInfo> arrayList = new ArrayList<PluginInfo>();
        for (Plugin plugin : ProxyServer.getInstance().getPluginManager().getPlugins()) {
            arrayList.add(new PluginInfo(true, plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getMain(), Collections.singletonList(plugin.getDescription().getAuthor())));
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
    public Collection<UnsupportedSoftware> getUnsupportedSoftwareClasses() {
        ArrayList<UnsupportedSoftware> arrayList = new ArrayList<UnsupportedSoftware>(ViaServerProxyPlatform.super.getUnsupportedSoftwareClasses());
        arrayList.add(new UnsupportedServerSoftware.Builder().name("FlameCord").addClassName("dev._2lstudios.flamecord.FlameCord").reason("You are using proxy software that intentionally breaks ViaVersion. Please use another proxy software or move ViaVersion to each backend server instead of the proxy.").build());
        return ImmutableList.copyOf(arrayList);
    }

    @Override
    public boolean hasPlugin(String string) {
        return this.getProxy().getPluginManager().getPlugin(string) != null;
    }

    @Override
    public com.viaversion.viaversion.bungee.service.ProtocolDetectorService protocolDetectorService() {
        return this.protocolDetectorService;
    }

    @Override
    public ProtocolDetectorService protocolDetectorService() {
        return this.protocolDetectorService();
    }

    @Override
    public ViaVersionConfig getConf() {
        return this.getConf();
    }
}

