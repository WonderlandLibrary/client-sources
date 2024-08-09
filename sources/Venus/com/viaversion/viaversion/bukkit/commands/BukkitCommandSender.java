/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 */
package com.viaversion.viaversion.bukkit.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

public class BukkitCommandSender
implements ViaCommandSender {
    private final CommandSender sender;

    public BukkitCommandSender(CommandSender commandSender) {
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
        if (this.sender instanceof Entity) {
            return ((Entity)this.sender).getUniqueId();
        }
        return new UUID(0L, 0L);
    }

    @Override
    public String getName() {
        return this.sender.getName();
    }
}

