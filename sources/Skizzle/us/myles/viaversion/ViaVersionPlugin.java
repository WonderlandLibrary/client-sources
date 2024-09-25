/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.TabCompleter
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package us.myles.ViaVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.bukkit.classgenerator.ClassGenerator;
import us.myles.ViaVersion.bukkit.commands.BukkitCommandHandler;
import us.myles.ViaVersion.bukkit.commands.BukkitCommandSender;
import us.myles.ViaVersion.bukkit.platform.BukkitTaskId;
import us.myles.ViaVersion.bukkit.platform.BukkitViaAPI;
import us.myles.ViaVersion.bukkit.platform.BukkitViaConfig;
import us.myles.ViaVersion.bukkit.platform.BukkitViaInjector;
import us.myles.ViaVersion.bukkit.platform.BukkitViaLoader;
import us.myles.ViaVersion.bukkit.util.NMSUtil;
import us.myles.ViaVersion.dump.PluginInfo;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonObject;

public class ViaVersionPlugin
extends JavaPlugin
implements ViaPlatform<Player> {
    private static ViaVersionPlugin instance;
    private final ViaConnectionManager connectionManager = new ViaConnectionManager();
    private final BukkitCommandHandler commandHandler;
    private final BukkitViaConfig conf;
    private final ViaAPI<Player> api = new BukkitViaAPI(this);
    private final List<Runnable> queuedTasks = new ArrayList<Runnable>();
    private final List<Runnable> asyncQueuedTasks = new ArrayList<Runnable>();
    private final boolean protocolSupport;
    private boolean compatSpigotBuild;
    private boolean spigot = true;
    private boolean lateBind;

    public ViaVersionPlugin() {
        instance = this;
        this.commandHandler = new BukkitCommandHandler();
        Via.init(ViaManager.builder().platform(this).commandHandler(this.commandHandler).injector(new BukkitViaInjector()).loader(new BukkitViaLoader(this)).build());
        this.conf = new BukkitViaConfig();
        boolean bl = this.protocolSupport = Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null;
        if (this.protocolSupport) {
            this.getLogger().info("Hooking into ProtocolSupport, to prevent issues!");
            try {
                BukkitViaInjector.patchLists();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onLoad() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        }
        catch (ClassNotFoundException e) {
            this.spigot = false;
        }
        try {
            NMSUtil.nms("PacketEncoder").getDeclaredField("version");
            this.compatSpigotBuild = true;
        }
        catch (Exception e) {
            this.compatSpigotBuild = false;
        }
        if (this.getServer().getPluginManager().getPlugin("ViaBackwards") != null) {
            MappingDataLoader.enableMappingsCache();
        }
        ClassGenerator.generate();
        this.lateBind = !BukkitViaInjector.isBinded();
        this.getLogger().info("ViaVersion " + this.getDescription().getVersion() + (this.compatSpigotBuild ? "compat" : "") + " is now loaded" + (this.lateBind ? ", waiting for boot. (late-bind)" : ", injecting!"));
        if (!this.lateBind) {
            Via.getManager().init();
        }
    }

    public void onEnable() {
        if (this.lateBind) {
            Via.getManager().init();
        }
        this.getCommand("viaversion").setExecutor((CommandExecutor)this.commandHandler);
        this.getCommand("viaversion").setTabCompleter((TabCompleter)this.commandHandler);
        if (this.conf.isAntiXRay() && !this.spigot) {
            this.getLogger().info("You have anti-xray on in your config, since you're not using spigot it won't fix xray!");
        }
        for (Runnable r : this.queuedTasks) {
            Bukkit.getScheduler().runTask((Plugin)this, r);
        }
        this.queuedTasks.clear();
        for (Runnable r : this.asyncQueuedTasks) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)this, r);
        }
        this.asyncQueuedTasks.clear();
    }

    public void onDisable() {
        Via.getManager().destroy();
    }

    @Override
    public String getPlatformName() {
        return Bukkit.getServer().getName();
    }

    @Override
    public String getPlatformVersion() {
        return Bukkit.getServer().getVersion();
    }

    @Override
    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public TaskId runAsync(Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitTaskId(this.getServer().getScheduler().runTaskAsynchronously((Plugin)this, runnable).getTaskId());
        }
        this.asyncQueuedTasks.add(runnable);
        return new BukkitTaskId(null);
    }

    @Override
    public TaskId runSync(Runnable runnable) {
        if (this.isPluginEnabled()) {
            return new BukkitTaskId(this.getServer().getScheduler().runTask((Plugin)this, runnable).getTaskId());
        }
        this.queuedTasks.add(runnable);
        return new BukkitTaskId(null);
    }

    @Override
    public TaskId runSync(Runnable runnable, Long ticks) {
        return new BukkitTaskId(this.getServer().getScheduler().runTaskLater((Plugin)this, runnable, ticks.longValue()).getTaskId());
    }

    @Override
    public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
        return new BukkitTaskId(this.getServer().getScheduler().runTaskTimer((Plugin)this, runnable, 0L, ticks.longValue()).getTaskId());
    }

    @Override
    public void cancelTask(TaskId taskId) {
        if (taskId == null) {
            return;
        }
        if (taskId.getObject() == null) {
            return;
        }
        if (taskId instanceof BukkitTaskId) {
            this.getServer().getScheduler().cancelTask(((Integer)taskId.getObject()).intValue());
        }
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        ViaCommandSender[] array = new ViaCommandSender[Bukkit.getOnlinePlayers().size()];
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            array[i++] = new BukkitCommandSender((CommandSender)player);
        }
        return array;
    }

    @Override
    public void sendMessage(UUID uuid, String message) {
        Player player = Bukkit.getPlayer((UUID)uuid);
        if (player != null) {
            player.sendMessage(message);
        }
    }

    @Override
    public boolean kickPlayer(UUID uuid, String message) {
        Player player = Bukkit.getPlayer((UUID)uuid);
        if (player != null) {
            player.kickPlayer(message);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPluginEnabled() {
        return Bukkit.getPluginManager().getPlugin("ViaVersion").isEnabled();
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.conf;
    }

    @Override
    public void onReload() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.getLogger().severe("ViaVersion is already loaded, we're going to kick all the players... because otherwise we'll crash because of ProtocolLib.");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.translateAlternateColorCodes((char)'&', (String)this.conf.getReloadDisconnectMsg()));
            }
        } else {
            this.getLogger().severe("ViaVersion is already loaded, this should work fine. If you get any console errors, try rebooting.");
        }
    }

    @Override
    public JsonObject getDump() {
        JsonObject platformSpecific = new JsonObject();
        ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(p.isEnabled(), p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getMain(), p.getDescription().getAuthors()));
        }
        platformSpecific.add("plugins", GsonUtil.getGson().toJsonTree(plugins));
        return platformSpecific;
    }

    @Override
    public boolean isOldClientsAllowed() {
        return !this.protocolSupport;
    }

    @Override
    public BukkitViaConfig getConf() {
        return this.conf;
    }

    @Override
    public ViaAPI<Player> getApi() {
        return this.api;
    }

    public boolean isCompatSpigotBuild() {
        return this.compatSpigotBuild;
    }

    public boolean isSpigot() {
        return this.spigot;
    }

    public boolean isProtocolSupport() {
        return this.protocolSupport;
    }

    public static ViaVersionPlugin getInstance() {
        return instance;
    }

    @Override
    public ViaConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
}

