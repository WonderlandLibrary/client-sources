package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandClearInventory extends CommandBase
{
    @Override
    public String getCommandName() {
        return "clear";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.clear.usage", new Object[0]);
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        final EntityPlayerMP var3 = (par2ArrayOfStr.length == 0) ? CommandBase.getCommandSenderAsPlayer(par1ICommandSender) : CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        final int var4 = (par2ArrayOfStr.length >= 2) ? CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1) : -1;
        final int var5 = (par2ArrayOfStr.length >= 3) ? CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 0) : -1;
        final int var6 = var3.inventory.clearInventory(var4, var5);
        var3.inventoryContainer.detectAndSendChanges();
        if (var6 == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { var3.getEntityName() });
        }
        CommandBase.notifyAdmins(par1ICommandSender, "commands.clear.success", var3.getEntityName(), var6);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllOnlineUsernames()) : null;
    }
    
    protected String[] getAllOnlineUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
