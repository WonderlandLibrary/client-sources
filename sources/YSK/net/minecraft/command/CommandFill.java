package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandFill extends CommandBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0x38 ^ 0x20])["".length()] = I("\r\u001d\"(", "ktNDq");
        CommandFill.I[" ".length()] = I("!!\u001f#3,*\u0001`4+\"\u001e`'1/\u0015+", "BNrNR");
        CommandFill.I["  ".length()] = I("\u0017\u001a5\u001c\u0015\u001a\u0011+_\u0012\u001d\u00194_\u0001\u0007\u0014?\u0014", "tuXqt");
        CommandFill.I["   ".length()] = I("\u00117=\u0015)\u001c<#V.\u001b4<V<\u001d7\u001d\u0019&\u000b\u001a<\u0017+\u0019+", "rXPxH");
        CommandFill.I[0x6B ^ 0x6F] = I("\u0017\u0018\u0005\u0001\u0007\u001a\u0013\u001bB\u0000\u001d\u001b\u0004B\t\u0001\u0003'\n1\u001b\u0005\u0004\b", "twhlf");
        CommandFill.I[0xBD ^ 0xB8] = I("\u000b6#\u001e\t\u0006==]\u000e\u00015\"]\u001c\t>\u000b\u0001\u001a\u0007+", "hYNsh");
        CommandFill.I[0xA ^ 0xC] = I("\u001e\u0013&\u0014:\u001f\u0003", "qfRxS");
        CommandFill.I[0x7C ^ 0x7B] = I("\u001a5'.-\u0005", "rZKBB");
        CommandFill.I[0x47 ^ 0x4F] = I(",\u001c1'>'\u0000", "HyBSL");
        CommandFill.I[0x32 ^ 0x3B] = I("\u001b&\u0010\u0001", "pCuqo");
        CommandFill.I[0x3B ^ 0x31] = I("578 \"$7", "GRHLC");
        CommandFill.I[0xAB ^ 0xA0] = I("\u0011<:\u0007\n\u000e", "ySVke");
        CommandFill.I[0xAB ^ 0xA7] = I("\u001f", "glIey");
        CommandFill.I[0x9C ^ 0x91] = I("\u000e", "wUkrm");
        CommandFill.I[0xE ^ 0x0] = I("\u0000", "ztJeD");
        CommandFill.I[0x74 ^ 0x7B] = I("\u001b**'\u0004\u0016!4d\u0003\u0011)+d\u0003\u0019,+/\u0001", "xEGJe");
        CommandFill.I[0x6D ^ 0x7D] = I("\u001a.\u0004\u0014\u001b\u0017%\u001aW\u001c\u0010-\u0005W\t\f\"\n\u001c\t\n", "yAiyz");
        CommandFill.I[0x9 ^ 0x18] = I(";9\u0019 \u001862\u0007c\u001f1:\u0018c\u0016-\";+.7$\u0018)", "XVtMy");
        CommandFill.I[0xA3 ^ 0xB1] = I("'\u000e\n\u001b\b6\u000e", "Ukzwi");
        CommandFill.I[0x5 ^ 0x16] = I(">26\u001d:5.", "ZWEiH");
        CommandFill.I[0xAD ^ 0xB9] = I("%=/6", "NXJFn");
        CommandFill.I[0x3 ^ 0x16] = I("\u0004$\u0005\u0015!\u001b", "lKiyN");
        CommandFill.I[0xB6 ^ 0xA0] = I("\u001d,\u0012\u0018,\u001c<", "rYftE");
        CommandFill.I[0xD5 ^ 0xC2] = I("\u0005\u0002#5\u0004\u0014\u0002", "wgSYe");
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x5E ^ 0x59)) {
            throw new WrongUsageException(CommandFill.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, "   ".length(), "".length() != 0);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array[0x96 ^ 0x90]);
        int n = "".length();
        if (array.length >= (0x26 ^ 0x2E)) {
            n = CommandBase.parseInt(array[0x4F ^ 0x48], "".length(), 0x95 ^ 0x9A);
        }
        final BlockPos blockPos3 = new BlockPos(Math.min(blockPos.getX(), blockPos2.getX()), Math.min(blockPos.getY(), blockPos2.getY()), Math.min(blockPos.getZ(), blockPos2.getZ()));
        final BlockPos blockPos4 = new BlockPos(Math.max(blockPos.getX(), blockPos2.getX()), Math.max(blockPos.getY(), blockPos2.getY()), Math.max(blockPos.getZ(), blockPos2.getZ()));
        final int n2 = (blockPos4.getX() - blockPos3.getX() + " ".length()) * (blockPos4.getY() - blockPos3.getY() + " ".length()) * (blockPos4.getZ() - blockPos3.getZ() + " ".length());
        if (n2 > 5277 + 28790 - 25790 + 24491) {
            final String s = CommandFill.I["   ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = n2;
            array2[" ".length()] = 12168 + 28035 - 21809 + 14374;
            throw new CommandException(s, array2);
        }
        if (blockPos3.getY() < 0 || blockPos4.getY() >= 18 + 2 + 171 + 65) {
            throw new CommandException(CommandFill.I[0xD7 ^ 0xC6], new Object["".length()]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        int i = blockPos3.getZ();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < blockPos4.getZ() + (0x14 ^ 0x4)) {
            int j = blockPos3.getX();
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (j < blockPos4.getX() + (0x6A ^ 0x7A)) {
                if (!entityWorld.isBlockLoaded(new BlockPos(j, blockPos4.getY() - blockPos3.getY(), i))) {
                    throw new CommandException(CommandFill.I[0x5D ^ 0x59], new Object["".length()]);
                }
                j += 16;
            }
            i += 16;
        }
        NBTTagCompound tagFromJson = new NBTTagCompound();
        int n3 = "".length();
        if (array.length >= (0x6A ^ 0x60) && blockByText.hasTileEntity()) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 0x8 ^ 0x1).getUnformattedText();
            try {
                tagFromJson = JsonToNBT.getTagFromJson(unformattedText);
                n3 = " ".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s2 = CommandFill.I[0x20 ^ 0x25];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = ex.getMessage();
                throw new CommandException(s2, array3);
            }
        }
        final ArrayList arrayList = Lists.newArrayList();
        int length = "".length();
        int k = blockPos3.getZ();
        "".length();
        if (false) {
            throw null;
        }
        while (k <= blockPos4.getZ()) {
            int l = blockPos3.getY();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (l <= blockPos4.getY()) {
                int x = blockPos3.getX();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (x <= blockPos4.getX()) {
                    final BlockPos blockPos5 = new BlockPos(x, l, k);
                    Label_1361: {
                        if (array.length >= (0x14 ^ 0x1D)) {
                            if (!array[0x1B ^ 0x13].equals(CommandFill.I[0x89 ^ 0x8F]) && !array[0x53 ^ 0x5B].equals(CommandFill.I[0x96 ^ 0x91])) {
                                if (array[0x87 ^ 0x8F].equals(CommandFill.I[0xB3 ^ 0xBB])) {
                                    entityWorld.destroyBlock(blockPos5, " ".length() != 0);
                                    "".length();
                                    if (3 < 1) {
                                        throw null;
                                    }
                                }
                                else if (array[0xB8 ^ 0xB0].equals(CommandFill.I[0x3E ^ 0x37])) {
                                    if (!entityWorld.isAirBlock(blockPos5)) {
                                        "".length();
                                        if (0 == 4) {
                                            throw null;
                                        }
                                        break Label_1361;
                                    }
                                }
                                else if (array[0x4F ^ 0x47].equals(CommandFill.I[0x3A ^ 0x30]) && !blockByText.hasTileEntity()) {
                                    if (array.length > (0x87 ^ 0x8E) && entityWorld.getBlockState(blockPos5).getBlock() != CommandBase.getBlockByText(commandSender, array[0x4A ^ 0x43])) {
                                        "".length();
                                        if (0 >= 2) {
                                            throw null;
                                        }
                                        break Label_1361;
                                    }
                                    else if (array.length > (0x5E ^ 0x54)) {
                                        final int int1 = CommandBase.parseInt(array[0x45 ^ 0x4F]);
                                        final IBlockState blockState = entityWorld.getBlockState(blockPos5);
                                        if (blockState.getBlock().getMetaFromState(blockState) != int1) {
                                            "".length();
                                            if (2 != 2) {
                                                throw null;
                                            }
                                            break Label_1361;
                                        }
                                    }
                                }
                            }
                            else if (x != blockPos3.getX() && x != blockPos4.getX() && l != blockPos3.getY() && l != blockPos4.getY() && k != blockPos3.getZ() && k != blockPos4.getZ()) {
                                if (!array[0x1A ^ 0x12].equals(CommandFill.I[0x71 ^ 0x7A])) {
                                    break Label_1361;
                                }
                                entityWorld.setBlockState(blockPos5, Blocks.air.getDefaultState(), "  ".length());
                                arrayList.add(blockPos5);
                                "".length();
                                if (-1 == 0) {
                                    throw null;
                                }
                                break Label_1361;
                            }
                        }
                        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos5);
                        if (tileEntity != null) {
                            if (tileEntity instanceof IInventory) {
                                ((IInventory)tileEntity).clear();
                            }
                            final World world = entityWorld;
                            final BlockPos blockPos6 = blockPos5;
                            final IBlockState defaultState = Blocks.barrier.getDefaultState();
                            int length2;
                            if (blockByText == Blocks.barrier) {
                                length2 = "  ".length();
                                "".length();
                                if (1 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                length2 = (0x39 ^ 0x3D);
                            }
                            world.setBlockState(blockPos6, defaultState, length2);
                        }
                        if (entityWorld.setBlockState(blockPos5, blockByText.getStateFromMeta(n), "  ".length())) {
                            arrayList.add(blockPos5);
                            ++length;
                            if (n3 != 0) {
                                final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos5);
                                if (tileEntity2 != null) {
                                    tagFromJson.setInteger(CommandFill.I[0x3B ^ 0x37], blockPos5.getX());
                                    tagFromJson.setInteger(CommandFill.I[0x2D ^ 0x20], blockPos5.getY());
                                    tagFromJson.setInteger(CommandFill.I[0x38 ^ 0x36], blockPos5.getZ());
                                    tileEntity2.readFromNBT(tagFromJson);
                                }
                            }
                        }
                    }
                    ++x;
                }
                ++l;
            }
            ++k;
        }
        final Iterator<BlockPos> iterator = (Iterator<BlockPos>)arrayList.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos blockPos7 = iterator.next();
            entityWorld.notifyNeighborsRespectDebug(blockPos7, entityWorld.getBlockState(blockPos7).getBlock());
        }
        if (length <= 0) {
            throw new CommandException(CommandFill.I[0x65 ^ 0x6A], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, length);
        final String s3 = CommandFill.I[0x6B ^ 0x7B];
        final Object[] array4 = new Object[" ".length()];
        array4["".length()] = length;
        CommandBase.notifyOperators(commandSender, this, s3, array4);
        "".length();
        if (-1 >= 2) {
            throw null;
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length > 0 && array.length <= "   ".length()) {
            list = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (array.length > "   ".length() && array.length <= (0xA8 ^ 0xAE)) {
            list = CommandBase.func_175771_a(array, "   ".length(), blockPos);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else if (array.length == (0x7E ^ 0x79)) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (array.length == (0x40 ^ 0x49)) {
            final String[] array2 = new String[0xB3 ^ 0xB6];
            array2["".length()] = CommandFill.I[0xC ^ 0x1E];
            array2[" ".length()] = CommandFill.I[0x30 ^ 0x23];
            array2["  ".length()] = CommandFill.I[0xA7 ^ 0xB3];
            array2["   ".length()] = CommandFill.I[0x40 ^ 0x55];
            array2[0x72 ^ 0x76] = CommandFill.I[0x57 ^ 0x41];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (array.length == (0x51 ^ 0x5B) && CommandFill.I[0xB6 ^ 0xA1].equals(array[0x69 ^ 0x61])) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (2 == -1) {
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandFill.I[" ".length()];
    }
    
    @Override
    public String getCommandName() {
        return CommandFill.I["".length()];
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    static {
        I();
    }
}
