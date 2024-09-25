/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.scheduler.BukkitRunnable
 */
package us.myles.ViaVersion.bukkit.listeners.protocol1_9to1_8;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import us.myles.ViaVersion.api.minecraft.item.Item;

public class HandItemCache
extends BukkitRunnable {
    private final Map<UUID, Item> handCache = new ConcurrentHashMap<UUID, Item>();

    public void run() {
        ArrayList<UUID> players = new ArrayList<UUID>(this.handCache.keySet());
        for (Player p : Bukkit.getOnlinePlayers()) {
            this.handCache.put(p.getUniqueId(), HandItemCache.convert(p.getItemInHand()));
            players.remove(p.getUniqueId());
        }
        for (UUID uuid : players) {
            this.handCache.remove(uuid);
        }
    }

    public Item getHandItem(UUID player) {
        return this.handCache.get(player);
    }

    public static Item convert(ItemStack itemInHand) {
        if (itemInHand == null) {
            return new Item(0, 0, 0, null);
        }
        return new Item(itemInHand.getTypeId(), (byte)itemInHand.getAmount(), itemInHand.getDurability(), null);
    }
}

