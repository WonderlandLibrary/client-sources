package net.minecraft.command.server;

import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandTestForBlock extends CommandBase
{
    private static final String[] I;
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x5B ^ 0x5F)) {
            throw new WrongUsageException(CommandTestForBlock.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final Block blockFromName = Block.getBlockFromName(array["   ".length()]);
        if (blockFromName == null) {
            final String s = CommandTestForBlock.I["   ".length()];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = array["   ".length()];
            throw new NumberInvalidException(s, array2);
        }
        int int1 = -" ".length();
        if (array.length >= (0x7F ^ 0x7A)) {
            int1 = CommandBase.parseInt(array[0xA8 ^ 0xAC], -" ".length(), 0x2A ^ 0x25);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException(CommandTestForBlock.I[0x1A ^ 0x1E], new Object["".length()]);
        }
        NBTTagCompound tagFromJson = new NBTTagCompound();
        int n = "".length();
        if (array.length >= (0x39 ^ 0x3F) && blockFromName.hasTileEntity()) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 0x30 ^ 0x35).getUnformattedText();
            try {
                tagFromJson = JsonToNBT.getTagFromJson(unformattedText);
                n = " ".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s2 = CommandTestForBlock.I[0x22 ^ 0x27];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = ex.getMessage();
                throw new CommandException(s2, array3);
            }
        }
        final IBlockState blockState = entityWorld.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block != blockFromName) {
            final String s3 = CommandTestForBlock.I[0x70 ^ 0x76];
            final Object[] array4 = new Object[0x5 ^ 0x0];
            array4["".length()] = blockPos.getX();
            array4[" ".length()] = blockPos.getY();
            array4["  ".length()] = blockPos.getZ();
            array4["   ".length()] = block.getLocalizedName();
            array4[0x38 ^ 0x3C] = blockFromName.getLocalizedName();
            throw new CommandException(s3, array4);
        }
        if (int1 > -" ".length()) {
            final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
            if (metaFromState != int1) {
                final String s4 = CommandTestForBlock.I[0x6F ^ 0x68];
                final Object[] array5 = new Object[0x27 ^ 0x22];
                array5["".length()] = blockPos.getX();
                array5[" ".length()] = blockPos.getY();
                array5["  ".length()] = blockPos.getZ();
                array5["   ".length()] = metaFromState;
                array5[0x96 ^ 0x92] = int1;
                throw new CommandException(s4, array5);
            }
        }
        if (n != 0) {
            final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
            if (tileEntity == null) {
                final String s5 = CommandTestForBlock.I[0x95 ^ 0x9D];
                final Object[] array6 = new Object["   ".length()];
                array6["".length()] = blockPos.getX();
                array6[" ".length()] = blockPos.getY();
                array6["  ".length()] = blockPos.getZ();
                throw new CommandException(s5, array6);
            }
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            tileEntity.writeToNBT(nbtTagCompound);
            if (!NBTUtil.func_181123_a(tagFromJson, nbtTagCompound, " ".length() != 0)) {
                final String s6 = CommandTestForBlock.I[0x4A ^ 0x43];
                final Object[] array7 = new Object["   ".length()];
                array7["".length()] = blockPos.getX();
                array7[" ".length()] = blockPos.getY();
                array7["  ".length()] = blockPos.getZ();
                throw new CommandException(s6, array7);
            }
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, " ".length());
        final String s7 = CommandTestForBlock.I[0x30 ^ 0x3A];
        final Object[] array8 = new Object["   ".length()];
        array8["".length()] = blockPos.getX();
        array8[" ".length()] = blockPos.getY();
        array8["  ".length()] = blockPos.getZ();
        CommandBase.notifyOperators(commandSender, this, s7, array8);
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandTestForBlock.I[" ".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length > 0 && array.length <= "   ".length()) {
            list = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (array.length == (0x60 ^ 0x64)) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandTestForBlock.I["".length()];
    }
    
    private static void I() {
        (I = new String[0x5B ^ 0x50])["".length()] = I("2\u001c#\u0003?)\u000b2\u001b6%\u0012", "FyPwY");
        CommandTestForBlock.I[" ".length()] = I("'\"\n\u001b0*)\u0014X%!>\u0013\u0010>6/\u000b\u00192/c\u0012\u00050#(", "DMgvQ");
        CommandTestForBlock.I["  ".length()] = I("\u0013+/)\u0015\u001e 1j\u0000\u001576\"\u001b\u0002&.+\u0017\u001bj77\u0015\u0017!", "pDBDt");
        CommandTestForBlock.I["   ".length()] = I("\u000f8;$%\u00023%g7\t#4%+\u000f<x'+\u0018\u00119<*\b", "lWVID");
        CommandTestForBlock.I[0x2E ^ 0x2A] = I(",&\u0014<5!-\n\u007f *:\r7;=+\u0015>7$g\u0016$ \u0000/.>&#-", "OIyQT");
        CommandTestForBlock.I[0x59 ^ 0x5C] = I("76\u0017\u0018):=\t[;1-\u0018\u0019'72T\u0001)3\u001c\b\u0007'&", "TYzuH");
        CommandTestForBlock.I[0x7C ^ 0x7A] = I("\u000f\f7\u0006\u0013\u0002\u0007)E\u0006\t\u0010.\r\u001d\u001e\u00016\u0004\u0011\u0007M<\n\u001b\u0000\u0006>E\u0006\u0005\u000f?", "lcZkr");
        CommandTestForBlock.I[0x9F ^ 0x98] = I("\u0006\b\f 9\u000b\u0003\u0012c,\u0000\u0014\u0015+7\u0017\u0005\r\";\u000eI\u0007,1\t\u0002\u0005c<\u0004\u0013\u0000", "egaMX");
        CommandTestForBlock.I[0x69 ^ 0x61] = I("\n\u0016%;1\u0007\u001d;x$\f\n<0?\u001b\u001b$93\u0002W.79\u0005\u001c,x$\u0000\u0015-\u0013>\u001d\u0010</", "iyHVP");
        CommandTestForBlock.I[0x99 ^ 0x90] = I("!\u001c\t\u001a\u0013,\u0017\u0017Y\u0006'\u0000\u0010\u0011\u001d0\u0011\b\u0018\u0011)]\u0002\u0016\u001b.\u0016\u0000Y\u001c \u0007", "Bsdwr");
        CommandTestForBlock.I[0x24 ^ 0x2E] = I("9 \u000b\"44+\u0015a!?<\u0012):(-\n 61a\u0015:69*\u0015<", "ZOfOU");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
