package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandClearInventory extends CommandBase
{
    private static final String[] I;
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x38])["".length()] = I(".5\u001647", "MYsUE");
        CommandClearInventory.I[" ".length()] = I("'\u0017%\u0004-*\u001c;G/(\u001d)\u001bb1\u000b)\u000e)", "DxHiL");
        CommandClearInventory.I["  ".length()] = I(":\r\u0004\u000b\u00077\u0006\u001aH\u00055\u0007\b\u0014H-\u0003\u000e#\u0014+\r\u001b", "Ybiff");
        CommandClearInventory.I["   ".length()] = I("\u0002+\u001a,\u0005\u000f \u0004o\u0007\r!\u00163J\u0007%\u001e-\u0011\u0013!", "aDwAd");
        CommandClearInventory.I[0x9A ^ 0x9E] = I("9%8\t+4.&J)6/4\u0016d<+<\b?(/", "ZJUdJ");
        CommandClearInventory.I[0x26 ^ 0x23] = I("\u000e&\u0014<\u001b\u0003-\n\u007f\u0019\u0001,\u0018#T\u0019,\n%\u0013\u0003.", "mIyQz");
        CommandClearInventory.I[0x9D ^ 0x9B] = I("\u0002\u0006'$'\u000f\r9g%\r\f+;h\u0012\u001c)*#\u0012\u001a", "aiJIF");
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandClearInventory.I[" ".length()];
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected String[] func_147209_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        EntityPlayerMP entityPlayerMP;
        if (array.length == 0) {
            entityPlayerMP = CommandBase.getCommandSenderAsPlayer(commandSender);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            entityPlayerMP = CommandBase.getPlayer(commandSender, array["".length()]);
        }
        final EntityPlayerMP entityPlayerMP2 = entityPlayerMP;
        Item itemByText;
        if (array.length >= "  ".length()) {
            itemByText = CommandBase.getItemByText(commandSender, array[" ".length()]);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            itemByText = null;
        }
        final Item item = itemByText;
        int int1;
        if (array.length >= "   ".length()) {
            int1 = CommandBase.parseInt(array["  ".length()], -" ".length());
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            int1 = -" ".length();
        }
        final int n = int1;
        int int2;
        if (array.length >= (0xA3 ^ 0xA7)) {
            int2 = CommandBase.parseInt(array["   ".length()], -" ".length());
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            int2 = -" ".length();
        }
        final int n2 = int2;
        NBTTagCompound tagFromJson = null;
        if (array.length >= (0x6E ^ 0x6B)) {
            try {
                tagFromJson = JsonToNBT.getTagFromJson(CommandBase.buildString(array, 0x24 ^ 0x20));
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s = CommandClearInventory.I["  ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = ex.getMessage();
                throw new CommandException(s, array2);
            }
        }
        if (array.length >= "  ".length() && item == null) {
            final String s2 = CommandClearInventory.I["   ".length()];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = entityPlayerMP2.getName();
            throw new CommandException(s2, array3);
        }
        final int clearMatchingItems = entityPlayerMP2.inventory.clearMatchingItems(item, n, n2, tagFromJson);
        entityPlayerMP2.inventoryContainer.detectAndSendChanges();
        if (!entityPlayerMP2.capabilities.isCreativeMode) {
            entityPlayerMP2.updateHeldItem();
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, clearMatchingItems);
        if (clearMatchingItems == 0) {
            final String s3 = CommandClearInventory.I[0x65 ^ 0x61];
            final Object[] array4 = new Object[" ".length()];
            array4["".length()] = entityPlayerMP2.getName();
            throw new CommandException(s3, array4);
        }
        if (n2 == 0) {
            final String s4 = CommandClearInventory.I[0xB3 ^ 0xB6];
            final Object[] array5 = new Object["  ".length()];
            array5["".length()] = entityPlayerMP2.getName();
            array5[" ".length()] = clearMatchingItems;
            commandSender.addChatMessage(new ChatComponentTranslation(s4, array5));
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            final String s5 = CommandClearInventory.I[0xB8 ^ 0xBE];
            final Object[] array6 = new Object["  ".length()];
            array6["".length()] = entityPlayerMP2.getName();
            array6[" ".length()] = clearMatchingItems;
            CommandBase.notifyOperators(commandSender, this, s5, array6);
        }
    }
    
    @Override
    public String getCommandName() {
        return CommandClearInventory.I["".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.func_147209_d());
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys());
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
