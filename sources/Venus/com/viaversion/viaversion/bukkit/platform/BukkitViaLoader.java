/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.compat.ProtocolSupportCompat;
import com.viaversion.viaversion.bukkit.listeners.UpdateListener;
import com.viaversion.viaversion.bukkit.listeners.multiversion.PlayerSneakListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_15to1_14_4.EntityToggleGlideListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_19_4To1_19_3.ArmorToggleListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_19to1_18_2.BlockBreakListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.ArmorListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.BlockListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.DeathListener;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.HandItemCache;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.PaperPatch;
import com.viaversion.viaversion.bukkit.providers.BukkitAckSequenceProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitBlockConnectionProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.bukkit.providers.BukkitViaMovementTransmitter;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class BukkitViaLoader
implements ViaPlatformLoader {
    private final Set<BukkitTask> tasks = new HashSet<BukkitTask>();
    private final ViaVersionPlugin plugin;
    private HandItemCache handItemCache;

    public BukkitViaLoader(ViaVersionPlugin viaVersionPlugin) {
        this.plugin = viaVersionPlugin;
    }

    public void registerListener(Listener listener) {
        this.plugin.getServer().getPluginManager().registerEvents(listener, (Plugin)this.plugin);
    }

    @Deprecated
    public <T extends Listener> T storeListener(T t) {
        return t;
    }

    @Override
    public void load() {
        boolean bl;
        this.registerListener(new UpdateListener());
        ViaVersionPlugin viaVersionPlugin = (ViaVersionPlugin)Bukkit.getPluginManager().getPlugin("ViaVersion");
        if (viaVersionPlugin.isProtocolSupport() && ProtocolSupportCompat.isMultiplatformPS()) {
            ProtocolSupportCompat.registerPSConnectListener(viaVersionPlugin);
        }
        if (!Via.getAPI().getServerVersion().isKnown()) {
            Via.getPlatform().getLogger().severe("Server version has not been loaded yet, cannot register additional listeners");
            return;
        }
        int n = Via.getAPI().getServerVersion().lowestSupportedVersion();
        if (n < ProtocolVersion.v1_9.getVersion()) {
            new ArmorListener((Plugin)viaVersionPlugin).register();
            new DeathListener((Plugin)viaVersionPlugin).register();
            new BlockListener((Plugin)viaVersionPlugin).register();
            if (viaVersionPlugin.getConf().isItemCache()) {
                this.handItemCache = new HandItemCache();
                this.tasks.add(this.handItemCache.runTaskTimerAsynchronously((Plugin)viaVersionPlugin, 1L, 1L));
            }
        }
        if (n < ProtocolVersion.v1_14.getVersion()) {
            boolean bl2 = bl = viaVersionPlugin.getConf().is1_9HitboxFix() && n < ProtocolVersion.v1_9.getVersion();
            if (bl || viaVersionPlugin.getConf().is1_14HitboxFix()) {
                try {
                    new PlayerSneakListener(viaVersionPlugin, bl, viaVersionPlugin.getConf().is1_14HitboxFix()).register();
                } catch (ReflectiveOperationException reflectiveOperationException) {
                    Via.getPlatform().getLogger().warning("Could not load hitbox fix - please report this on our GitHub");
                    reflectiveOperationException.printStackTrace();
                }
            }
        }
        if (n < ProtocolVersion.v1_15.getVersion()) {
            try {
                Class.forName("org.bukkit.event.entity.EntityToggleGlideEvent");
                new EntityToggleGlideListener(viaVersionPlugin).register();
            } catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        if (n < ProtocolVersion.v1_12.getVersion() && !Boolean.getBoolean("com.viaversion.ignorePaperBlockPlacePatch")) {
            bl = true;
            try {
                Class.forName("org.github.paperspigot.PaperSpigotConfig");
            } catch (ClassNotFoundException classNotFoundException) {
                try {
                    Class.forName("com.destroystokyo.paper.PaperConfig");
                } catch (ClassNotFoundException classNotFoundException2) {
                    bl = false;
                }
            }
            if (bl) {
                new PaperPatch((Plugin)viaVersionPlugin).register();
            }
        }
        if (n < ProtocolVersion.v1_19_4.getVersion() && viaVersionPlugin.getConf().isArmorToggleFix() && this.hasGetHandMethod()) {
            new ArmorToggleListener(viaVersionPlugin).register();
        }
        if (n < ProtocolVersion.v1_9.getVersion()) {
            Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BukkitViaMovementTransmitter());
            Via.getManager().getProviders().use(HandItemProvider.class, new HandItemProvider(this){
                final BukkitViaLoader this$0;
                {
                    this.this$0 = bukkitViaLoader;
                }

                @Override
                public Item getHandItem(UserConnection userConnection) {
                    if (BukkitViaLoader.access$000(this.this$0) != null) {
                        return BukkitViaLoader.access$000(this.this$0).getHandItem(userConnection.getProtocolInfo().getUuid());
                    }
                    try {
                        return (Item)Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), () -> 1.lambda$getHandItem$0(userConnection)).get(10L, TimeUnit.SECONDS);
                    } catch (Exception exception) {
                        Via.getPlatform().getLogger().severe("Error fetching hand item: " + exception.getClass().getName());
                        if (Via.getManager().isDebug()) {
                            exception.printStackTrace();
                        }
                        return null;
                    }
                }

                private static Item lambda$getHandItem$0(UserConnection userConnection) throws Exception {
                    UUID uUID = userConnection.getProtocolInfo().getUuid();
                    Player player = Bukkit.getPlayer((UUID)uUID);
                    if (player != null) {
                        return HandItemCache.convert(player.getItemInHand());
                    }
                    return null;
                }
            });
        }
        if (n < ProtocolVersion.v1_12.getVersion() && viaVersionPlugin.getConf().is1_12QuickMoveActionFix()) {
            Via.getManager().getProviders().use(InventoryQuickMoveProvider.class, new BukkitInventoryQuickMoveProvider());
        }
        if (n < ProtocolVersion.v1_13.getVersion() && Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("world")) {
            BukkitBlockConnectionProvider bukkitBlockConnectionProvider = new BukkitBlockConnectionProvider();
            Via.getManager().getProviders().use(BlockConnectionProvider.class, bukkitBlockConnectionProvider);
            ConnectionData.blockConnectionProvider = bukkitBlockConnectionProvider;
        }
        if (n < ProtocolVersion.v1_19.getVersion()) {
            Via.getManager().getProviders().use(AckSequenceProvider.class, new BukkitAckSequenceProvider(viaVersionPlugin));
            new BlockBreakListener(viaVersionPlugin).register();
        }
    }

    private boolean hasGetHandMethod() {
        try {
            PlayerInteractEvent.class.getDeclaredMethod("getHand", new Class[0]);
            Material.class.getMethod("getEquipmentSlot", new Class[0]);
            return true;
        } catch (NoSuchMethodException noSuchMethodException) {
            return true;
        }
    }

    @Override
    public void unload() {
        for (BukkitTask bukkitTask : this.tasks) {
            bukkitTask.cancel();
        }
        this.tasks.clear();
    }

    static HandItemCache access$000(BukkitViaLoader bukkitViaLoader) {
        return bukkitViaLoader.handItemCache;
    }
}

