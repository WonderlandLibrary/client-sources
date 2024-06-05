package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandPardonPlayer extends CommandBase
{
    @Override
    public String getCommandName() {
        return "pardon";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.unban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(sender);
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 0) {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.getConfigurationManager().getBannedPlayers().isUsernameBanned(args[0]);
        if (var4 == null) {
            throw new CommandException("commands.unban.failed", new Object[] { args[0] });
        }
        var3.getConfigurationManager().getBannedPlayers().removeEntry(var4);
        CommandBase.notifyOperators(sender, this, "commands.unban.success", args[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()) : null;
    }
}
