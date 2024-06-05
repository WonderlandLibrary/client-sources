package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerWhitelist extends CommandBase
{
    @Override
    public String getCommandName() {
        return "whitelist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.whitelist.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1) {
            if (par2ArrayOfStr[0].equals("on")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.whitelist.enabled", new Object[0]);
                return;
            }
            if (par2ArrayOfStr[0].equals("off")) {
                MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.whitelist.disabled", new Object[0]);
                return;
            }
            if (par2ArrayOfStr[0].equals("list")) {
                par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.whitelist.list", MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().size(), MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat().length));
                par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().toArray(new String[0])));
                return;
            }
            if (par2ArrayOfStr[0].equals("add")) {
                if (par2ArrayOfStr.length < 2) {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                MinecraftServer.getServer().getConfigurationManager().addToWhiteList(par2ArrayOfStr[1]);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.whitelist.add.success", par2ArrayOfStr[1]);
                return;
            }
            else if (par2ArrayOfStr[0].equals("remove")) {
                if (par2ArrayOfStr.length < 2) {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(par2ArrayOfStr[1]);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.whitelist.remove.success", par2ArrayOfStr[1]);
                return;
            }
            else if (par2ArrayOfStr[0].equals("reload")) {
                MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
                CommandBase.notifyAdmins(par1ICommandSender, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }
        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "on", "off", "list", "add", "remove", "reload");
        }
        if (par2ArrayOfStr.length == 2) {
            if (par2ArrayOfStr[0].equals("add")) {
                final String[] var3 = MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat();
                final ArrayList var4 = new ArrayList();
                final String var5 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
                final String[] var6 = var3;
                for (int var7 = var3.length, var8 = 0; var8 < var7; ++var8) {
                    final String var9 = var6[var8];
                    if (CommandBase.doesStringStartWith(var5, var9) && !MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().contains(var9)) {
                        var4.add(var9);
                    }
                }
                return var4;
            }
            if (par2ArrayOfStr[0].equals("remove")) {
                return CommandBase.getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers());
            }
        }
        return null;
    }
}
