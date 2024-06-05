package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandGive extends CommandBase
{
    @Override
    public String getCommandName() {
        return "give";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.give.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        final int var4 = CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1);
        int var5 = 1;
        int var6 = 0;
        if (Item.itemsList[var4] == null) {
            throw new NumberInvalidException("commands.give.notFound", new Object[] { var4 });
        }
        if (par2ArrayOfStr.length >= 3) {
            var5 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
        }
        if (par2ArrayOfStr.length >= 4) {
            var6 = CommandBase.parseInt(par1ICommandSender, par2ArrayOfStr[3]);
        }
        final ItemStack var7 = new ItemStack(var4, var5, var6);
        final EntityItem var8 = var3.dropPlayerItem(var7);
        var8.delayBeforeCanPickup = 0;
        CommandBase.notifyAdmins(par1ICommandSender, "commands.give.success", Item.itemsList[var4].func_77653_i(var7), var4, var5, var3.getEntityName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getPlayers()) : null;
    }
    
    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
