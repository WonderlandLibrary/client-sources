package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerKick extends CommandBase
{
    @Override
    public String getCommandName() {
        return "kick";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.kick.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0 || par2ArrayOfStr[0].length() <= 1) {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
        String var4 = "Kicked by an operator.";
        boolean var5 = false;
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (par2ArrayOfStr.length >= 2) {
            var4 = CommandBase.func_82360_a(par1ICommandSender, par2ArrayOfStr, 1);
            var5 = true;
        }
        var3.playerNetServerHandler.kickPlayerFromServer(var4);
        if (var5) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.kick.success.reason", var3.getEntityName(), var4);
        }
        else {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.kick.success", var3.getEntityName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
