package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandGameRule extends CommandBase
{
    @Override
    public String getCommandName() {
        return "gamerule";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.gamerule.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 2) {
            final String var3 = par2ArrayOfStr[0];
            final String var4 = par2ArrayOfStr[1];
            final GameRules var5 = this.getGameRules();
            if (var5.hasRule(var3)) {
                var5.setOrCreateGameRule(var3, var4);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.gamerule.success", new Object[0]);
            }
            else {
                CommandBase.notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var3);
            }
        }
        else if (par2ArrayOfStr.length == 1) {
            final String var3 = par2ArrayOfStr[0];
            final GameRules var6 = this.getGameRules();
            if (var6.hasRule(var3)) {
                final String var7 = var6.getGameRuleStringValue(var3);
                par1ICommandSender.sendChatToPlayer(String.valueOf(var3) + " = " + var7);
            }
            else {
                CommandBase.notifyAdmins(par1ICommandSender, "commands.gamerule.norule", var3);
            }
        }
        else {
            if (par2ArrayOfStr.length != 0) {
                throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
            }
            final GameRules var6 = this.getGameRules();
            par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(var6.getRules()));
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getGameRules().getRules()) : ((par2ArrayOfStr.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "true", "false") : null);
    }
    
    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }
}
