package net.minecraft.src;

import net.minecraft.server.*;
import java.util.regex.*;
import java.util.*;

public class CommandServerPardonIp extends CommandBase
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
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.unbanip.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length != 1 || par2ArrayOfStr[0].length() <= 1) {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        final Matcher var3 = CommandServerBanIp.IPv4Pattern.matcher(par2ArrayOfStr[0]);
        if (var3.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().remove(par2ArrayOfStr[0]);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.unbanip.success", par2ArrayOfStr[0]);
            return;
        }
        throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet()) : null;
    }
}
