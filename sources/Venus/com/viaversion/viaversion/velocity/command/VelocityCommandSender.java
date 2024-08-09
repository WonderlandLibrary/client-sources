/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.command.CommandSource
 *  com.velocitypowered.api.proxy.Player
 *  net.kyori.adventure.text.Component
 */
package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.kyori.adventure.text.Component;

public class VelocityCommandSender
implements ViaCommandSender {
    private final CommandSource source;

    public VelocityCommandSender(CommandSource commandSource) {
        this.source = commandSource;
    }

    @Override
    public boolean hasPermission(String string) {
        return this.source.hasPermission(string);
    }

    @Override
    public void sendMessage(String string) {
        this.source.sendMessage((Component)VelocityPlugin.COMPONENT_SERIALIZER.deserialize(string));
    }

    @Override
    public UUID getUUID() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        if (this.source instanceof Player) {
            return ((Player)this.source).getUsername();
        }
        return "?";
    }
}

