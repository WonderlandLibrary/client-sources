/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerToggleSneakEvent
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class PlayerSneakListener
extends ViaBukkitListener {
    private static final float STANDING_HEIGHT = 1.8f;
    private static final float HEIGHT_1_14 = 1.5f;
    private static final float HEIGHT_1_9 = 1.6f;
    private static final float DEFAULT_WIDTH = 0.6f;
    private final boolean is1_9Fix;
    private final boolean is1_14Fix;
    private Map<Player, Boolean> sneaking;
    private Set<UUID> sneakingUuids;
    private final Method getHandle;
    private Method setSize;
    private boolean useCache;

    public PlayerSneakListener(ViaVersionPlugin viaVersionPlugin, boolean bl, boolean bl2) throws ReflectiveOperationException {
        super((Plugin)viaVersionPlugin, null);
        this.is1_9Fix = bl;
        this.is1_14Fix = bl2;
        String string = viaVersionPlugin.getServer().getClass().getPackage().getName();
        this.getHandle = Class.forName(string + ".entity.CraftPlayer").getMethod("getHandle", new Class[0]);
        Class<?> clazz = Class.forName(string.replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer");
        try {
            this.setSize = clazz.getMethod("setSize", Float.TYPE, Float.TYPE);
        } catch (NoSuchMethodException noSuchMethodException) {
            this.setSize = clazz.getMethod("a", Float.TYPE, Float.TYPE);
        }
        if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
            this.sneaking = new WeakHashMap<Player, Boolean>();
            this.useCache = true;
            viaVersionPlugin.getServer().getScheduler().runTaskTimer((Plugin)viaVersionPlugin, new Runnable(this){
                final PlayerSneakListener this$0;
                {
                    this.this$0 = playerSneakListener;
                }

                @Override
                public void run() {
                    for (Map.Entry entry : PlayerSneakListener.access$000(this.this$0).entrySet()) {
                        PlayerSneakListener.access$100(this.this$0, (Player)entry.getKey(), (Boolean)entry.getValue() != false ? 1.5f : 1.6f);
                    }
                }
            }, 1L, 1L);
        }
        if (bl2) {
            this.sneakingUuids = new HashSet<UUID>();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void playerToggleSneak(PlayerToggleSneakEvent playerToggleSneakEvent) {
        Player player = playerToggleSneakEvent.getPlayer();
        UserConnection userConnection = this.getUserConnection(player);
        if (userConnection == null) {
            return;
        }
        ProtocolInfo protocolInfo = userConnection.getProtocolInfo();
        if (protocolInfo == null) {
            return;
        }
        int n = protocolInfo.getProtocolVersion();
        if (this.is1_14Fix && n >= ProtocolVersion.v1_14.getVersion()) {
            this.setHeight(player, playerToggleSneakEvent.isSneaking() ? 1.5f : 1.8f);
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneakingUuids.add(player.getUniqueId());
            } else {
                this.sneakingUuids.remove(player.getUniqueId());
            }
            if (!this.useCache) {
                return;
            }
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneaking.put(player, true);
            } else {
                this.sneaking.remove(player);
            }
        } else if (this.is1_9Fix && n >= ProtocolVersion.v1_9.getVersion()) {
            this.setHeight(player, playerToggleSneakEvent.isSneaking() ? 1.6f : 1.8f);
            if (!this.useCache) {
                return;
            }
            if (playerToggleSneakEvent.isSneaking()) {
                this.sneaking.put(player, false);
            } else {
                this.sneaking.remove(player);
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void playerDamage(EntityDamageEvent entityDamageEvent) {
        if (!this.is1_14Fix) {
            return;
        }
        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.SUFFOCATION) {
            return;
        }
        if (entityDamageEvent.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player)entityDamageEvent.getEntity();
        if (!this.sneakingUuids.contains(player.getUniqueId())) {
            return;
        }
        double d = player.getEyeLocation().getY() + 0.045;
        if ((d -= (double)((int)d)) < 0.09) {
            entityDamageEvent.setCancelled(false);
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent playerQuitEvent) {
        if (this.sneaking != null) {
            this.sneaking.remove(playerQuitEvent.getPlayer());
        }
        if (this.sneakingUuids != null) {
            this.sneakingUuids.remove(playerQuitEvent.getPlayer().getUniqueId());
        }
    }

    private void setHeight(Player player, float f) {
        try {
            this.setSize.invoke(this.getHandle.invoke(player, new Object[0]), Float.valueOf(0.6f), Float.valueOf(f));
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
        }
    }

    static Map access$000(PlayerSneakListener playerSneakListener) {
        return playerSneakListener.sneaking;
    }

    static void access$100(PlayerSneakListener playerSneakListener, Player player, float f) {
        playerSneakListener.setHeight(player, f);
    }
}

