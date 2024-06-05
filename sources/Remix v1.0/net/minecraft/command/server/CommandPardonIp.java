package net.minecraft.command.server;

import net.minecraft.server.*;
import java.util.regex.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandPardonIp extends CommandBase
{
    @Override
    public String getCommandName() {
        return "pardon-ip";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(sender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender sender) {
        return "commands.unbanip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length != 1 || args[0].length() <= 1) {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        final Matcher var3 = CommandBanIp.field_147211_a.matcher(args[0]);
        if (var3.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(args[0]);
            CommandBase.notifyOperators(sender, this, "commands.unbanip.success", args[0]);
            return;
        }
        throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
        return (args.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
    }
}
