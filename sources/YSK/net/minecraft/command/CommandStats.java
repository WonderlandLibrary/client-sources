package net.minecraft.command;

import net.minecraft.server.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public class CommandStats extends CommandBase
{
    private static final String[] I;
    
    protected List<String> func_175777_e() {
        final Collection<ScoreObjective> scoreObjectives = MinecraftServer.getServer().worldServerForDimension("".length()).getScoreboard().getScoreObjectives();
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<ScoreObjective> iterator = scoreObjectives.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ScoreObjective scoreObjective = iterator.next();
            if (!scoreObjective.getCriteria().isReadOnly()) {
                arrayList.add(scoreObjective.getName());
            }
        }
        return (List<String>)arrayList;
    }
    
    @Override
    public List<String> addTabCompletionOptions(final ICommandSender commandSender, final String[] array, final BlockPos blockPos) {
        List<String> list;
        if (array.length == " ".length()) {
            final String[] array2 = new String["  ".length()];
            array2["".length()] = CommandStats.I[0x9B ^ 0x8C];
            array2[" ".length()] = CommandStats.I[0x80 ^ 0x98];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array2);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (array.length == "  ".length() && array["".length()].equals(CommandStats.I[0x23 ^ 0x3A])) {
            list = CommandBase.getListOfStringsMatchingLastWord(array, this.func_175776_d());
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (array.length >= "  ".length() && array.length <= (0x1E ^ 0x1A) && array["".length()].equals(CommandStats.I[0x8B ^ 0x91])) {
            list = CommandBase.func_175771_a(array, " ".length(), blockPos);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else if ((array.length != "   ".length() || !array["".length()].equals(CommandStats.I[0x1D ^ 0x6])) && (array.length != (0xA2 ^ 0xA7) || !array["".length()].equals(CommandStats.I[0xA6 ^ 0xBA]))) {
            if ((array.length != (0x9 ^ 0xD) || !array["".length()].equals(CommandStats.I[0x55 ^ 0x48])) && (array.length != (0xB ^ 0xD) || !array["".length()].equals(CommandStats.I[0x8A ^ 0x94]))) {
                if ((array.length != (0x2F ^ 0x29) || !array["".length()].equals(CommandStats.I[0xB6 ^ 0xA9])) && (array.length != (0x2E ^ 0x26) || !array["".length()].equals(CommandStats.I[0x5A ^ 0x7A]))) {
                    list = null;
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else {
                    list = CommandBase.getListOfStringsMatchingLastWord(array, this.func_175777_e());
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
            }
            else {
                list = CommandBase.getListOfStringsMatchingLastWord(array, CommandResultStats.Type.getTypeNames());
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
        }
        else {
            final String[] array3 = new String["  ".length()];
            array3["".length()] = CommandStats.I[0x1E ^ 0x3F];
            array3[" ".length()] = CommandStats.I[0x5F ^ 0x7D];
            list = CommandBase.getListOfStringsMatchingLastWord(array, array3);
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
            if (1 < 1) {
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
    public void processCommand(final ICommandSender commandSender, final String[] array) throws CommandException {
        if (array.length < " ".length()) {
            throw new WrongUsageException(CommandStats.I["  ".length()], new Object["".length()]);
        }
        int n;
        if (array["".length()].equals(CommandStats.I["   ".length()])) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            if (!array["".length()].equals(CommandStats.I[0x26 ^ 0x22])) {
                throw new WrongUsageException(CommandStats.I[0x1C ^ 0x19], new Object["".length()]);
            }
            n = " ".length();
        }
        int length;
        if (n != 0) {
            if (array.length < (0x53 ^ 0x56)) {
                throw new WrongUsageException(CommandStats.I[0x96 ^ 0x90], new Object["".length()]);
            }
            length = (0x3 ^ 0x7);
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            if (array.length < "   ".length()) {
                throw new WrongUsageException(CommandStats.I[0x73 ^ 0x74], new Object["".length()]);
            }
            length = "  ".length();
        }
        final String s = array[length++];
        if (CommandStats.I[0xCA ^ 0xC2].equals(s)) {
            if (array.length < length + "   ".length()) {
                if (length == (0xB2 ^ 0xB7)) {
                    throw new WrongUsageException(CommandStats.I[0x59 ^ 0x50], new Object["".length()]);
                }
                throw new WrongUsageException(CommandStats.I[0x46 ^ 0x4C], new Object["".length()]);
            }
        }
        else {
            if (!CommandStats.I[0xB ^ 0x0].equals(s)) {
                throw new WrongUsageException(CommandStats.I[0xC ^ 0x0], new Object["".length()]);
            }
            if (array.length < length + " ".length()) {
                if (length == (0x30 ^ 0x35)) {
                    throw new WrongUsageException(CommandStats.I[0xCB ^ 0xC6], new Object["".length()]);
                }
                throw new WrongUsageException(CommandStats.I[0x1E ^ 0x10], new Object["".length()]);
            }
        }
        final CommandResultStats.Type typeByName = CommandResultStats.Type.getTypeByName(array[length++]);
        if (typeByName == null) {
            throw new CommandException(CommandStats.I[0x15 ^ 0x1A], new Object["".length()]);
        }
        final World entityWorld = commandSender.getEntityWorld();
        CommandResultStats commandResultStats;
        if (n != 0) {
            final BlockPos blockPos = CommandBase.parseBlockPos(commandSender, array, " ".length(), "".length() != 0);
            final TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
            if (tileEntity == null) {
                final String s2 = CommandStats.I[0x32 ^ 0x22];
                final Object[] array2 = new Object["   ".length()];
                array2["".length()] = blockPos.getX();
                array2[" ".length()] = blockPos.getY();
                array2["  ".length()] = blockPos.getZ();
                throw new CommandException(s2, array2);
            }
            if (tileEntity instanceof TileEntityCommandBlock) {
                commandResultStats = ((TileEntityCommandBlock)tileEntity).getCommandResultStats();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                if (!(tileEntity instanceof TileEntitySign)) {
                    final String s3 = CommandStats.I[0xF ^ 0x1E];
                    final Object[] array3 = new Object["   ".length()];
                    array3["".length()] = blockPos.getX();
                    array3[" ".length()] = blockPos.getY();
                    array3["  ".length()] = blockPos.getZ();
                    throw new CommandException(s3, array3);
                }
                commandResultStats = ((TileEntitySign)tileEntity).getStats();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
        }
        else {
            commandResultStats = CommandBase.func_175768_b(commandSender, array[" ".length()]).getCommandStats();
        }
        if (CommandStats.I[0x3F ^ 0x2D].equals(s)) {
            final String s4 = array[length++];
            final String s5 = array[length];
            if (s4.length() == 0 || s5.length() == 0) {
                throw new CommandException(CommandStats.I[0x6B ^ 0x78], new Object["".length()]);
            }
            CommandResultStats.func_179667_a(commandResultStats, typeByName, s4, s5);
            final String s6 = CommandStats.I[0xA2 ^ 0xB6];
            final Object[] array4 = new Object["   ".length()];
            array4["".length()] = typeByName.getTypeName();
            array4[" ".length()] = s5;
            array4["  ".length()] = s4;
            CommandBase.notifyOperators(commandSender, this, s6, array4);
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (CommandStats.I[0xF ^ 0x1A].equals(s)) {
            CommandResultStats.func_179667_a(commandResultStats, typeByName, null, null);
            final String s7 = CommandStats.I[0x2F ^ 0x39];
            final Object[] array5 = new Object[" ".length()];
            array5["".length()] = typeByName.getTypeName();
            CommandBase.notifyOperators(commandSender, this, s7, array5);
        }
        if (n != 0) {
            entityWorld.getTileEntity(CommandBase.parseBlockPos(commandSender, array, " ".length(), (boolean)("".length() != 0))).markDirty();
        }
    }
    
    protected String[] func_175776_d() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public String getCommandName() {
        return CommandStats.I["".length()];
    }
    
    @Override
    public String getCommandUsage(final ICommandSender commandSender) {
        return CommandStats.I[" ".length()];
    }
    
    static {
        I();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] array, final int n) {
        if (array.length > 0 && array["".length()].equals(CommandStats.I[0x3F ^ 0x1C]) && n == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x48 ^ 0x6C])["".length()] = I("%\u0004\u00006$", "VpaBW");
        CommandStats.I[" ".length()] = I(".?\u0018&6#4\u0006e$91\u00018y8#\u0014,2", "MPuKW");
        CommandStats.I["  ".length()] = I("\u000e\"/\u00023\u0003)1A!\u0019,6\u001c|\u0018>#\b7", "mMBoR");
        CommandStats.I["   ".length()] = I("0/#\u001d\u0003,", "UAWtw");
        CommandStats.I[0x73 ^ 0x77] = I("%\u001a\u0017\t\u001b", "Gvxjp");
        CommandStats.I[0x3B ^ 0x3E] = I("\u0011#\u00075\u0003\u001c(\u0019v\u0011\u0006-\u001e+L\u0007?\u000b?\u0007", "rLjXb");
        CommandStats.I[0x5C ^ 0x5A] = I("\f9+\u001e\u0007\u000125]\u0015\u001b72\u0000H\r:)\u0010\rA#5\u0012\u0001\n", "oVFsf");
        CommandStats.I[0xC ^ 0xB] = I("\u0011\t\u0001>\u0000\u001c\u0002\u001f}\u0012\u0006\u0007\u0018 O\u0017\b\u0018:\u0015\u000bH\u0019 \u0000\u0015\u0003", "rflSa");
        CommandStats.I[0x36 ^ 0x3E] = I("\u0016\u0007\u0007", "ebsVS");
        CommandStats.I[0x68 ^ 0x61] = I("\t\t\u00045\u000f\u0004\u0002\u001av\u001d\u001e\u0007\u001d+@\b\n\u0006;\u0005D\u0015\f,@\u001f\u0015\b?\u000b", "jfiXn");
        CommandStats.I[0x2A ^ 0x20] = I("\n\u001e$=\u0017\u0007\u0015:~\u0005\u001d\u0010=#X\f\u001f=9\u0002\u0010_:5\u0002G\u0004:1\u0011\f", "iqIPv");
        CommandStats.I[0x50 ^ 0x5B] = I("\f\u000e\u0010\u0002\u0010", "obucb");
        CommandStats.I[0x3F ^ 0x33] = I("\b5).'\u0005>7m5\u001f;00h\u001e)%$#", "kZDCF");
        CommandStats.I[0xA0 ^ 0xAD] = I("\u0015-\b9$\u0018&\u0016z6\u0002#\u0011'k\u0014.\n7.X!\t1$\u0004l\u0010'$\u0011'", "vBeTE");
        CommandStats.I[0x27 ^ 0x29] = I("\u0011<\u001f;\n\u001c7\u0001x\u0018\u00062\u0006%E\u0017=\u0006?\u001f\u000b}\u0011:\u000e\u0013!\\#\u0018\u00134\u0017", "rSrVk");
        CommandStats.I[0x80 ^ 0x8F] = I("$:$<\")1:\u007f034=\"m!4 =&#", "GUIQC");
        CommandStats.I[0x8F ^ 0x9F] = I(":\n/\u001767\u00011T$-\u00046\ty7\n\u0001\u0015:)\u00046\u001355\u0000\u0000\u00168:\u000e", "YeBzW");
        CommandStats.I[0x85 ^ 0x94] = I("\u000b\u000e<\u00063\u0006\u0005\"E!\u001c\u0000%\u0018|\u0006\u000e\u0012\u0004?\u0018\u0000%\u00020\u0004\u0004\u0013\u0007=\u000b\n", "haQkR");
        CommandStats.I[0x30 ^ 0x22] = I("\u0004\u000f<", "wjHvs");
        CommandStats.I[0x65 ^ 0x76] = I("\u0012% 5\r\u001f.>v\u001f\u0005+9+B\u0017+$4\t\u0015", "qJMXl");
        CommandStats.I[0x5 ^ 0x11] = I("\u00176,%\u0015\u001a=2f\u0007\u000085;Z\u0007,\"+\u0011\u0007*", "tYAHt");
        CommandStats.I[0xAC ^ 0xB9] = I(" \u0001#\u0005\u001d", "CmFdo");
        CommandStats.I[0x84 ^ 0x92] = I(" \u0000 #9-\u000b>`+7\u000e9=v \u0003(/*&\u000b", "CoMNX");
        CommandStats.I[0x94 ^ 0x83] = I(".\u001e\u0006; 2", "KprRT");
        CommandStats.I[0xBB ^ 0xA3] = I("\t\b7\u0016'", "kdXuL");
        CommandStats.I[0x58 ^ 0x41] = I("\u001c\b\u0002/\u0011\u0000", "yfvFe");
        CommandStats.I[0x42 ^ 0x58] = I("$\u0014\u0006-\u0001", "FxiNj");
        CommandStats.I[0x49 ^ 0x52] = I("4 \f\u001a\"(", "QNxsV");
        CommandStats.I[0xB8 ^ 0xA4] = I("\u0016\u000e\t7$", "tbfTO");
        CommandStats.I[0x82 ^ 0x9F] = I("!\f8\u0019\u001c=", "DbLph");
        CommandStats.I[0x98 ^ 0x86] = I("\u0012\u001b..<", "pwAMW");
        CommandStats.I[0x51 ^ 0x4E] = I("<4\u0004,\u0019 ", "YZpEm");
        CommandStats.I[0x72 ^ 0x52] = I("\u000e\u0018\u0018:\u000f", "ltwYd");
        CommandStats.I[0x11 ^ 0x30] = I(":6\u0012", "ISfZq");
        CommandStats.I[0xBA ^ 0x98] = I("6\u0005\u001d47", "UixUE");
        CommandStats.I[0x73 ^ 0x50] = I("+\u00171\f'7", "NyEeS");
    }
}
