package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;

public class CommandListPlayers extends CommandBase
{
    @Override
    public String getCommandName() {
        return "list";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.players.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        final int var3 = MinecraftServer.getServer().getCurrentPlayerCount();
        sender.addChatMessage(new ChatComponentTranslation("commands.players.list", new Object[] { var3, MinecraftServer.getServer().getMaxPlayers() }));
        sender.addChatMessage(new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_180602_f()));
        sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
    }
}
