/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.misc;

import drunkclient.beta.API.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventInventory
extends Event {
    private final EntityPlayer player;

    public EventInventory(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }
}

