/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.proxy.Player
 */
package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;

public class VelocityViaAPI
extends ViaAPIBase<Player> {
    @Override
    public int getPlayerVersion(Player player) {
        return this.getPlayerVersion(player.getUniqueId());
    }

    @Override
    public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
        this.sendRawPacket(player.getUniqueId(), packet);
    }
}

