/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.CommandSender
 *  net.md_5.bungee.api.plugin.Command
 *  net.md_5.bungee.api.plugin.TabExecutor
 */
package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.bungee.commands.BungeeCommandHandler;
import com.viaversion.viaversion.bungee.commands.BungeeCommandSender;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommand
extends Command
implements TabExecutor {
    private final BungeeCommandHandler handler;

    public BungeeCommand(BungeeCommandHandler bungeeCommandHandler) {
        super("viaversion", "viaversion.admin", new String[]{"viaver", "vvbungee"});
        this.handler = bungeeCommandHandler;
    }

    public void execute(CommandSender commandSender, String[] stringArray) {
        this.handler.onCommand(new BungeeCommandSender(commandSender), stringArray);
    }

    public Iterable<String> onTabComplete(CommandSender commandSender, String[] stringArray) {
        return this.handler.onTabComplete(new BungeeCommandSender(commandSender), stringArray);
    }
}

