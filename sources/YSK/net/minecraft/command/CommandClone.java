package net.minecraft.command;

import net.minecraft.block.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandClone extends CommandBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xAF ^ 0xB6])["".length()] = I(",\u0016\u001a\u001b+", "OzuuN");
        CommandClone.I[" ".length()] = I("0,\t\u0004\u0002='\u0017G\u0000?,\n\fM&0\u0005\u000e\u0006", "SCdic");
        CommandClone.I["  ".length()] = I("\u001b*$\n3\u0016!:I1\u0014*'\u0002|\r6(\u00007", "xEIgR");
        CommandClone.I["   ".length()] = I("(=\u001b\u001e2%6\u0005]0'=\u0018\u0016}?=\u0019>2%+4\u001f<(9\u0005", "KRvsS");
        CommandClone.I[0x23 ^ 0x27] = I(" \u001d\u001a,\u000b", "FrhOn");
        CommandClone.I[0x95 ^ 0x90] = I("\u0006\u00181\n", "kwGov");
        CommandClone.I[0x4D ^ 0x4B] = I("\u0000\u001a' \"\r\u00119c \u000f\u001a$(m\r\u001a\u0005;&\u0011\u0019+=", "cuJMC");
        CommandClone.I[0x95 ^ 0x92] = I("\u0004\u0007!\u000b", "ihWnC");
        CommandClone.I[0x34 ^ 0x3C] = I("<\u0017'$*5", "QvTOO");
        CommandClone.I[0x47 ^ 0x4E] = I("-$\u001d<\u000e9(\u0015", "KMqHk");
        CommandClone.I[0x42 ^ 0x48] = I("+\t\u001f;\t&\u0002\u0001x\u000b$\t\u001c3F=\u0015\u00131\r", "HfrVh");
        CommandClone.I[0x1A ^ 0x11] = I(">", "FPQHp");
        CommandClone.I[0xB1 ^ 0xBD] = I("(", "QwAzI");
        CommandClone.I[0x80 ^ 0x8D] = I("\u001f", "eNpnt");
        CommandClone.I[0x41 ^ 0x4F] = I("\u0004>\u001f$ \t5\u0001g\"\u000b>\u001c,o\u00010\u001b%$\u0003", "gQrIA");
        CommandClone.I[0x62 ^ 0x6D] = I("\u0019\"\u0004$\u0019\u0014)\u001ag\u001b\u0016\"\u0007,V\t8\n*\u001d\t>", "zMiIx");
        CommandClone.I[0x8F ^ 0x9F] = I("\u0019):;6\u0014\"$x4\u0016)93y\u00153#\u00191-)%:3", "zFWVW");
        CommandClone.I[0x8F ^ 0x9E] = I("\"6:.\u0006/=$m\u0004-69&I.,#\f\u0001\u00166%/\u0003", "AYWCg");
        CommandClone.I[0xB2 ^ 0xA0] = I("\u0014'\u001c#\r\u0005'", "fBlOl");
        CommandClone.I[0x58 ^ 0x4B] = I("\u001c)\u0001%-\u0015", "qHrNH");
        CommandClone.I[0x4B ^ 0x5F] = I("\u0001\u001d\u001c:*\u0015\u0011\u0014", "gtpNO");
        CommandClone.I[0x5E ^ 0x4B] = I("\u001a\"?\u0018\b\u0018", "tMMui");
        CommandClone.I[0x58 ^ 0x4E] = I("\u000b<\u0006%\u0001", "mStFd");
        CommandClone.I[0x51 ^ 0x46] = I("4\u0002\u001d\f", "YmkiQ");
        CommandClone.I[0x50 ^ 0x48] = I("*\u0018\u0018-$>\u0014\u0010", "LqtYA");
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandName() {
        return CommandClone.I["".length()];
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length > 0 && array.length <= "   ".length()) {
            list = CommandBase.func_175771_a(array, "".length(), blockPos);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (array.length > "   ".length() && array.length <= (0x5D ^ 0x5B)) {
            list = CommandBase.func_175771_a(array, "   ".length(), blockPos);
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else if (array.length > (0x1B ^ 0x1D) && array.length <= (0x35 ^ 0x3C)) {
            list = CommandBase.func_175771_a(array, 0x4B ^ 0x4D, blockPos);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (array.length == (0x4 ^ 0xE)) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = CommandClone.I[0x18 ^ 0xA];
            array2[" ".length()] = CommandClone.I[0xD6 ^ 0xC5];
            array2["  ".length()] = CommandClone.I[0x53 ^ 0x47];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (array.length == (0x56 ^ 0x5D)) {
            final String[] array3 = new String["   ".length()];
            array3["".length()] = CommandClone.I[0x17 ^ 0x2];
            array3[" ".length()] = CommandClone.I[0x7A ^ 0x6C];
            array3["  ".length()] = CommandClone.I[0x60 ^ 0x77];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array3);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else if (array.length == (0x7D ^ 0x71) && CommandClone.I[0x33 ^ 0x2B].equals(array[0x4B ^ 0x42])) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandClone.I[" ".length()];
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x86 ^ 0x8F)) {
            throw new WrongUsageException(CommandClone.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final BlockPos blockPos2 = CommandBase.parseBlockPos(commandSender, array, "   ".length(), "".length() != 0);
        final BlockPos blockPos3 = CommandBase.parseBlockPos(commandSender, array, 0x26 ^ 0x20, "".length() != 0);
        final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(blockPos, blockPos2);
        final StructureBoundingBox structureBoundingBox2 = new StructureBoundingBox(blockPos3, blockPos3.add(structureBoundingBox.func_175896_b()));
        final int n = structureBoundingBox.getXSize() * structureBoundingBox.getYSize() * structureBoundingBox.getZSize();
        if (n > 12213 + 5981 + 7120 + 7454) {
            final String s = CommandClone.I["   ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = n;
            array2[" ".length()] = 19058 + 4978 - 11374 + 20106;
            throw new CommandException(s, array2);
        }
        int n2 = "".length();
        Block blockByText = null;
        int int1 = -" ".length();
        if ((array.length < (0x52 ^ 0x59) || (!array[0x6D ^ 0x67].equals(CommandClone.I[0xB6 ^ 0xB2]) && !array[0x63 ^ 0x69].equals(CommandClone.I[0x65 ^ 0x60]))) && structureBoundingBox.intersectsWith(structureBoundingBox2)) {
            throw new CommandException(CommandClone.I[0x45 ^ 0x43], new Object["".length()]);
        }
        if (array.length >= (0x18 ^ 0x13) && array[0x56 ^ 0x5C].equals(CommandClone.I[0xD ^ 0xA])) {
            n2 = " ".length();
        }
        if (structureBoundingBox.minY < 0 || structureBoundingBox.maxY >= 69 + 187 - 239 + 239 || structureBoundingBox2.minY < 0 || structureBoundingBox2.maxY >= 8 + 249 - 31 + 30) {
            throw new CommandException(CommandClone.I[0x6D ^ 0x7C], new Object["".length()]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isAreaLoaded(structureBoundingBox) || !entityWorld.isAreaLoaded(structureBoundingBox2)) {
            throw new CommandException(CommandClone.I[0xD0 ^ 0xC0], new Object["".length()]);
        }
        int n3 = "".length();
        if (array.length >= (0x9 ^ 0x3)) {
            if (array[0x68 ^ 0x61].equals(CommandClone.I[0x29 ^ 0x21])) {
                n3 = " ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else if (array[0x89 ^ 0x80].equals(CommandClone.I[0x52 ^ 0x5B])) {
                if (array.length < (0x9F ^ 0x93)) {
                    throw new WrongUsageException(CommandClone.I[0x8E ^ 0x84], new Object["".length()]);
                }
                blockByText = CommandBase.getBlockByText(commandSender, array[0x6C ^ 0x67]);
                if (array.length >= (0xB9 ^ 0xB4)) {
                    int1 = CommandBase.parseInt(array[0x8B ^ 0x87], "".length(), 0x9B ^ 0x94);
                }
            }
        }
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        final LinkedList linkedList = Lists.newLinkedList();
        final BlockPos blockPos4 = new BlockPos(structureBoundingBox2.minX - structureBoundingBox.minX, structureBoundingBox2.minY - structureBoundingBox.minY, structureBoundingBox2.minZ - structureBoundingBox.minZ);
        int i = structureBoundingBox.minZ;
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i <= structureBoundingBox.maxZ) {
            int j = structureBoundingBox.minY;
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (j <= structureBoundingBox.maxY) {
                int k = structureBoundingBox.minX;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (k <= structureBoundingBox.maxX) {
                    final BlockPos blockPos5 = new BlockPos(k, j, i);
                    final BlockPos add = blockPos5.add(blockPos4);
                    final IBlockState blockState = entityWorld.getBlockState(blockPos5);
                    if ((n3 == 0 || blockState.getBlock() != Blocks.air) && (blockByText == null || (blockState.getBlock() == blockByText && (int1 < 0 || blockState.getBlock().getMetaFromState(blockState) == int1)))) {
                        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos5);
                        if (tileEntity != null) {
                            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                            tileEntity.writeToNBT(nbtTagCompound);
                            arrayList2.add(new StaticCloneData(add, blockState, nbtTagCompound));
                            linkedList.addLast(blockPos5);
                            "".length();
                            if (0 == 2) {
                                throw null;
                            }
                        }
                        else if (!blockState.getBlock().isFullBlock() && !blockState.getBlock().isFullCube()) {
                            arrayList3.add(new StaticCloneData(add, blockState, null));
                            linkedList.addFirst(blockPos5);
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            arrayList.add(new StaticCloneData(add, blockState, null));
                            linkedList.addLast(blockPos5);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        if (n2 != 0) {
            final Iterator<BlockPos> iterator = (Iterator<BlockPos>)linkedList.iterator();
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockPos blockPos6 = iterator.next();
                final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos6);
                if (tileEntity2 instanceof IInventory) {
                    ((IInventory)tileEntity2).clear();
                }
                entityWorld.setBlockState(blockPos6, Blocks.barrier.getDefaultState(), "  ".length());
            }
            final Iterator<BlockPos> iterator2 = (Iterator<BlockPos>)linkedList.iterator();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                entityWorld.setBlockState(iterator2.next(), Blocks.air.getDefaultState(), "   ".length());
            }
        }
        final ArrayList arrayList4 = Lists.newArrayList();
        arrayList4.addAll(arrayList);
        arrayList4.addAll(arrayList2);
        arrayList4.addAll(arrayList3);
        final List reverse = Lists.reverse((List)arrayList4);
        final Iterator<StaticCloneData> iterator3 = reverse.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final StaticCloneData staticCloneData = iterator3.next();
            final TileEntity tileEntity3 = entityWorld.getTileEntity(staticCloneData.field_179537_a);
            if (tileEntity3 instanceof IInventory) {
                ((IInventory)tileEntity3).clear();
            }
            entityWorld.setBlockState(staticCloneData.field_179537_a, Blocks.barrier.getDefaultState(), "  ".length());
        }
        int length = "".length();
        final Iterator<StaticCloneData> iterator4 = (Iterator<StaticCloneData>)arrayList4.iterator();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator4.hasNext()) {
            final StaticCloneData staticCloneData2 = iterator4.next();
            if (entityWorld.setBlockState(staticCloneData2.field_179537_a, staticCloneData2.blockState, "  ".length())) {
                ++length;
            }
        }
        final Iterator<StaticCloneData> iterator5 = (Iterator<StaticCloneData>)arrayList2.iterator();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (iterator5.hasNext()) {
            final StaticCloneData staticCloneData3 = iterator5.next();
            final TileEntity tileEntity4 = entityWorld.getTileEntity(staticCloneData3.field_179537_a);
            if (staticCloneData3.field_179536_c != null && tileEntity4 != null) {
                staticCloneData3.field_179536_c.setInteger(CommandClone.I[0x41 ^ 0x4A], staticCloneData3.field_179537_a.getX());
                staticCloneData3.field_179536_c.setInteger(CommandClone.I[0x7B ^ 0x77], staticCloneData3.field_179537_a.getY());
                staticCloneData3.field_179536_c.setInteger(CommandClone.I[0x66 ^ 0x6B], staticCloneData3.field_179537_a.getZ());
                tileEntity4.readFromNBT(staticCloneData3.field_179536_c);
                tileEntity4.markDirty();
            }
            entityWorld.setBlockState(staticCloneData3.field_179537_a, staticCloneData3.blockState, "  ".length());
        }
        final Iterator<StaticCloneData> iterator6 = reverse.iterator();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (iterator6.hasNext()) {
            final StaticCloneData staticCloneData4 = iterator6.next();
            entityWorld.notifyNeighborsRespectDebug(staticCloneData4.field_179537_a, staticCloneData4.blockState.getBlock());
        }
        final List<NextTickListEntry> func_175712_a = entityWorld.func_175712_a(structureBoundingBox, "".length() != 0);
        if (func_175712_a != null) {
            final Iterator<NextTickListEntry> iterator7 = func_175712_a.iterator();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (iterator7.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator7.next();
                if (structureBoundingBox.isVecInside(nextTickListEntry.position)) {
                    entityWorld.scheduleBlockUpdate(nextTickListEntry.position.add(blockPos4), nextTickListEntry.getBlock(), (int)(nextTickListEntry.scheduledTime - entityWorld.getWorldInfo().getWorldTotalTime()), nextTickListEntry.priority);
                }
            }
        }
        if (length <= 0) {
            throw new CommandException(CommandClone.I[0x37 ^ 0x39], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, length);
        final String s2 = CommandClone.I[0x46 ^ 0x49];
        final Object[] array3 = new Object[" ".length()];
        array3["".length()] = length;
        CommandBase.notifyOperators(commandSender, this, s2, array3);
        "".length();
        if (2 != 2) {
            throw null;
        }
    }
    
    static {
        I();
    }
    
    static class StaticCloneData
    {
        public final BlockPos field_179537_a;
        public final NBTTagCompound field_179536_c;
        public final IBlockState blockState;
        
        public StaticCloneData(final BlockPos field_179537_a, final IBlockState blockState, final NBTTagCompound field_179536_c) {
            this.field_179537_a = field_179537_a;
            this.blockState = blockState;
            this.field_179536_c = field_179536_c;
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
