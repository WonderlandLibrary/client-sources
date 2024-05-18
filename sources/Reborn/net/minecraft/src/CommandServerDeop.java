package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerDeop extends CommandBase
{
    @Override
    public String getCommandName() {
        return "deop";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.deop.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0) {
            MinecraftServer.getServer().getConfigurationManager().removeOp(par2ArrayOfStr[0]);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.deop.success", par2ArrayOfStr[0]);
            return;
        }
        throw new WrongUsageException("commands.deop.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getOps()) : null;
    }
}
