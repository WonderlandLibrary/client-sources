package net.minecraft.command;

import java.util.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public class CommandCompare extends CommandBase
{
    private static final String[] I;
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length > 0 && array.length <= "   ".length()) {
            list = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (array.length > "   ".length() && array.length <= (0x79 ^ 0x7F)) {
            list = CommandBase.func_175771_a(array, "   ".length(), blockPos);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else if (array.length > (0x49 ^ 0x4F) && array.length <= (0xC ^ 0x5)) {
            list = CommandBase.func_175771_a(array, 0xA8 ^ 0xAE, blockPos);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (array.length == (0x56 ^ 0x5C)) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandCompare.I[0xA8 ^ 0xA7];
            array2[" ".length()] = CommandCompare.I[0x44 ^ 0x54];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    private static void I() {
        (I = new String[0xAD ^ 0xBC])["".length()] = I("'\u0000'&\u0014<\u00176>\u001d0\u000e'", "SeTRr");
        CommandCompare.I[" ".length()] = I("\t<\u0018\u00075\u00047\u0006D7\u0005>\u0005\u000b&\u000f}\u0000\u00195\r6", "jSujT");
        CommandCompare.I["  ".length()] = I("4($,49#:o68*9 '2i<240\"", "WGIAU");
        CommandCompare.I["   ".length()] = I("4\u001b\u000b\u001c\u00129\u0010\u0015_\u00108\u0019\u0016\u0010\u00012Z\u0012\u001e\u001c\u001a\u0015\b\b1;\u001b\u0005\u001a\u0000", "Wtfqs");
        CommandCompare.I[0x42 ^ 0x46] = I("\u00188%:\u0013\u0011", "uYVQv");
        CommandCompare.I[0xB7 ^ 0xB2] = I("=", "EPNqD");
        CommandCompare.I[0xC2 ^ 0xC4] = I("\u0016", "ojFww");
        CommandCompare.I[0xC1 ^ 0xC6] = I("8", "BiWev");
        CommandCompare.I[0x1 ^ 0x9] = I(">", "FphNU");
        CommandCompare.I[0x37 ^ 0x3E] = I("*", "SkOGL");
        CommandCompare.I[0x65 ^ 0x6F] = I("\u000e", "tYbbr");
        CommandCompare.I[0x73 ^ 0x78] = I("2\u000b\u001c\n8?\u0000\u0002I:>\t\u0001\u0006+4J\u0017\u00060=\u0001\u0015", "QdqgY");
        CommandCompare.I[0x8D ^ 0x81] = I("\f\u001e*>\u000b\u0001\u00154}\t\u0000\u001c72\u0018\n_4&\t\f\u00144 ", "oqGSj");
        CommandCompare.I[0x84 ^ 0x89] = I("\u0011\n\u0017\u001d\r\u001c\u0001\t^\u000f\u001d\b\n\u0011\u001e\u0017K\u0015\u0005\u0018=\u0003-\u001f\u001e\u001e\u0001", "rezpl");
        CommandCompare.I[0x99 ^ 0x97] = I("'\u0018\u0003\u0002/*\u0013\u001dA-+\u001a\u001e\u000e<!Y\u0001\u001a:\u000b\u00119\u0000<(\u0013", "DwnoN");
        CommandCompare.I[0xAF ^ 0xA0] = I("\u0002'6 4\u000b", "oFEKQ");
        CommandCompare.I[0xAB ^ 0xBB] = I("\u0010\u0016(", "qzDwa");
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandCompare.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0xB6 ^ 0xBF)) {
            throw new WrongUsageException(CommandCompare.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, "   ".length(), "".length() != 0);
        final BlockPos blockPos3 = CommandBase.parseBlockPos(commandSender, array, 0x96 ^ 0x90, "".length() != 0);
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(blockPos, blockPos2);
        final StructureBoundingBox structureBoundingBox2 = new StructureBoundingBox(blockPos3, blockPos3.add(structureBoundingBox.func_175896_b()));
        final int n = structureBoundingBox.getXSize() * structureBoundingBox.getYSize() * structureBoundingBox.getZSize();
        if (n > 153895 + 388075 - 430666 + 412984) {
            final String s = CommandCompare.I["   ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = n;
            array2[" ".length()] = 198548 + 144738 + 95691 + 85311;
            throw new CommandException(s, array2);
        }
        if (structureBoundingBox.minY < 0 || structureBoundingBox.maxY >= 132 + 104 - 70 + 90 || structureBoundingBox2.minY < 0 || structureBoundingBox2.maxY >= 162 + 204 - 264 + 154) {
            throw new CommandException(CommandCompare.I[0x5C ^ 0x52], new Object["".length()]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isAreaLoaded(structureBoundingBox) || !entityWorld.isAreaLoaded(structureBoundingBox2)) {
            throw new CommandException(CommandCompare.I[0x5E ^ 0x53], new Object["".length()]);
        }
        int n2 = "".length();
        if (array.length > (0x48 ^ 0x41) && array[0x4E ^ 0x47].equals(CommandCompare.I[0x99 ^ 0x9D])) {
            n2 = " ".length();
        }
        int length = "".length();
        final BlockPos blockPos4 = new BlockPos(structureBoundingBox2.minX - structureBoundingBox.minX, structureBoundingBox2.minY - structureBoundingBox.minY, structureBoundingBox2.minZ - structureBoundingBox.minZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        final BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
        int i = structureBoundingBox.minZ;
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (i <= structureBoundingBox.maxZ) {
            int j = structureBoundingBox.minY;
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (j <= structureBoundingBox.maxY) {
                int k = structureBoundingBox.minX;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (k <= structureBoundingBox.maxX) {
                    mutableBlockPos.func_181079_c(k, j, i);
                    mutableBlockPos2.func_181079_c(k + blockPos4.getX(), j + blockPos4.getY(), i + blockPos4.getZ());
                    int n3 = "".length();
                    final IBlockState blockState = entityWorld.getBlockState(mutableBlockPos);
                    if (n2 == 0 || blockState.getBlock() != Blocks.air) {
                        if (blockState == entityWorld.getBlockState(mutableBlockPos2)) {
                            final TileEntity tileEntity = entityWorld.getTileEntity(mutableBlockPos);
                            final TileEntity tileEntity2 = entityWorld.getTileEntity(mutableBlockPos2);
                            if (tileEntity != null && tileEntity2 != null) {
                                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                                tileEntity.writeToNBT(nbtTagCompound);
                                nbtTagCompound.removeTag(CommandCompare.I[0x46 ^ 0x43]);
                                nbtTagCompound.removeTag(CommandCompare.I[0x7 ^ 0x1]);
                                nbtTagCompound.removeTag(CommandCompare.I[0x17 ^ 0x10]);
                                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                                tileEntity2.writeToNBT(nbtTagCompound2);
                                nbtTagCompound2.removeTag(CommandCompare.I[0xA3 ^ 0xAB]);
                                nbtTagCompound2.removeTag(CommandCompare.I[0x1E ^ 0x17]);
                                nbtTagCompound2.removeTag(CommandCompare.I[0x9B ^ 0x91]);
                                if (!nbtTagCompound.equals(nbtTagCompound2)) {
                                    n3 = " ".length();
                                    "".length();
                                    if (0 >= 3) {
                                        throw null;
                                    }
                                }
                            }
                            else if (tileEntity != null) {
                                n3 = " ".length();
                                "".length();
                                if (4 < 4) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            n3 = " ".length();
                        }
                        ++length;
                        if (n3 != 0) {
                            throw new CommandException(CommandCompare.I[0xA4 ^ 0xAF], new Object["".length()]);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, length);
        final String s2 = CommandCompare.I[0x4D ^ 0x41];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = length;
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (3 == 2) {
            throw null;
        }
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public String getCommandName() {
        return CommandCompare.I["".length()];
    }
}
