/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.CommandSender
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCommandSender
implements ViaCommandSender {
    private final CommandSender sender;

    public BungeeCommandSender(CommandSender commandSender) {
        this.sender = commandSender;
    }

    @Override
    public boolean hasPermission(String string) {
        return this.sender.hasPermission(string);
    }

    @Override
    public void sendMessage(String string) {
        this.sender.sendMessage(string);
    }

    @Override
    public UUID getUUID() {
        if (this.sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer)this.sender).getUniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        return this.sender.getName();
    }
}

