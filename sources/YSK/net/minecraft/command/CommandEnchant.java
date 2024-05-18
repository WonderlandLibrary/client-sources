package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class CommandEnchant extends CommandBase
{
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.getListOfPlayers());
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Enchantment.func_181077_c());
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandName() {
        return CommandEnchant.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandEnchant.I[" ".length()];
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandEnchant.I["  ".length()], new Object["".length()]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array["".length()]);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, "".length());
        int n;
        try {
            n = CommandBase.parseInt(array[" ".length()], "".length());
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (NumberInvalidException ex) {
            final Enchantment enchantmentByLocation = Enchantment.getEnchantmentByLocation(array[" ".length()]);
            if (enchantmentByLocation == null) {
                throw ex;
            }
            n = enchantmentByLocation.effectId;
        }
        int n2 = " ".length();
        final ItemStack currentEquippedItem = player.getCurrentEquippedItem();
        if (currentEquippedItem == null) {
            throw new CommandException(CommandEnchant.I["   ".length()], new Object["".length()]);
        }
        final Enchantment enchantmentById = Enchantment.getEnchantmentById(n);
        if (enchantmentById == null) {
            final String s = CommandEnchant.I[0x50 ^ 0x54];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = n;
            throw new NumberInvalidException(s, array2);
        }
        if (!enchantmentById.canApply(currentEquippedItem)) {
            throw new CommandException(CommandEnchant.I[0xBE ^ 0xBB], new Object["".length()]);
        }
        if (array.length >= "   ".length()) {
            n2 = CommandBase.parseInt(array["  ".length()], enchantmentById.getMinLevel(), enchantmentById.getMaxLevel());
        }
        if (currentEquippedItem.hasTagCompound()) {
            final NBTTagList enchantmentTagList = currentEquippedItem.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                int i = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (i < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(i).getShort(CommandEnchant.I[0xC0 ^ 0xC6]);
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        final Enchantment enchantmentById2 = Enchantment.getEnchantmentById(short1);
                        if (!enchantmentById2.canApplyTogether(enchantmentById)) {
                            final String s2 = CommandEnchant.I[0xAB ^ 0xAC];
                            final Object[] array3 = new Object["  ".length()];
                            array3["".length()] = enchantmentById.getTranslatedName(n2);
                            array3[" ".length()] = enchantmentById2.getTranslatedName(enchantmentTagList.getCompoundTagAt(i).getShort(CommandEnchant.I[0xA ^ 0x2]));
                            throw new CommandException(s2, array3);
                        }
                    }
                    ++i;
                }
            }
        }
        currentEquippedItem.addEnchantment(enchantmentById, n2);
        CommandBase.notifyOperators(commandSender, this, CommandEnchant.I[0x4C ^ 0x45], new Object["".length()]);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, " ".length());
    }
    
    private static void I() {
        (I = new String[0x42 ^ 0x48])["".length()] = I("& &+\r-:", "CNECl");
        CommandEnchant.I[" ".length()] = I("4\u001e\u0014*;9\u0015\ni?9\u0012\u0011&4#_\f4;0\u0014", "WqyGZ");
        CommandEnchant.I["  ".length()] = I("4\u0002?\n\u00009\t!I\u00049\u000e:\u0006\u000f#C'\u0014\u00000\b", "WmRga");
        CommandEnchant.I["   ".length()] = I("2:\u0004\u00064?1\u001aE0?6\u0001\n;%{\u0007\u0004\u001c%0\u0004", "QUikU");
        CommandEnchant.I[0x9A ^ 0x9E] = I(")9*8\f$24{\b$5/4\u0003>x):\u0019\f92;\t", "JVGUm");
        CommandEnchant.I[0x14 ^ 0x11] = I("-\u000e55\u0005 \u0005+v\u0001 \u000209\n:O;9\n:$6;\f/\u000f,", "NaXXd");
        CommandEnchant.I[0x95 ^ 0x93] = I("\r\u0011", "duiCE");
        CommandEnchant.I[0x83 ^ 0x84] = I(",)\u001e.\f!\"\u0000m\b!%\u001b\"\u0003;h\u0010\"\u0003;\u0005\u001c.\u000f&(\u0016", "OFsCm");
        CommandEnchant.I[0xB7 ^ 0xBF] = I("<\u0007&", "PqJyF");
        CommandEnchant.I[0xB9 ^ 0xB0] = I("\u0006-(\u001c\u0018\u000b&6_\u001c\u000b!-\u0010\u0017\u0011l6\u0004\u001a\u0006'6\u0002", "eBEqy");
    }
}
