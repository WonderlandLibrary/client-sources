/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.CommandSender
 *  net.md_5.bungee.api.plugin.Command
 *  net.md_5.bungee.api.plugin.TabExecutor
 */
package us.myles.ViaVersion.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import us.myles.ViaVersion.bungee.commands.BungeeCommandHandler;
import us.myles.ViaVersion.bungee.commands.BungeeCommandSender;

public class BungeeCommand
extends Command
implements TabExecutor {
    private final BungeeCommandHandler handler;

    public BungeeCommand(BungeeCommandHandler handler) {
        super("viaversion", "", new String[]{"viaver", "vvbungee"});
        this.handler = handler;
    }

    public void execute(CommandSender commandSender, String[] strings) {
        this.handler.onCommand(new BungeeCommandSender(commandSender), strings);
    }

    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return this.handler.onTabComplete(new BungeeCommandSender(commandSender), strings);
    }
}

