package net.minecraft.command.server;

import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;

public class CommandSetBlock extends CommandBase
{
    private static final String[] I;
    
    @Override
    public String getCommandName() {
        return CommandSetBlock.I["".length()];
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return "  ".length();
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandSetBlock.I[" ".length()];
    }
    
    private static void I() {
        (I = new String[0x61 ^ 0x70])["".length()] = I("*\f7%!6\n(", "YiCGM");
        CommandSetBlock.I[" ".length()] = I("+\u0017#=\u0014&\u001c=~\u0006-\f,<\u001a+\u0013`%\u0006)\u001f+", "HxNPu");
        CommandSetBlock.I["  ".length()] = I("\u0004\u001f5\u001b \t\u0014+X2\u0002\u0004:\u001a.\u0004\u001bv\u00032\u0006\u0017=", "gpXvA");
        CommandSetBlock.I["   ".length()] = I("\u000f\u0007\u0004\u001b\"\u0002\f\u001aX0\t\u001c\u000b\u001a,\u000f\u0003G\u00196\u0018'\u000f!,\u001e\u0004\r", "lhivC");
        CommandSetBlock.I[0x18 ^ 0x1C] = I("954\"\u001b4>*a\t?.;#\u001591w;\u001b=\u001f+=\u0015(", "ZZYOz");
        CommandSetBlock.I[0x8A ^ 0x8F] = I("\u0010\u0000\u001d;5\u001b\u001c", "tenOG");
        CommandSetBlock.I[0x7B ^ 0x7D] = I("\u001b-\u001f/5\u0016&\u0001l'\u001d6\u0010.;\u001b)\\1!\u001b!\u00171'", "xBrBT");
        CommandSetBlock.I[0xA8 ^ 0xAF] = I("8<\u0013\u0018", "SYvhJ");
        CommandSetBlock.I[0xAD ^ 0xA5] = I("9>>\u001f\u000245 \\\u0010?%1\u001e\f9:}\u001c\f\u001992\u001c\u0004?", "ZQSrc");
        CommandSetBlock.I[0x8A ^ 0x83] = I("\u000b<\u0003?3\u00067\u001d|!\r'\f>=\u000b8@<=+;\u000f<5\r", "hSnRR");
        CommandSetBlock.I[0x6E ^ 0x64] = I("\u001f", "gRmvi");
        CommandSetBlock.I[0x50 ^ 0x5B] = I("2", "KipVq");
        CommandSetBlock.I[0x7E ^ 0x72] = I("\u0016", "lliGU");
        CommandSetBlock.I[0x92 ^ 0x9F] = I("\b\u001f\u0001!\r\u0005\u0014\u001fb\u001f\u000e\u0004\u000e \u0003\b\u001bB?\u0019\b\u0013\t?\u001f", "kplLl");
        CommandSetBlock.I[0xCF ^ 0xC1] = I("\u0015\f?\u000e,\u0004\f", "giObM");
        CommandSetBlock.I[0x97 ^ 0x98] = I(">\u0014\u0005\u0019\u00145\b", "Zqvmf");
        CommandSetBlock.I[0x76 ^ 0x66] = I("=37\u001f", "VVRoo");
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
        else if (array.length == (0xC3 ^ 0xC7)) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, Block.blockRegistry.getKeys());
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (array.length == (0x3C ^ 0x3A)) {
            final String[] array2 = new String["   ".length()];
            array2["".length()] = CommandSetBlock.I[0x13 ^ 0x1D];
            array2[" ".length()] = CommandSetBlock.I[0x94 ^ 0x9B];
            array2["  ".length()] = CommandSetBlock.I[0xA2 ^ 0xB2];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            list = null;
        }
        return list;
    }
    
    @Override
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < (0x72 ^ 0x76)) {
            throw new WrongUsageException(CommandSetBlock.I["  ".length()], new Object["".length()]);
        }
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, "".length());
        final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, "".length(), "".length() != 0);
        final Block blockByText = CommandBase.getBlockByText(commandSender, array["   ".length()]);
        int n = "".length();
        if (array.length >= (0x9A ^ 0x9F)) {
            n = CommandBase.parseInt(array[0x15 ^ 0x11], "".length(), 0x13 ^ 0x1C);
        }
        final World entityWorld = commandSender.getEntityWorld();
        if (!entityWorld.isBlockLoaded(blockPos)) {
            throw new CommandException(CommandSetBlock.I["   ".length()], new Object["".length()]);
        }
        NBTTagCompound tagFromJson = new NBTTagCompound();
        int n2 = "".length();
        if (array.length >= (0xC5 ^ 0xC2) && blockByText.hasTileEntity()) {
            final String unformattedText = CommandBase.getChatComponentFromNthArg(commandSender, array, 0x97 ^ 0x91).getUnformattedText();
            try {
                tagFromJson = JsonToNBT.getTagFromJson(unformattedText);
                n2 = " ".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            catch (NBTException ex) {
                final String s = CommandSetBlock.I[0x7A ^ 0x7E];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = ex.getMessage();
                throw new CommandException(s, array2);
            }
        }
        if (array.length >= (0x86 ^ 0x80)) {
            if (array[0xC ^ 0x9].equals(CommandSetBlock.I[0xA8 ^ 0xAD])) {
                entityWorld.destroyBlock(blockPos, " ".length() != 0);
                if (blockByText == Blocks.air) {
                    CommandBase.notifyOperators(commandSender, this, CommandSetBlock.I[0x87 ^ 0x81], new Object["".length()]);
                    return;
                }
            }
            else if (array[0x93 ^ 0x96].equals(CommandSetBlock.I[0xA5 ^ 0xA2]) && !entityWorld.isAirBlock(blockPos)) {
                throw new CommandException(CommandSetBlock.I[0x10 ^ 0x18], new Object["".length()]);
            }
        }
        final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
        if (tileEntity != null) {
            if (tileEntity instanceof IInventory) {
                ((IInventory)tileEntity).clear();
            }
            final World world = entityWorld;
            final BlockPos blockPos2 = blockPos;
            final IBlockState defaultState = Blocks.air.getDefaultState();
            int length;
            if (blockByText == Blocks.air) {
                length = "  ".length();
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                length = (0x3 ^ 0x7);
            }
            world.setBlockState(blockPos2, defaultState, length);
        }
        final IBlockState stateFromMeta = blockByText.getStateFromMeta(n);
        if (!entityWorld.setBlockState(blockPos, stateFromMeta, "  ".length())) {
            throw new CommandException(CommandSetBlock.I[0x8C ^ 0x85], new Object["".length()]);
        }
        if (n2 != 0) {
            final TileEntity tileEntity2 = entityWorld.getTileEntity(blockPos);
            if (tileEntity2 != null) {
                tagFromJson.setInteger(CommandSetBlock.I[0x58 ^ 0x52], blockPos.getX());
                tagFromJson.setInteger(CommandSetBlock.I[0x3E ^ 0x35], blockPos.getY());
                tagFromJson.setInteger(CommandSetBlock.I[0x52 ^ 0x5E], blockPos.getZ());
                tileEntity2.readFromNBT(tagFromJson);
            }
        }
        entityWorld.notifyNeighborsRespectDebug(blockPos, stateFromMeta.getBlock());
        commandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, " ".length());
        CommandBase.notifyOperators(commandSender, this, CommandSetBlock.I[0x34 ^ 0x39], new Object["".length()]);
    }
}
