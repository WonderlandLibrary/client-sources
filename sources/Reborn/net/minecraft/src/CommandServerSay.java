package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerSay extends CommandBase
{
    @Override
    public String getCommandName() {
        return "say";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.say.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 0) {
            final String var3 = CommandBase.func_82361_a(par1ICommandSender, par2ArrayOfStr, 0, true);
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(String.format("[%s] %s", par1ICommandSender.getCommandSenderName(), var3));
            return;
        }
        throw new WrongUsageException("commands.say.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
