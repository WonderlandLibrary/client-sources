package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerPardon extends CommandBase
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
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.unban.usage", new Object[0]);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0) {
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(par2ArrayOfStr[0]);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.unban.success", par2ArrayOfStr[0]);
            return;
        }
        throw new WrongUsageException("commands.unban.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet()) : null;
    }
}
