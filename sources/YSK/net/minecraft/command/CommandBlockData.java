package net.minecraft.command;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class CommandBlockData extends CommandBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xB4 ^ 0xBF])["".length()] = I("\u0006:\u001f2$\u00007\u00040", "dVpQO");
        CommandBlockData.I[" ".length()] = I("\u0002\u0000\u0004\u001f6\u000f\u000b\u001a\\5\r\u0000\n\u00193\u0000\u001b\b\\\"\u0012\u000e\u000e\u0017", "aoirW");
        CommandBlockData.I["  ".length()] = I("\f\u0004\u0017\u001b\u000f\u0001\u000f\tX\f\u0003\u0004\u0019\u001d\n\u000e\u001f\u001bX\u001b\u001c\n\u001d\u0013", "okzvn");
        CommandBlockData.I["   ".length()] = I("\u0019\u0000)\u0015\u0005\u0014\u000b7V\u0006\u0016\u0000'\u0013\u0000\u001b\u001b%V\u000b\u000f\u001b\u000b\u001e3\u0015\u001d(\u001c", "zoDxd");
        CommandBlockData.I[0x66 ^ 0x62] = I("\u000e\u001b\t.\t\u0003\u0010\u0017m\n\u0001\u001b\u0007(\f\f\u0000\u0005m\u0006\u0002\u00002\"\u0004\u0004\u0010", "mtdCh");
        CommandBlockData.I[0xC ^ 0x9] = I("&?\u0014\u0002\u0006+4\nA\u0005)?\u001a\u0004\u0003$$\u0018A\u0013$7<\u001d\u0015*\"", "EPyog");
        CommandBlockData.I[0x73 ^ 0x75] = I("1", "INHOT");
        CommandBlockData.I[0x12 ^ 0x15] = I("(", "QHKlv");
        CommandBlockData.I[0xB ^ 0x3] = I("\t", "swfIY");
        CommandBlockData.I[0x9F ^ 0x96] = I("*.\u001f\u0014)'%\u0001W*%.\u0011\u0012,(5\u0013W.((\u001e\u001c,", "IAryH");
        CommandBlockData.I[0xAF ^ 0xA5] = I("\n\r)\n5\u0007\u00067I6\u0005\r'\f0\b\u0016%I'\u001c\u0001'\u0002'\u001a", "ibDgT");
    }
    
    static {
        I();
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> func_175771_a;
        if (array.length > 0 && array.length <= "   ".length()) {
            func_175771_a = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            func_175771_a = null;
        }
        return func_175771_a;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandBlockData.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0xD ^ 0x9)) {
            throw new WrongUsageException(CommandBlockData.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException(CommandBlockData.I["   ".length()], new Object["".length()]);
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity == null) {
            throw new CommandException(CommandBlockData.I[0x94 ^ 0x90], new Object["".length()]);
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        tileEntity.writeToNBT(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
        NBTTagCompound tagFromJson;
        try {
            tagFromJson = JsonToNBT.getTagFromJson(CommandBase.getChatComponentFromNthArg(commandSender, array, "   ".length()).getUnformattedText());
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (NBTException ex) {
            final String s = CommandBlockData.I[0xA7 ^ 0xA2];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = ex.getMessage();
            throw new CommandException(s, array2);
        }
        nbtTagCompound.merge(tagFromJson);
        nbtTagCompound.setInteger(CommandBlockData.I[0x33 ^ 0x35], blockPos.getX());
        nbtTagCompound.setInteger(CommandBlockData.I[0x98 ^ 0x9F], blockPos.getY());
        nbtTagCompound.setInteger(CommandBlockData.I[0x8C ^ 0x84], blockPos.getZ());
        if (nbtTagCompound.equals(nbtTagCompound2)) {
            final String s2 = CommandBlockData.I[0x1B ^ 0x12];
            final Object[] array3 = new Object[" ".length()];
            array3["".length()] = nbtTagCompound.toString();
            throw new CommandException(s2, array3);
        }
        tileEntity.readFromNBT(nbtTagCompound);
        tileEntity.markDirty();
        entityWorld.markBlockForUpdate(blockPos);
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, " ".length());
        final String s3 = CommandBlockData.I[0x6A ^ 0x60];
        final Object[] array4 = new Object[" ".length()];
        array4["".length()] = nbtTagCompound.toString();
        CommandBase.notifyOperators(commandSender, this, s3, array4);
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandName() {
        return CommandBlockData.I["".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
}
