package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandEffect extends CommandBase
{
    @Override
    public String getCommandName() {
        return "effect";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.effect.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        final int var4 = CommandBase.parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1);
        int var5 = 600;
        int var6 = 30;
        int var7 = 0;
        if (var4 >= 0 && var4 < Potion.potionTypes.length && Potion.potionTypes[var4] != null) {
            if (par2ArrayOfStr.length >= 3) {
                var6 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 0, 1000000);
                if (Potion.potionTypes[var4].isInstant()) {
                    var5 = var6;
                }
                else {
                    var5 = var6 * 20;
                }
            }
            else if (Potion.potionTypes[var4].isInstant()) {
                var5 = 1;
            }
            if (par2ArrayOfStr.length >= 4) {
                var7 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[3], 0, 255);
            }
            if (var6 == 0) {
                if (!var3.isPotionActive(var4)) {
                    throw new CommandException("commands.effect.failure.notActive", new Object[] { StatCollector.translateToLocal(Potion.potionTypes[var4].getName()), var3.getEntityName() });
                }
                var3.removePotionEffect(var4);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.effect.success.removed", StatCollector.translateToLocal(Potion.potionTypes[var4].getName()), var3.getEntityName());
            }
            else {
                final PotionEffect var8 = new PotionEffect(var4, var5, var7);
                var3.addPotionEffect(var8);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.effect.success", StatCollector.translateToLocal(var8.getEffectName()), var4, var7, var3.getEntityName(), var6);
            }
            return;
        }
        throw new NumberInvalidException("commands.effect.notFound", new Object[] { var4 });
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllUsernames()) : null;
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
