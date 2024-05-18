package net.minecraft.src;

import java.util.*;
import net.minecraft.server.*;

public class CommandEnchant extends CommandBase
{
    @Override
    public String getCommandName() {
        return "enchant";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender par1ICommandSender) {
        return par1ICommandSender.translateString("commands.enchant.usage", new Object[0]);
    }
    
    @Override
    public void processCommand(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
        final int var4 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 0, Enchantment.enchantmentsList.length - 1);
        int var5 = 1;
        final ItemStack var6 = var3.getCurrentEquippedItem();
        if (var6 == null) {
            CommandBase.notifyAdmins(par1ICommandSender, "commands.enchant.noItem", new Object[0]);
        }
        else {
            final Enchantment var7 = Enchantment.enchantmentsList[var4];
            if (var7 == null) {
                throw new NumberInvalidException("commands.enchant.notFound", new Object[] { var4 });
            }
            if (!var7.canApply(var6)) {
                CommandBase.notifyAdmins(par1ICommandSender, "commands.enchant.cantEnchant", new Object[0]);
            }
            else {
                if (par2ArrayOfStr.length >= 3) {
                    var5 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], var7.getMinLevel(), var7.getMaxLevel());
                }
                if (var6.hasTagCompound()) {
                    final NBTTagList var8 = var6.getEnchantmentTagList();
                    if (var8 != null) {
                        for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
                            final short var10 = ((NBTTagCompound)var8.tagAt(var9)).getShort("id");
                            if (Enchantment.enchantmentsList[var10] != null) {
                                final Enchantment var11 = Enchantment.enchantmentsList[var10];
                                if (!var11.canApplyTogether(var7)) {
                                    CommandBase.notifyAdmins(par1ICommandSender, "commands.enchant.cantCombine", var7.getTranslatedName(var5), var11.getTranslatedName(((NBTTagCompound)var8.tagAt(var9)).getShort("lvl")));
                                    return;
                                }
                            }
                        }
                    }
                }
                var6.addEnchantment(var7, var5);
                CommandBase.notifyAdmins(par1ICommandSender, "commands.enchant.success", new Object[0]);
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender par1ICommandSender, final String[] par2ArrayOfStr) {
        return (par2ArrayOfStr.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getListOfPlayers()) : null;
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] par1ArrayOfStr, final int par2) {
        return par2 == 0;
    }
}
