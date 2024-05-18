package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandServerMessage extends CommandBase
{
    @Override
    public List getCommandAliases() {
        return Arrays.asList("w", "msg");
    }
    
    @Override
    public String getCommandName() {
        return "tell";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.message.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (var3 == par1ICommandSender) {
            throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
        }
        final String var4 = CommandBase.func_82361_a(par1ICommandSender, par2ArrayOfStr, 1, !(par1ICommandSender instanceof EntityPlayer));
        var3.sendChatToPlayer(new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.ITALIC).append(var3.translateString("commands.message.display.incoming", par1ICommandSender.getCommandSenderName(), var4)).toString());
        par1ICommandSender.sendChatToPlayer(new StringBuilder().append(EnumChatFormatting.GRAY).append(EnumChatFormatting.ITALIC).append(par1ICommandSender.translateString("commands.message.display.outgoing", var3.getCommandSenderName(), var4)).toString());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
