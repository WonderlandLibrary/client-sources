package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerEmote extends CommandBase
{
    @Override
    public String getCommandName() {
        return "me";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.me.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length > 0) {
            final String var3 = CommandBase.func_82361_a(par1ICommandSender, par2ArrayOfStr, 0, par1ICommandSender.canCommandSenderUseCommand(1, "me"));
            MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("* " + par1ICommandSender.getCommandSenderName() + " " + var3));
            return;
        }
        throw new WrongUsageException("commands.me.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
}
