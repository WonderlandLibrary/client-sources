/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandListPlayers
extends CommandBase {
    private static final String __OBFID = "CL_00000615";

    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "commands.players.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        int var3 = MinecraftServer.getServer().getCurrentPlayerCount();
        sender.addChatMessage(new ChatComponentTranslation("commands.players.list", var3, MinecraftServer.getServer().getMaxPlayers()));
        sender.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_180602_f()));
        sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
    }
}

