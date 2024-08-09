/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class TickEvent {
    private final Minecraft minecraft;

    public TickEvent(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public Minecraft getMinecraft() {
        return this.minecraft;
    }

    public PlayerEntity getPlayer() {
        return this.minecraft.player;
    }

    public World getWorld() {
        return this.minecraft.world;
    }
}

