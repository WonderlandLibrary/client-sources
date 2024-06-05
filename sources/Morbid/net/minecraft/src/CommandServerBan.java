package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerBan extends CommandBase
{
    @Override
    public String getCommandName() {
        return "ban";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.ban.usage", new Object[0]);
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 0) {
            final EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(par2ArrayOfStr[0]);
            final BanEntry var4 = new BanEntry(par2ArrayOfStr[0]);
            var4.setBannedBy(par1ICommandSender.getCommandSenderName());
            if (par2ArrayOfStr.length >= 2) {
                var4.setBanReason(CommandBase.func_82360_a(par1ICommandSender, par2ArrayOfStr, 1));
            }
            MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(var4);
            if (var3 != null) {
                var3.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
            }
            CommandBase.notifyAdmins(par1ICommandSender, "commands.ban.success", par2ArrayOfStr[0]);
            return;
        }
        throw new WrongUsageException("commands.ban.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
