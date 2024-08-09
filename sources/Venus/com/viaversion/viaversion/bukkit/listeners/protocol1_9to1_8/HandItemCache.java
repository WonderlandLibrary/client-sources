/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HandItemCache
extends BukkitRunnable {
    private final Map<UUID, Item> handCache = new ConcurrentHashMap<UUID, Item>();

    public void run() {
        ArrayList<UUID> arrayList = new ArrayList<UUID>(this.handCache.keySet());
        for (Player object : Bukkit.getOnlinePlayers()) {
            this.handCache.put(object.getUniqueId(), HandItemCache.convert(object.getItemInHand()));
            arrayList.remove(object.getUniqueId());
        }
        for (UUID uUID : arrayList) {
            this.handCache.remove(uUID);
        }
    }

    public Item getHandItem(UUID uUID) {
        return this.handCache.get(uUID);
    }

    public static Item convert(ItemStack itemStack) {
        if (itemStack == null) {
            return new DataItem(0, 0, 0, null);
        }
        return new DataItem(itemStack.getTypeId(), (byte)itemStack.getAmount(), itemStack.getDurability(), null);
    }
}

