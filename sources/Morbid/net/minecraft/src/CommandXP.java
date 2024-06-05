package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandXP extends CommandBase
{
    @Override
    public String getCommandName() {
        return "xp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.xp.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
        String var3 = par2ArrayOfStr[0];
        final boolean var4 = var3.endsWith("l") || var3.endsWith("L");
        if (var4 && var3.length() > 1) {
            var3 = var3.substring(0, var3.length() - 1);
        }
        int var5 = CommandBase.parseInt(par1ICommandSender, var3);
        final boolean var6 = var5 < 0;
        if (var6) {
            var5 *= -1;
        }
        EntityPlayerMP var7;
        if (par2ArrayOfStr.length > 1) {
            var7 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[1]);
        }
        else {
            var7 = CommandBase.getCommandSenderAsPlayer(par1ICommandSender);
        }
        if (var4) {
            if (var6) {
                var7.addExperienceLevel(-var5);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.xp.success.negative.levels", var5, var7.getEntityName());
            }
            else {
                var7.addExperienceLevel(var5);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.xp.success.levels", var5, var7.getEntityName());
            }
        }
        else {
            if (var6) {
                throw new WrongUsageException("commands.xp.failure.widthdrawXp", new Object[0]);
            }
            var7.addExperience(var5);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.xp.success", var5, var7.getEntityName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames()) : null;
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 1;
    }
}
