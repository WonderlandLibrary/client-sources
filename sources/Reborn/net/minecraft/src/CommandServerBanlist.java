package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerBanlist extends CommandBase
{
    @Override
    public String getCommandName() {
        return "banlist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender) {
        return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive()) && super.canCommandSenderUseCommand(par1ICommandSender);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.banlist.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].equalsIgnoreCase("ips")) {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.banlist.ips", MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().size()));
            par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getBannedList().keySet().toArray()));
        }
        else {
            par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.banlist.players", MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().size()));
            par1ICommandSender.sendChatToPlayer(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet().toArray()));
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, "players", "ips") : null;
    }
}
