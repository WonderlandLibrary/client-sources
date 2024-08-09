/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SCooldownPacket;
import net.minecraft.util.CooldownTracker;

public class ServerCooldownTracker
extends CooldownTracker {
    private final ServerPlayerEntity player;

    public ServerCooldownTracker(ServerPlayerEntity serverPlayerEntity) {
        this.player = serverPlayerEntity;
    }

    @Override
    protected void notifyOnSet(Item item, int n) {
        super.notifyOnSet(item, n);
        this.player.connection.sendPacket(new SCooldownPacket(item, n));
    }

    @Override
    protected void notifyOnRemove(Item item) {
        super.notifyOnRemove(item);
        this.player.connection.sendPacket(new SCooldownPacket(item, 0));
    }
}

