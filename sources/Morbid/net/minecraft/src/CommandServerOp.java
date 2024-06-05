package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class CommandServerOp extends CommandBase
{
    @Override
    public String getCommandName() {
        return "op";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.op.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0) {
            MinecraftServer.getServer().getConfigurationManager().addOp(par2ArrayOfStr[0]);
            CommandBase.notifyAdmins(par1ICommandSender, "commands.op.success", par2ArrayOfStr[0]);
            return;
        }
        throw new WrongUsageException("commands.op.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) {
            final String var3 = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            final ArrayList var4 = new ArrayList();
            for (final String var8 : MinecraftServer.getServer().getAllUsernames()) {
                if (!MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var8) && CommandBase.doesStringStartWith(var3, var8)) {
                    var4.add(var8);
                }
            }
            return var4;
        }
        return null;
    }
}
