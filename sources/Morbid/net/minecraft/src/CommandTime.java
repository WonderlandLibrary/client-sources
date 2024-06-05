package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandTime extends CommandBase
{
    @Override
    public String getCommandName() {
        return "time";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.time.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 1) {
            if (par2ArrayOfStr[0].equals("set")) {
                int var3;
                if (par2ArrayOfStr[1].equals("day")) {
                    var3 = 0;
                }
                else if (par2ArrayOfStr[1].equals("night")) {
                    var3 = 12500;
                }
                else {
                    var3 = CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                }
                this.setTime(par1ICommandSender, var3);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.time.set", var3);
                return;
            }
            if (par2ArrayOfStr[0].equals("add")) {
                final int var3 = CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 0);
                this.addTime(par1ICommandSender, var3);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.time.added", var3);
                return;
            }
        }
        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "set", "add") : ((par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set")) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "day", "night") : null);
    }
    
    protected void setTime(final ICommandSender par1ICommandSender, final int par2) {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
            MinecraftServer.getServer().worldServers[var3].setWorldTime(par2);
        }
    }
    
    protected void addTime(final ICommandSender par1ICommandSender, final int par2) {
        for (int var3 = 0; var3 < MinecraftServer.getServer().worldServers.length; ++var3) {
            final WorldServer var4 = MinecraftServer.getServer().worldServers[var3];
            var4.setWorldTime(var4.getWorldTime() + par2);
        }
    }
}
