/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1;

import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.ItemTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitInventoryUpdateTask
implements Runnable {
    private final BukkitInventoryQuickMoveProvider provider;
    private final UUID uuid;
    private final List<ItemTransaction> items;

    public BukkitInventoryUpdateTask(BukkitInventoryQuickMoveProvider bukkitInventoryQuickMoveProvider, UUID uUID) {
        this.provider = bukkitInventoryQuickMoveProvider;
        this.uuid = uUID;
        this.items = Collections.synchronizedList(new ArrayList());
    }

    public void addItem(short s, short s2, short s3) {
        ItemTransaction itemTransaction = new ItemTransaction(s, s2, s3);
        this.items.add(itemTransaction);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Player player = Bukkit.getServer().getPlayer(this.uuid);
        if (player == null) {
            this.provider.onTaskExecuted(this.uuid);
            return;
        }
        try {
            List<ItemTransaction> list = this.items;
            synchronized (list) {
                ItemTransaction itemTransaction;
                Object object;
                boolean bl;
                Iterator<ItemTransaction> iterator2 = this.items.iterator();
                while (iterator2.hasNext() && (bl = this.provider.sendPacketToServer(player, object = this.provider.buildWindowClickPacket(player, itemTransaction = iterator2.next())))) {
                }
                this.items.clear();
            }
        } finally {
            this.provider.onTaskExecuted(this.uuid);
        }
    }
}

