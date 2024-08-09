/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.CraftingInventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ArmorType;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ArmorListener
extends ViaBukkitListener {
    private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");

    public ArmorListener(Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    public void sendArmorUpdate(Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        int n = 0;
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            n += ArmorType.findById(itemStack.getTypeId()).getArmorPoints();
        }
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_PROPERTIES, null, this.getUserConnection(player));
        try {
            packetWrapper.write(Type.VAR_INT, player.getEntityId());
            packetWrapper.write(Type.INT, 1);
            packetWrapper.write(Type.STRING, "generic.armor");
            packetWrapper.write(Type.DOUBLE, 0.0);
            packetWrapper.write(Type.VAR_INT, 1);
            packetWrapper.write(Type.UUID, ARMOR_ATTRIBUTE);
            packetWrapper.write(Type.DOUBLE, Double.valueOf(n));
            packetWrapper.write(Type.BYTE, (byte)0);
            packetWrapper.scheduleSend(Protocol1_9To1_8.class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        HumanEntity humanEntity = inventoryClickEvent.getWhoClicked();
        if (humanEntity instanceof Player && inventoryClickEvent.getInventory() instanceof CraftingInventory) {
            Player player = (Player)humanEntity;
            if (inventoryClickEvent.getCurrentItem() != null && ArmorType.isArmor(inventoryClickEvent.getCurrentItem().getTypeId())) {
                this.sendDelayedArmorUpdate(player);
                return;
            }
            if (inventoryClickEvent.getRawSlot() >= 5 && inventoryClickEvent.getRawSlot() <= 8) {
                this.sendDelayedArmorUpdate(player);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getItem() != null && (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Player player = playerInteractEvent.getPlayer();
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.getPlugin(), () -> this.lambda$onInteract$0(player), 3L);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onItemBreak(PlayerItemBreakEvent playerItemBreakEvent) {
        this.sendDelayedArmorUpdate(playerItemBreakEvent.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        this.sendDelayedArmorUpdate(playerJoinEvent.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onRespawn(PlayerRespawnEvent playerRespawnEvent) {
        this.sendDelayedArmorUpdate(playerRespawnEvent.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onWorldChange(PlayerChangedWorldEvent playerChangedWorldEvent) {
        this.sendArmorUpdate(playerChangedWorldEvent.getPlayer());
    }

    public void sendDelayedArmorUpdate(Player player) {
        if (!this.isOnPipe(player)) {
            return;
        }
        Via.getPlatform().runSync(() -> this.lambda$sendDelayedArmorUpdate$1(player));
    }

    private void lambda$sendDelayedArmorUpdate$1(Player player) {
        this.sendArmorUpdate(player);
    }

    private void lambda$onInteract$0(Player player) {
        this.sendArmorUpdate(player);
    }
}

