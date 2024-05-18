package net.minecraft.command;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import java.util.*;

public class CommandReplaceItem extends CommandBase
{
    private static final String[] I;
    private static final Map<String, Integer> SHORTCUTS;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandReplaceItem.I[0x4D ^ 0x5D], new Object["".length()]);
        }
        int n;
        if (array["".length()].equals(CommandReplaceItem.I[0xB2 ^ 0xA3])) {
            n = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            if (!array["".length()].equals(CommandReplaceItem.I[0x92 ^ 0x80])) {
                throw new WrongUsageException(CommandReplaceItem.I[0x47 ^ 0x54], new Object["".length()]);
            }
            n = " ".length();
        }
        int length;
        if (n != 0) {
            if (array.length < (0x4F ^ 0x49)) {
                throw new WrongUsageException(CommandReplaceItem.I[0x98 ^ 0x8C], new Object["".length()]);
            }
            length = (0x55 ^ 0x51);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            if (array.length < (0x3C ^ 0x38)) {
                throw new WrongUsageException(CommandReplaceItem.I[0x70 ^ 0x65], new Object["".length()]);
            }
            length = "  ".length();
        }
        final int slotForShortcut = this.getSlotForShortcut(array[length++]);
        Item itemByText;
        try {
            itemByText = CommandBase.getItemByText(commandSender, array[length]);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        catch (NumberInvalidException ex) {
            if (Block.getBlockFromName(array[length]) != Blocks.air) {
                throw ex;
            }
            itemByText = null;
        }
        ++length;
        int n2;
        if (array.length > length) {
            n2 = CommandBase.parseInt(array[length++], " ".length(), 0x3 ^ 0x43);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        int n4;
        if (array.length > length) {
            n4 = CommandBase.parseInt(array[length++]);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        ItemStack itemStack = new ItemStack(itemByText, n3, n4);
        if (array.length > length) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, length).getUnformattedText();
            try {
                itemStack.setTagCompound(JsonToNBT.getTagFromJson(unformattedText));
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            catch (NBTException ex2) {
                final String s = CommandReplaceItem.I[0x42 ^ 0x54];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = ex2.getMessage();
                throw new CommandException(s, array2);
            }
        }
        if (itemStack.getItem() == null) {
            itemStack = null;
        }
        if (n != 0) {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, "".length());
            final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, " ".length(), "".length() != 0);
            final TileEntity tileEntity = commandSender.getEntityWorld().getTileEntity(blockPos);
            if (tileEntity == null || !(tileEntity instanceof IInventory)) {
                final String s2 = CommandReplaceItem.I[0x69 ^ 0x7E];
                final Object[] array3 = new Object["   ".length()];
                array3["".length()] = blockPos.getX();
                array3[" ".length()] = blockPos.getY();
                array3["  ".length()] = blockPos.getZ();
                throw new CommandException(s2, array3);
            }
            final IInventory inventory = (IInventory)tileEntity;
            if (slotForShortcut >= 0 && slotForShortcut < inventory.getSizeInventory()) {
                inventory.setInventorySlotContents(slotForShortcut, itemStack);
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
        }
        else {
            final Entity func_175768_b = CommandBase.func_175768_b(commandSender, array[" ".length()]);
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, "".length());
            if (func_175768_b instanceof EntityPlayer) {
                ((EntityPlayer)func_175768_b).inventoryContainer.detectAndSendChanges();
            }
            if (!func_175768_b.replaceItemInInventory(slotForShortcut, itemStack)) {
                final String s3 = CommandReplaceItem.I[0x54 ^ 0x4C];
                final Object[] array4 = new Object["   ".length()];
                array4["".length()] = slotForShortcut;
                array4[" ".length()] = n3;
                final int length2 = "  ".length();
                Object chatComponent;
                if (itemStack == null) {
                    chatComponent = CommandReplaceItem.I[0x8D ^ 0x94];
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else {
                    chatComponent = itemStack.getChatComponent();
                }
                array4[length2] = chatComponent;
                throw new CommandException(s3, array4);
            }
            if (func_175768_b instanceof EntityPlayer) {
                ((EntityPlayer)func_175768_b).inventoryContainer.detectAndSendChanges();
            }
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n3);
        final String s4 = CommandReplaceItem.I[0xA1 ^ 0xBB];
        final Object[] array5 = new Object["   ".length()];
        array5["".length()] = slotForShortcut;
        array5[" ".length()] = n3;
        final int length3 = "  ".length();
        Object chatComponent2;
        if (itemStack == null) {
            chatComponent2 = CommandReplaceItem.I[0x3C ^ 0x27];
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            chatComponent2 = itemStack.getChatComponent();
        }
        array5[length3] = chatComponent2;
        CommandBase.notifyOperators(commandSender, this, s4, array5);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (array.length > 0 && array["".length()].equals(CommandReplaceItem.I[0x46 ^ 0x63]) && n == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected String[] getUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    private int getSlotForShortcut(final String s) throws CommandException {
        if (!CommandReplaceItem.SHORTCUTS.containsKey(s)) {
            final String s2 = CommandReplaceItem.I[0x5 ^ 0x19];
            final Object[] array = new Object[" ".length()];
            array["".length()] = s;
            throw new CommandException(s2, array);
        }
        return CommandReplaceItem.SHORTCUTS.get(s);
    }
    
    static {
        I();
        SHORTCUTS = Maps.newHashMap();
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < (0x1C ^ 0x2A)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I["".length()] + i, i);
            ++i;
        }
        int j = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (j < (0x39 ^ 0x30)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[" ".length()] + j, j);
            ++j;
        }
        int k = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (k < (0x96 ^ 0x8D)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I["  ".length()] + k, (0x17 ^ 0x1E) + k);
            ++k;
        }
        int l = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (l < (0x83 ^ 0x98)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I["   ".length()] + l, 24 + 72 - 54 + 158 + l);
            ++l;
        }
        int length = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (length < (0x25 ^ 0x2D)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x8B ^ 0x8F] + length, 0 + 297 - 209 + 212 + length);
            ++length;
        }
        int length2 = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (length2 < (0x4 ^ 0xB)) {
            CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0xAB ^ 0xAE] + length2, 237 + 249 - 167 + 181 + length2);
            ++length2;
        }
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x3E ^ 0x38], 0x59 ^ 0x3A);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x3F ^ 0x38], 0x7D ^ 0x1A);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x96 ^ 0x9E], 0xD7 ^ 0xB1);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x38 ^ 0x31], 0xC5 ^ 0xA0);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x27 ^ 0x2D], 0x7D ^ 0x19);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x50 ^ 0x5B], 338 + 386 - 535 + 211);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x9A ^ 0x96], 203 + 359 - 352 + 191);
        CommandReplaceItem.SHORTCUTS.put(CommandReplaceItem.I[0x8A ^ 0x87], 300 + 176 - 394 + 417);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandReplaceItem.I[0x47 ^ 0x48];
    }
    
    private static void I() {
        (I = new String[0x4A ^ 0x6C])["".length()] = I("\u0011*\u001c h\u0001)\u001d '\u000b(\u0016&h", "bFsTF");
        CommandReplaceItem.I[" ".length()] = I("\u001f-96@\u0004.\" \u000f\u001eo", "lAVBn");
        CommandReplaceItem.I["  ".length()] = I(" \u001a\u00065X:\u0018\u001f$\u0018'\u0019\u001b8X", "SviAv");
        CommandReplaceItem.I["   ".length()] = I("9!(0c/##!?)%\"79d", "JMGDM");
        CommandReplaceItem.I[0xB6 ^ 0xB2] = I("\u0002\t\u0003\u0016w\u0007\f\u0000\u000e8\u0016\u0000\u001eL", "qelbY");
        CommandReplaceItem.I[0x42 ^ 0x47] = I("\"5\u00057i96\u00180\"\u007f", "QYjCG");
        CommandReplaceItem.I[0x9 ^ 0xF] = I("'\u0003)\u0015b#\n'\u0011#:", "ToFaL");
        CommandReplaceItem.I[0x56 ^ 0x51] = I("\u001d\u000f\u000e\fV\u000f\u0011\f\u0017\n@\u000b\u0004\u0019\u001c", "ncaxx");
        CommandReplaceItem.I[0xCE ^ 0xC6] = I("6.\u0019\u001cW$0\u001b\u0007\u000bk!\u001e\r\n1", "EBvhy");
        CommandReplaceItem.I[0x29 ^ 0x20] = I("6(\u0015\u001dy$6\u0017\u0006%k(\u001f\u000e$", "EDziW");
        CommandReplaceItem.I[0x8D ^ 0x87] = I(";\u001b=\rd)\u0005?\u00168f\u00117\u001c>", "HwRyJ");
        CommandReplaceItem.I[0x18 ^ 0x13] = I("4\u001e>,I/\u001d#+\u0002i\u00010<\u0003+\u0017", "GrQXg");
        CommandReplaceItem.I[0x38 ^ 0x34] = I("\u00164\t\u0019\u007f\r7\u0014\u001e4K9\u0014\u0000>\u0017", "eXfmQ");
        CommandReplaceItem.I[0x4B ^ 0x46] = I("8\u001c\u00035w#\u001f\u001e2<e\u0013\u0004$*?", "KplAY");
        CommandReplaceItem.I[0x32 ^ 0x3C] = I("9&5\u00068(&,\u001e<&", "KCEjY");
        CommandReplaceItem.I[0x17 ^ 0x18] = I("\u0016#>(\"\u001b( k1\u0010<?$ \u0010%' .[9 $$\u0010", "uLSEC");
        CommandReplaceItem.I[0xAC ^ 0xBC] = I("\u0005\t8,2\b\u0002&o!\u0003\u00169 0\u0003\u000f!$>H\u0013& 4\u0003", "ffUAS");
        CommandReplaceItem.I[0xBC ^ 0xAD] = I("7\u001c7+\"+", "RrCBV");
        CommandReplaceItem.I[0x3E ^ 0x2C] = I("\t?\u001e&\u001d", "kSqEv");
        CommandReplaceItem.I[0x1B ^ 0x8] = I("\u0011+\u0015\u0004\u0012\u001c \u000bG\u0001\u00174\u0014\b\u0010\u0017-\f\f\u001e\\1\u000b\b\u0014\u0017", "rDxis");
        CommandReplaceItem.I[0x95 ^ 0x81] = I("\u00045\u0018%#\t>\u0006f0\u0002*\u0019)!\u00023\u0001-/I8\u0019'!\ft\u0000;#\u0000?", "gZuHB");
        CommandReplaceItem.I[0x96 ^ 0x83] = I("\u0007\u0006.&\u0003\n\r0e\u0010\u0001\u0019/*\u0001\u0001\u00007.\u000fJ\f-?\u000b\u0010\u0010m>\u0011\u0005\u000e&", "diCKb");
        CommandReplaceItem.I[0x66 ^ 0x70] = I("+8+>9&35}*-'*2;->265f#'4\u001d:%)!", "HWFSX");
        CommandReplaceItem.I[0x89 ^ 0x9E] = I("/(\u0002)'\"#\u001cj4)7\u0003%%).\u001b!+b)\u0000\u0007)\"3\u000e-()5", "LGoDF");
        CommandReplaceItem.I[0x7C ^ 0x64] = I("\u0006!)4+\u000b*7w8\u0000>(8)\u0000'0<'K(%0&\u0000*", "eNDYJ");
        CommandReplaceItem.I[0x68 ^ 0x71] = I("$''", "eNUto");
        CommandReplaceItem.I[0x94 ^ 0x8E] = I("'9?\u001e\u0018*2!]\u000b!&>\u0012\u001a!?&\u0016\u0014j%'\u0010\u001a!%!", "DVRsy");
        CommandReplaceItem.I[0x3F ^ 0x24] = I("9 \u0019", "xIkAE");
        CommandReplaceItem.I[0x80 ^ 0x9C] = I("/*#\u001f3\"!=\\5)++\u0000;/k>\u0013 -(+\u00067>k'\u001c$-)'\u0016", "LENrR");
        CommandReplaceItem.I[0x6A ^ 0x77] = I("\u0000)8<\u0002\u001c", "eGLUv");
        CommandReplaceItem.I[0x6A ^ 0x74] = I("$\u0000\n6)", "FleUB");
        CommandReplaceItem.I[0x40 ^ 0x5F] = I("\u0003#&\u0005\u0002\u001f", "fMRlv");
        CommandReplaceItem.I[0x27 ^ 0x7] = I("/\u0005$\r\u0002", "MiKni");
        CommandReplaceItem.I[0x2 ^ 0x23] = I("\u0002!3\u000f0\u001e", "gOGfD");
        CommandReplaceItem.I[0x88 ^ 0xAA] = I(":693)", "XZVPB");
        CommandReplaceItem.I[0x62 ^ 0x41] = I("&\b \u0013\u0004:", "CfTzp");
        CommandReplaceItem.I[0xA6 ^ 0x82] = I("$;.\"\u000e", "FWAAe");
        CommandReplaceItem.I[0x3C ^ 0x19] = I("\u0011<\u0006\u0004'\r", "tRrmS");
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandReplaceItem.I[0x2B ^ 0x36];
            array2[" ".length()] = CommandReplaceItem.I[0x72 ^ 0x6C];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandReplaceItem.I[0xBC ^ 0xA3])) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.getUsernames());
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if (array.length >= "  ".length() && array.length <= (0x1B ^ 0x1F) && array["".length()].equals(CommandReplaceItem.I[0x3F ^ 0x1F])) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else if ((array.length != "   ".length() || !array["".length()].equals(CommandReplaceItem.I[0x12 ^ 0x33])) && (array.length != (0x6D ^ 0x68) || !array["".length()].equals(CommandReplaceItem.I[0x30 ^ 0x12]))) {
            if ((array.length != (0xC0 ^ 0xC4) || !array["".length()].equals(CommandReplaceItem.I[0x9 ^ 0x2A])) && (array.length != (0x8B ^ 0x8D) || !array["".length()].equals(CommandReplaceItem.I[0x66 ^ 0x42]))) {
                list = null;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                list = CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys());
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
        }
        else {
            list = CommandBase.getListOfStringsMatchingLastWord(array, CommandReplaceItem.SHORTCUTS.keySet());
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandReplaceItem.I[0x7B ^ 0x75];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
