package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;

public class CommandGive extends CommandBase
{
    private static final String[] I;
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.getPlayers());
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (array.length == "  ".length()) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Item.itemRegistry.getKeys());
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public String getCommandName() {
        return CommandGive.I["".length()];
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (n == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandGive.I[" ".length()];
    }
    
    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    private static void I() {
        (I = new String[0x4F ^ 0x49])["".length()] = I("\u0002\u001d=)", "etKLW");
        CommandGive.I[" ".length()] = I(":\r:4\u00007\u0006$w\u00060\u00142w\u0014*\u00030<", "YbWYa");
        CommandGive.I["  ".length()] = I("\f\u001a*\u001e'\u0001\u00114]!\u0006\u0003\"]3\u001c\u0014 \u0016", "ouGsF");
        CommandGive.I["   ".length()] = I("29\n\u00187?2\u0014[18 \u0002[\"01\"\u0007$>$", "QVguV");
        CommandGive.I[0x86 ^ 0x82] = I("$\u0004,#\t;K2(\u0016", "VeBGf");
        CommandGive.I[0xAE ^ 0xAB] = I("(\"5\"\r%)+a\u000b\";=a\u001f>.;*\u001f8", "KMXOl");
    }
    
    static {
        I();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < "  ".length()) {
            throw new WrongUsageException(CommandGive.I["  ".length()], new Object["".length()]);
        }
        final EntityPlayerMP player = CommandBase.getPlayer(commandSender, array["".length()]);
        final Item itemByText = CommandBase.getItemByText(commandSender, array[" ".length()]);
        int n;
        if (array.length >= "   ".length()) {
            n = CommandBase.parseInt(array["  ".length()], " ".length(), 0x49 ^ 0x9);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        int n3;
        if (array.length >= (0x8C ^ 0x88)) {
            n3 = CommandBase.parseInt(array["   ".length()]);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final ItemStack itemStack = new ItemStack(itemByText, n2, n3);
        if (array.length >= (0x21 ^ 0x24)) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 0x6B ^ 0x6F).getUnformattedText();
            try {
                itemStack.setTagCompound(JsonToNBT.getTagFromJson(unformattedText));
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s = CommandGive.I["   ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = ex.getMessage();
                throw new CommandException(s, array2);
            }
        }
        final boolean addItemStackToInventory = player.inventory.addItemStackToInventory(itemStack);
        if (addItemStackToInventory) {
            player.worldObj.playSoundAtEntity(player, CommandGive.I[0x39 ^ 0x3D], 0.2f, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7f + 1.0f) * 2.0f);
            player.inventoryContainer.detectAndSendChanges();
        }
        if (addItemStackToInventory && itemStack.stackSize <= 0) {
            itemStack.stackSize = " ".length();
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n2);
            final EntityItem dropPlayerItemWithRandomChoice = player.dropPlayerItemWithRandomChoice(itemStack, "".length() != 0);
            if (dropPlayerItemWithRandomChoice != null) {
                dropPlayerItemWithRandomChoice.func_174870_v();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, n2 - itemStack.stackSize);
            final EntityItem dropPlayerItemWithRandomChoice2 = player.dropPlayerItemWithRandomChoice(itemStack, "".length() != 0);
            if (dropPlayerItemWithRandomChoice2 != null) {
                dropPlayerItemWithRandomChoice2.setNoPickupDelay();
                dropPlayerItemWithRandomChoice2.setOwner(player.getName());
            }
        }
        final String s2 = CommandGive.I[0x49 ^ 0x4C];
        final Object[] array3 = new Object["   ".length()];
        array3["".length()] = itemStack.getChatComponent();
        array3[" ".length()] = n2;
        array3["  ".length()] = player.getName();
        CommandBase.notifyOperators(commandSender, this, s2, array3);
    }
}
